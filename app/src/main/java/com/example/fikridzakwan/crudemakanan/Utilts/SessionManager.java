package com.example.fikridzakwan.crudemakanan.Utilts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.fikridzakwan.crudemakanan.Model.Login.LoginData;
import com.example.fikridzakwan.crudemakanan.UI.login.LoginActivity;

public class SessionManager {
    // Membuat variable global untuk shared preference

    private SharedPreferences preference;
    private SharedPreferences.Editor editor;
    private final Context context;

    public SessionManager(Context context) {
        this.context = context;
        preference = context.getSharedPreferences(Constants.preference_name, 0);
        // Membuat pref degan mode edit
        editor = preference.edit();
    }

    public void createSesion(LoginData loginData) {
        // Memsukan data user yang sudah login ke dalam Shared Preference
        editor.putBoolean(Constants.KEY_IS_LOGIN, true);
        editor.putString(Constants.KEY_USER_ID, loginData.getId_user());
        editor.putString(Constants.KEY_USER_NAMA, loginData.getNama_user());
        editor.putString(Constants.KEY_USER_ALAMAT, loginData.getAlamat());
        editor.putString(Constants.KEY_USER_NOTELP, loginData.getNo_telpl());
        editor.putString(Constants.KEY_USER_LEVEL, loginData.getLevel());
        editor.putString(Constants.KEY_USER_JENKEL, loginData.getJenkel());
        editor.putString(Constants.KEY_USER_USERNAME, loginData.getUsername());

        // Mengeksekusi penyimpanan
        editor.commit();
    }

    // Function untuk mencek apakah user sudah pernah login
    public boolean isLogin() {
        // Mengembalikan nilai boolean degan mengambil data dari pref KEY_IS_LOGIN
        return preference.getBoolean(Constants.KEY_IS_LOGIN, false);
    }

    // Membuat function untuk melakukan logout atau menghapus isi di dalam Shared Preference
    public void logout() {
        // Mengambil method clear untuk menghapus data Shared Preference
        editor.clear();
        // Mengeksekusi perintah clear
        editor.commit();
        // Membuat intent untuk berpindah halaman
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
