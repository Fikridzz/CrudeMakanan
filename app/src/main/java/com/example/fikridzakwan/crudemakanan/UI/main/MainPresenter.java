package com.example.fikridzakwan.crudemakanan.UI.main;

import android.content.Context;

import com.example.fikridzakwan.crudemakanan.Utilts.SessionManager;

public class MainPresenter implements MainConstract.Presenter {

    @Override
    public void logoutSesion(Context context) {
        // Membuat object Session Manager untuk dapat digunakan
        SessionManager mSessionManager = new SessionManager(context);
        mSessionManager.logout();
    }
}
