package com.example.fikridzakwan.crudemakanan.UI.login;

import com.example.fikridzakwan.crudemakanan.Model.LoginData;

public interface LoginConstract {
    interface View {
        void showProgress();
        void hideProgress();
        void loginSuccess(String msg, LoginData loginData);
        void loginFailure(String msg);
        void usernameError(String msg);
        void passwordError(String msg);
    }
    interface Presenter {
        void doLogin(String username, String password);
    }
}
