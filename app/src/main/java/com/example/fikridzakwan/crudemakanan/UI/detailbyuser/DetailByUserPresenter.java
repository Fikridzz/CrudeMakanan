package com.example.fikridzakwan.crudemakanan.UI.detailbyuser;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.fikridzakwan.crudemakanan.Data.remote.ApiClient;
import com.example.fikridzakwan.crudemakanan.Data.remote.ApiInterface;
import com.example.fikridzakwan.crudemakanan.Model.DetailMakanan.DetailMakananResponse;
import com.example.fikridzakwan.crudemakanan.Model.Makanan.MakananResponse;
import com.example.fikridzakwan.crudemakanan.Utilts.Constants;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class DetailByUserPresenter implements DetailByUserConstract.Presenter {

    private final DetailByUserConstract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private File imageFile;

    public DetailByUserPresenter(DetailByUserConstract.View view) {
        this.view = view;
    }

    @Override
    public void getKategori() {
        view.showProgress();

        Call<MakananResponse> call = apiInterface.getKategoriMakanan();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();

                if (response.body() != null) {
                    if (response.body().getResult() == 1) {
                        view.showSpinnerKategori(response.body().getMakananDataList());
                    } else {
                        view.showMessage(response.body().getMessage());
                    }
                } else {
                    view.showMessage("Data kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.showMessage(t.getMessage());
            }
        });
    }

    @Override
    public void getDetailMakanan(String idMakanan) {
        view.showProgress();

        if (idMakanan.isEmpty()) {
            view.showMessage("ID makanan tidak ada");
            return;
        }

        Call<DetailMakananResponse> call = apiInterface.getDetailMakanan(Integer.valueOf(idMakanan));
        call.enqueue(new Callback<DetailMakananResponse>() {
            @Override
            public void onResponse(Call<DetailMakananResponse> call, Response<DetailMakananResponse> response) {
                view.hideProgress();
                if (response.body() != null) {
                    if (response.body().getResult() == 1) {
                        view.showDetailMakanan(response.body().getMakananData());
                    } else {
                        view.showMessage(response.body().getMessage());
                    }
                } else {
                    view.showMessage("Data kosong");
                }
            }

            @Override
            public void onFailure(Call<DetailMakananResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void updateDetailMakanan(Context context, Uri filePath, String namaMakanan, String descMakanan, String idKategori, String namaFotoMakanan, String idMakanan) {
        view.showProgress();

        // Mencek foto, namamakanan, dan descmakanan apakah sudah terisi
        if (namaMakanan.isEmpty()) {
            view.showMessage("Nama makanan kosong");
            view.hideProgress();
            return;
        }

        if (descMakanan.isEmpty()) {
            view.hideProgress();
            view.showMessage("Desc makanan kosong");
            return;
        }

        if (filePath == null) {
            view.hideProgress();
            // Mengambil alamat file image
            File myFile = new File(filePath.getPath());
            Uri selectedImage = getImageContenrUri(context, myFile, filePath);
            String partImage = getPath(context, selectedImage);
            imageFile = new File(partImage);
        }


        // Mengambil id user dari shared pref
        SharedPreferences pref = context.getSharedPreferences(Constants.preference_name, 0);
        String idUser = pref.getString(Constants.KEY_USER_ID, "");

        // Mengambil tanngal sekarang degan format default yyyy-mm-dd hh:mm:ss
        String sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").format(new Date());

        // Memasukan data yang di perlukan ke dalam request body dengan time format-data untuk id kirim ke API
        RequestBody requestBody = RequestBody.create(MediaType.parse("mulitapart/form-data"), imageFile);
        MultipartBody.Part mPartImage = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);

        // Membungkus nama makanan
        RequestBody mNamaMakanan = RequestBody.create(MediaType.parse("mulipart/form-data"), namaMakanan);
        RequestBody mDescMakanan = RequestBody.create(MediaType.parse("mulipart/form-data"), descMakanan);
        RequestBody mFotoNamaMakanan = RequestBody.create(MediaType.parse("mulipart/form-data"), namaFotoMakanan);
        RequestBody datetime = RequestBody.create(MediaType.parse("multipart/form-data"), sdf);

        Call<MakananResponse> call = apiInterface.updateMakanan(
                Integer.valueOf(idMakanan),
                Integer.valueOf(idKategori),
                mNamaMakanan,
                mDescMakanan,
                mFotoNamaMakanan,
                datetime,
                mPartImage);

        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();

                if (response.body() == null) {
                    if (response.body().getResult() == 1) {
                        view.showMessage(response.body().getMessage());
                        view.successUpdate();
                    } else {
                        view.showMessage(response.body().getMessage());
                    }
                } else {
                    view.showMessage("Data kurang atau bermasalah");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showMessage(t.getMessage());
                Log.i("cek", "onFailure: " + t.getMessage());
            }
        });


    }

    @Override
    public void deleteMakanan(String idMakanan, String namaFotoMakanan) {
        view.showProgress();

        if (idMakanan.isEmpty()) {
            view.showMessage("ID tidak makanan ada");
            return;
        }
        if (namaFotoMakanan.isEmpty()) {
            view.showMessage("Nama foto makanan tidak ada");
            return;
        }

        Call<MakananResponse> call = apiInterface.deleteMakanan(Integer.valueOf(idMakanan), namaFotoMakanan);
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();

                if (response.body() == null) {
                    if (response.body().getResult() == 1) {
                        view.showMessage(response.body().getMessage());
                        view.successDelete();
                    } else {
                        view.showMessage(response.body().getMessage());
                    }
                } else {
                    view.showMessage("Data kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showMessage(t.getMessage());
            }
        });
    }

    private String getPath(Context context, Uri filePath) {

        Cursor cursor = context.getContentResolver().query(filePath, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ",
                new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    private Uri getImageContenrUri(Context context, File imageFile, Uri filePath) {
        String fileAbsolutePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{fileAbsolutePath}, null);

        if (cursor != null && cursor.moveToFirst()) {

            Log.i("Isi Selected if", "Masuk cursor ada");
            return filePath;

        } else {
            Log.i("Isi Selected else", "cursor tidak ada");
            if (imageFile.exists()) {
                Log.i("Isi Selected else", "imagefile exists");
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, fileAbsolutePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {

                Log.i("Isi Selected else", "imagefile tidak exists");
                return filePath;
            }
        }
    }
}
