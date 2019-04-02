package com.example.fikridzakwan.crudemakanan.UI.login;

import android.content.Context;

import com.example.fikridzakwan.crudemakanan.Model.Login.LoginData;

public interface LoginConstract {
    interface View {
        void showProgress();
        void hideProgress();
        void loginSuccess(String msg, LoginData loginData);
        void loginFailure(String msg);
        void usernameError(String msg);
        void passwordError(String msg);
        void isLogin();
    }
    interface Presenter {
        void doLogin(String username, String password);
        void saveDataUser(Context context, LoginData loginData);
        void cekLogin(Context context);
    }
}
