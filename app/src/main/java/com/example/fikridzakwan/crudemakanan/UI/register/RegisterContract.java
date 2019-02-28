package com.example.fikridzakwan.crudemakanan.UI.register;

import com.example.fikridzakwan.crudemakanan.Model.LoginData;

public interface RegisterContract {
    interface View {
        void showProgress();
        void hideProgress();
        void showError(String msg);
        void showRegisterSuccess(String msg);
    }
    interface Presenter {
        void doRegisterUser(LoginData loginData);
    }
}
