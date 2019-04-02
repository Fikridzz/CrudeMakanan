package com.example.fikridzakwan.crudemakanan.UI.uploadmakanan;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.fikridzakwan.crudemakanan.Data.remote.ApiClient;
import com.example.fikridzakwan.crudemakanan.Data.remote.ApiInterface;
import com.example.fikridzakwan.crudemakanan.Model.Upload.UploadResponse;
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

public class UploadMakananPresenter implements UploadMakananConstract.Presenter {

    // TODO Menyiapkan variable yang dibutuhkan
    private final UploadMakananConstract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public UploadMakananPresenter(UploadMakananConstract.View view) {
        this.view = view;
    }

    @Override
    public void getCategory() {
        view.showProgress();
        Call<MakananResponse> call = apiInterface.getKategoriMakanan();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body() != null) {
                    if (response.body().getResult() == 1) {
                        view.showSpinnerCategory(response.body().getMakananDataList());
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
                Log.i("cek failure", "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void uploadMakanan(Context context, Uri filePath, String namaMakanan, String descMakanan, String idKategori) {
        view.showProgress();

        if (namaMakanan.isEmpty()) {
            view.showMessage("Nama makanan kosong");
            view.hideProgress();
            return;
        }

        if (descMakanan.isEmpty()) {
            view.showMessage("Desc makanan kosong");
            view.hideProgress();
            return;
        }

        if (filePath == null) {
            view.showMessage("Gambar blom di pilih");
            view.hideProgress();
            return;
        }

        // Mengambil alamat file image
        File myFile = new File(filePath.getPath());
        Uri selectedImage = getImageContenrUri(context, myFile, filePath);
        String pathImage = getPath(context, selectedImage);
        File imageFile = new File(pathImage);


        // Mengambil id user di dalam Shared Preference
        SharedPreferences pref = context.getSharedPreferences(Constants.preference_name,0);
        String idUSer = pref.getString(Constants.KEY_USER_ID,"");

        // Mengambil date sekarang untuk waktu upload sekarang
        String dateNow = new SimpleDateFormat("yyyy-MM-dd- HH:mm:ss").format(new Date());

        // Memasukkan data yang diperlukan ke dalam request body degan  tipe from-data
        // Memasukan image file ke dalam requestbody
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
        MultipartBody.Part mPartImage = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);

        // Memasukkan nama, desc, dan inserttime
        RequestBody mNamaMakanan = RequestBody.create(MediaType.parse("multipart/form-data"), namaMakanan);
        RequestBody mDescMakanan = RequestBody.create(MediaType.parse("mulitpart/form-data"), descMakanan);
        RequestBody mInserttime = RequestBody.create(MediaType.parse("multipart/form-data"), dateNow);

        // Mengirim data ke  API
        Call<UploadResponse> call = apiInterface.uploadMakanan(
                Integer.valueOf(idUSer),
                Integer.valueOf(idKategori),
                mNamaMakanan,
                mDescMakanan,
                mInserttime,
                mPartImage);
        call.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                view.hideProgress();
                if (response.body() != null) {
                    if (response.body().getResult() == 1) {
                        view.showMessage(response.body().getMessage());
                        view.successUpload();
                    } else {
                        view.showMessage(response.body().getMessage());
                    }
                } else {
                    view.showMessage("Data kosong");
                }
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {

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
