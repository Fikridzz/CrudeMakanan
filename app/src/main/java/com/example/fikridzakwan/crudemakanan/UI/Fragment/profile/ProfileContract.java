package com.example.fikridzakwan.crudemakanan.UI.Fragment.profile;

import android.content.Context;

import com.example.fikridzakwan.crudemakanan.Model.Login.LoginData;

public interface ProfileContract {
    interface View {
        void showProgress();
        void hideProgress();
        void showSuccessUpdateUser(String msg);
        void showDataUser(LoginData loginData);
    }

    interface Presenter {
        void getDataUser(Context context);
        void logoutSesion(Context context);
        void updateDataUser(Context context, LoginData loginData);
    }
}
