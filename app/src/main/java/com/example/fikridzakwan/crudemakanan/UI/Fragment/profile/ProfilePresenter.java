package com.example.fikridzakwan.crudemakanan.UI.Fragment.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.fikridzakwan.crudemakanan.Data.remote.ApiClient;
import com.example.fikridzakwan.crudemakanan.Data.remote.ApiInterface;
import com.example.fikridzakwan.crudemakanan.Model.LoginData;
import com.example.fikridzakwan.crudemakanan.Model.LoginRespone;
import com.example.fikridzakwan.crudemakanan.Utilts.Constants;
import com.example.fikridzakwan.crudemakanan.Utilts.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter implements ProfileContract.Presenter {

    private SharedPreferences   preferences;
    private final ProfileContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


    public ProfilePresenter(ProfileContract.View view) {
        this.view = view;
    }

    @Override
    public void getDataUser(Context context) {
        // Mengambil data dari SharedPreference
        preferences = context.getSharedPreferences(Constants.preference_name,0);

        // Mebuat object model Login Data
        LoginData loginData = new LoginData();

        // Memasukan data SharedePreference ke dalam model Login Data
        loginData.setId_user(preferences.getString(Constants.KEY_USER_ID, ""));
        loginData.setNama_user(preferences.getString(Constants.KEY_USER_NAMA, ""));
        loginData.setAlamat(preferences.getString(Constants.KEY_USER_ALAMAT, ""));
        loginData.setNo_telpl(preferences.getString(Constants.KEY_USER_NOTELP, ""));
        loginData.setJenkel(preferences.getString(Constants.KEY_USER_JENKEL, ""));

        // Mengirim data model Login Data ke View
        view.showDataUser(loginData);
    }

    @Override
    public void logoutSesion(Context context) {
        // Membuat class Sesion Manager untuk memangil method logout
        SessionManager mSessionManager = new SessionManager(context);
        mSessionManager.logout();

    }

    @Override
    public void updateDataUser(final Context context, final LoginData loginData) {

        // Menampilkan Show Progress
        view.showProgress();

        // Membuat object call dan memanggil method update user serta mengirimkan datanya
        Call<LoginRespone> call = apiInterface.updateUser(
                Integer.valueOf(loginData.getId_user()),
                loginData.getNama_user(),
                loginData.getAlamat(),
                loginData.getJenkel(),
                loginData.getNo_telpl());
        call.enqueue(new Callback<LoginRespone>() {
            @Override
            public void onResponse(Call<LoginRespone> call, Response<LoginRespone> response) {
                // Mencek reponse dan mengisi body
                view.hideProgress();
                if (response.isSuccessful() && response.body() != null) {
                    // Mencek result apakah 1
                    if (response.body().getResult() == 1) {
                        Log.i("cek", loginData.getJenkel());
                        // Setelah berhasil update online lalu update ke SharedPreference
                        // Membuat object SharedPreference yang sudah ada di SesionManager
                        preferences = context.getSharedPreferences(Constants.preference_name,0);
                        // Mengubah mode SharedPreference menjadi edit
                        SharedPreferences.Editor editor = preferences.edit();
                        // Memasukan data ke dalam SharedPreference
                        editor.putString(Constants.KEY_USER_NAMA, loginData.getNama_user());
                        editor.putString(Constants.KEY_USER_ALAMAT, loginData.getAlamat());
                        editor.putString(Constants.KEY_USER_JENKEL, loginData.getJenkel());
                        editor.putString(Constants.KEY_USER_NOTELP, loginData.getNo_telpl());
                        // Apply perubahan
                        editor.apply();
                        view.showSuccessUpdateUser(response.body().getMessage());
                    } else {
                        view.showSuccessUpdateUser(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginRespone> call, Throwable t) {
                view.hideProgress();
                view.showSuccessUpdateUser(t.getMessage());
            }
        });
    }
}
