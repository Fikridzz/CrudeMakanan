package com.example.fikridzakwan.crudemakanan.UI.Fragment.profile;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fikridzakwan.crudemakanan.Model.LoginData;
import com.example.fikridzakwan.crudemakanan.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProfileContract.View {


    @BindView(R.id.picture)
    CircleImageView picture;
    @BindView(R.id.fabChoosePic)
    FloatingActionButton fabChoosePic;
    @BindView(R.id.layoutPicture)
    RelativeLayout layoutPicture;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_alamat)
    EditText edtAlamat;
    @BindView(R.id.edt_no_telp)
    EditText edtNoTelp;
    @BindView(R.id.spin_gender)
    Spinner spinGender;
    @BindView(R.id.layoutProfil)
    CardView layoutProfil;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.layoutJenkel)
    CardView layoutJenkel;
    Unbinder unbinder;

    private ProfilePresenter mProfilePresenter = new ProfilePresenter(this);

    private String idUser, nama, alamat, noTelp;
    private int gender;
    private Menu action;

    private String mGender;
    private static final int GENDER_MALE = 1;
    private static final int GENDER_FEMALE = 2;
    private ProgressDialog progressDialog;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        // Menampilkan Option di fragment
        setHasOptionsMenu(true);

        // Mensetting spinner
        setupSpinner();

        // Merequest data yang di kerjakan oleh presenter
        mProfilePresenter.getDataUser(getContext());
        return view;
    }

    private void setupSpinner() {
        // Membuat adapter spinner
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.array_gender_option, android.R.layout.simple_spinner_item);
        // Menampilkan spinner 1 line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Memasukan adapter spinner ke dalam widget spinner kita
        spinGender.setAdapter(genderSpinnerAdapter);
        // Listterner spinner
        spinGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Menganbil posisi item yang dipilih
                String selection = (String) parent.getItemAtPosition(position);
                // Mengecek posisi pakah ada isinya
                if (!TextUtils.isEmpty(selection)) {
                    // Mencek apakah 1 atau 2 yang dipilih user
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = "L";
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = "P";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = "L";

            }
        });

    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Get Data");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();

    }

    @Override
    public void showSuccessUpdateUser(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        // Memgambil data ulang
        mProfilePresenter.getDataUser(getContext());

    }

    @Override
    public void showDataUser(LoginData loginData) {
        // Memasukan data yang sdah di ambil oleh presenter
        idUser = loginData.getId_user();
        nama = loginData.getNama_user();
        alamat = loginData.getAlamat();
        noTelp = loginData.getNo_telpl();
        if (loginData.getJenkel().equals("L")) {
            Log.i("cek gender", "masuk ke if L");
            gender = 1;
        } else {
            gender = 2;
        }

        Log.i("cek jenkel", loginData.getJenkel());
        if (!TextUtils.isEmpty(idUser)) {
            // Megubah widget agar tidak bisa di edit
            readMode();
            // Menset nama title action bar
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile" + nama);

            // Menampilkan data ke layar
            edtName.setText(nama);
            edtAlamat.setText(alamat);
            edtNoTelp.setText(noTelp);
            // Mencek gender dan memiblih gender untuk ditampilkan pada spinner
            switch (gender) {
                case GENDER_MALE:
                    Log.i("cek male", String.valueOf(gender));
                    spinGender.setSelection(0);
                    break;
                case GENDER_FEMALE:
                    spinGender.setSelection(1);
                    break;
            }
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_logout)
    public void onViewClicked() {
        // Melakukan perintah logout ke presenter
        mProfilePresenter.logoutSesion(getContext());
        // Menutup MainActivity
        getActivity().finish();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_editor, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:

                edtMode();
                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;
            case R.id.menu_save:
                // Mencek apakah field kosong
                if (!TextUtils.isEmpty(idUser)) {
                    if (TextUtils.isEmpty(edtName.getText().toString()) ||
                            TextUtils.isEmpty(edtAlamat.getText().toString()) ||
                            TextUtils.isEmpty(edtNoTelp.getText().toString())) {
                        // Menampilkan alert dialog untuk memberi hu user tidak boleh ada yang field yang kosong
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                        alertDialog.setMessage("Please complete the field");
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        alertDialog.show();

                    } else {
                        // Apabila user sudah mengisi semua field
                        LoginData loginData = new LoginData();
                        // Mengisi inputan user ke model logindata
                        loginData.setId_user(idUser);
                        loginData.setNama_user(edtName.getText().toString());
                        loginData.setAlamat(edtAlamat.getText().toString());
                        loginData.setNo_telpl(edtNoTelp.getText().toString());
                        loginData.setJenkel(mGender);

                        // Mengirim data ke presenter untuk di masukan ke dalam database
                        mProfilePresenter.updateDataUser(getContext(), loginData);

                        readMode();
                        action.findItem(R.id.menu_edit).setVisible(true);
                        action.findItem(R.id.menu_save).setVisible(false);
                    }
                } else {
                    readMode();
                    action.findItem(R.id.menu_edit).setVisible(true);
                    action.findItem(R.id.menu_save).setVisible(false);
                }

                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("RestrictedApi")
    private void readMode() {

        edtName.setFocusableInTouchMode(false);
        edtAlamat.setFocusableInTouchMode(false);
        edtNoTelp.setFocusableInTouchMode(false);
        edtName.setFocusable(false);
        edtAlamat.setFocusable(false);
        edtNoTelp.setFocusable(false);

        spinGender.setEnabled(false);
        fabChoosePic.setVisibility(View.INVISIBLE);

    }

    @SuppressLint("RestrictedApi")
    private void edtMode() {

        edtName.setFocusableInTouchMode(true);
        edtAlamat.setFocusableInTouchMode(true);
        edtNoTelp.setFocusableInTouchMode(true);

        spinGender.setEnabled(true);
        fabChoosePic.setVisibility(View.VISIBLE);

        action.findItem(R.id.menu_edit).setVisible(false);
        action.findItem(R.id.menu_save).setVisible(true);
    }
}
