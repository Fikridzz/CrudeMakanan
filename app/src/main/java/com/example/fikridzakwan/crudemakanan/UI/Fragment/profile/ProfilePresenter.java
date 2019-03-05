package com.example.fikridzakwan.crudemakanan.UI.Fragment.profile;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.fikridzakwan.crudemakanan.Model.LoginData;
import com.example.fikridzakwan.crudemakanan.Utilts.Constants;
import com.example.fikridzakwan.crudemakanan.Utilts.SessionManager;

public class ProfilePresenter implements ProfileContract.Presenter {

    private SharedPreferences preferences;
    private final ProfileContract.View view;

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
}
