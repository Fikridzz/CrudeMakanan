package com.example.fikridzakwan.crudemakanan.UI.Fragment.profile;

import android.content.Context;

import com.example.fikridzakwan.crudemakanan.Model.LoginData;

public interface ProfileContract {
    interface View {
        void showDataUser(LoginData loginData);
    }

    interface Presenter {
        void getData(Context context);
        void logoutSesion(Context context);
    }
}
