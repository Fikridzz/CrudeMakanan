package com.example.fikridzakwan.crudemakanan.UI.login;

import android.content.Context;

import com.example.fikridzakwan.crudemakanan.Data.remote.ApiClient;
import com.example.fikridzakwan.crudemakanan.Data.remote.ApiInterface;
import com.example.fikridzakwan.crudemakanan.Model.Login.LoginData;
import com.example.fikridzakwan.crudemakanan.Model.Login.LoginRespone;
import com.example.fikridzakwan.crudemakanan.Utilts.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginConstract.Presenter {

    private final LoginConstract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private SessionManager mSessionManager;

    public LoginPresenter(LoginConstract.View view) {
        this.view = view;
    }

    @Override
    public void doLogin(String username, String password) {
        // Mencek username dan password
        if (username.isEmpty()) {
            view.usernameError("Username is empty");
            return;
        }
        if (password.isEmpty()) {
            view.passwordError("Password is empety");
            return;
        }

        view.showProgress();
        Call<LoginRespone> call = apiInterface.loiginUser(username, password);
        call.enqueue(new Callback<LoginRespone>() {
            @Override
            public void onResponse(Call<LoginRespone> call, Response<LoginRespone> response) {
                view.hideProgress();
                if (response.body() != null) {
                    if (response.body().getResult() == 1) {
                        if (response.body().getData() != null) {
                            LoginData loginData = response.body().getData();
                            String message = response.body().getMessage();
                            view.loginSuccess(response.body().getMessage(), response.body().getData());
                        } else {
                            view.loginFailure("No data");
                        }
                    } else {
                        view.loginFailure(response.body().getMessage());
                    }
                } else {
                    view.loginFailure("No data");
                }
            }

            @Override
            public void onFailure(Call<LoginRespone> call, Throwable t) {
                view.hideProgress();
                view.loginFailure(t.getMessage());
            }
        });
    }

    @Override
    public void saveDataUser(Context context, LoginData loginData) {
        // Membuat object Session Manager
        mSessionManager = new SessionManager(context);
        // Mensave data ke Session Prefrence degan menggunakan metho dari class Session Manager
        mSessionManager.createSesion(loginData);
    }

    @Override
    public void cekLogin(Context context) {
        mSessionManager = new SessionManager(context);
        Boolean isLogin = mSessionManager.isLogin();
        // Mengecek apaka KEY_IS_LOGIN bernilai true
        if (isLogin) {
            // Menyuruh view untuk melakukan perpindahan ke MainActivity
            view.isLogin();
        }
    }
}
