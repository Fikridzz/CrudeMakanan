package com.example.fikridzakwan.crudemakanan.UI.detailbyuser;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.fikridzakwan.crudemakanan.Model.Makanan.MakananData;
import com.example.fikridzakwan.crudemakanan.R;
import com.example.fikridzakwan.crudemakanan.Utilts.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailByUserActivity extends AppCompatActivity implements DetailByUserConstract.View {

    @BindView(R.id.img_picture)
    ImageView imgPicture;
    @BindView(R.id.fab_choose_picture)
    FloatingActionButton fabChoosePicture;
    @BindView(R.id.layoutPicture)
    CardView layoutPicture;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_desc)
    EditText edtDesc;
    @BindView(R.id.spin_category)
    Spinner spinCategory;
    @BindView(R.id.layoutSaveMakanan)
    CardView layoutSaveMakanan;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private DetailByUserPresenter mDetailByUserPresenter = new DetailByUserPresenter(this);
    private Uri filePath;
    private String idKategori, idMakanan;
    private MakananData mMakananData;
    private String namaFotoMakanam;
    private String[] mIdKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_by_user);
        ButterKnife.bind(this);

        // Melakukan pengecekan dan permission untuk bisa mengaksese gallery
        PermissionGalery();

        // Menangkap idmakanan yang dikirimkan dari activity sebelumnya
        idMakanan = getIntent().getStringExtra(Constants.KEY_EXTRA_ID_MAKANAN);

        // Mengambil data kategori ntuk ditampilkan di spinner
        mDetailByUserPresenter.getKategori();

        // Mensetting swipe refresh
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(false);

                mDetailByUserPresenter.getKategori();
            }
        });
    }

    @Override
    public void showProgress() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showDetailMakanan(MakananData makananData) {
        // Kita ambil semua data detail makanan
        mMakananData = makananData;


        Log.i("cek foto makanan", mMakananData.getFotoMakanan());

        // Mengambil nama foto makanan
        namaFotoMakanam =  mMakananData.getFotoMakanan();

        // Mengambil idkategori
        idKategori = makananData.getIdKategori();

        // Menampilkan semua data ke layar
        edtName.setText(makananData.getNamaMakanan());
        edtDesc.setText(makananData.getDescMakanan());

        for (int i = 0;i < mIdKategori.length; i++) {

            Log.i("cek", "isi loop select namakategori " + mIdKategori[i]);

            if (Integer.valueOf(mIdKategori[i]).equals(Integer.valueOf(idKategori))) {
                spinCategory.setSelection(i);
                Log.i("cek", "isi dari i " + mIdKategori[i]);
                Log.i("cek", "isi select mIdkategori " + mIdKategori[i]);
                Log.i("cek", "isi select idKategori " + idKategori);
            }
        }

        RequestOptions options = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
        Glide.with(this).load(makananData.getUrlMakanan()).apply(options).into(imgPicture);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successDelete() {
        finish();
    }

    @Override
    public void successUpdate() {
        mDetailByUserPresenter.getKategori();
    }

    @Override
    public void showSpinnerKategori(final List<MakananData> kategoriDataList) {
        // Data penampung untuk  spinner
        String [] namaKategori = new String[kategoriDataList.size()];
        mIdKategori = new String[kategoriDataList.size()];
        for (int i = 0; i < kategoriDataList.size(); i++) {

            namaKategori[i] = kategoriDataList.get(i).getNamaKategori();
            mIdKategori[i] = kategoriDataList.get(i).getIdKategori();

            Log.i("cek", "isi show namakategori " + namaKategori[i]);
            Log.i("cek", "isi show mIdkategori " + mIdKategori[i]);
        }

        // Membuat adapter spinner
        ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, namaKategori);
        // Kita setting untuk menampilkan spinner degan 1 line
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Memasukan adapter ke spinner
        spinCategory.setAdapter(categorySpinnerAdapter);

        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Mengambil id kategory sesuai degan pilihan user
                idKategori = kategoriDataList.get(position).getIdKategori();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Mengambil data detail makanan
        mDetailByUserPresenter.getDetailMakanan(idMakanan);
    }

    private void PermissionGalery() {
        // Mengecek apakah user sudah memberikan permission untuk mengakses setelah storage
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.STORAGE_PERMISSION_CODE);
    }

    @OnClick({R.id.fab_choose_picture, R.id.btn_update, R.id.btn_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_choose_picture:
                // Mengambil gambar dari storage
                ShowFileChooser();
                break;
            case R.id.btn_update:
                mDetailByUserPresenter.updateDetailMakanan(
                        this,
                        filePath,
                        edtName.getText().toString(),
                        edtDesc.getText().toString(),
                        idKategori,
                        namaFotoMakanam,
                        idMakanan);
                break;
            case R.id.btn_delete:
                mDetailByUserPresenter.deleteMakanan(idMakanan, namaFotoMakanam);
                break;
        }
    }

    private void ShowFileChooser() {
        // Membuat object intent unutk dapat memilih data
        Intent intentGallery = new Intent(Intent.ACTION_PICK);
        intentGallery.setType("image/*");
        intentGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentGallery, "Selected picture"), Constants.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Mengambil data foto dan memasukan ke dalam variable filepath
            filePath = data.getData();

            try {
                // Mengambil data lalu di convert ke bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // Tampilkan gambar yang baru dipilihan layar
                imgPicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == Constants.STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                showMessage("Permission granted now you can read the storage");
                Log.i("Permission on", "onRequestPermissionsResult: " + String.valueOf(grantResults));
            } else {
                //Displaying another toast if permission is not granted
                showMessage("Oops you just denied the permission");
                Log.i("Permission off", "onRequestPermissionsResult: " + String.valueOf(grantResults));

            }
        }
    }
}
