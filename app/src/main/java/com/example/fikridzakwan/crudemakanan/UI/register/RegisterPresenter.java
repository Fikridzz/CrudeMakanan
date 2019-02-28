package com.example.fikridzakwan.crudemakanan.UI.register;

import android.telecom.Call;

import com.example.fikridzakwan.crudemakanan.Data.remote.ApiClient;
import com.example.fikridzakwan.crudemakanan.Data.remote.ApiInterface;
import com.example.fikridzakwan.crudemakanan.Model.LoginData;
import com.example.fikridzakwan.crudemakanan.Model.LoginRespone;

import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter implements RegisterContract.Presenter {

    private final RegisterContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
    }

    @Override
    public void doRegisterUser(LoginData loginData) {
        if (loginData != null) {
            if (!loginData.getNama_user().isEmpty() &&
                    !loginData.getUsername().isEmpty() &&
                    !loginData.getAlamat().isEmpty() &&
                    !loginData.getJenkel().isEmpty() &&
                    !loginData.getNo_telpl().isEmpty() &&
                    !loginData.getPassword().isEmpty() &&
                    !loginData.getLevel().isEmpty()) {

                view.showProgress();
                retrofit2.Call<LoginRespone> call = apiInterface.registerUser(
                        loginData.getUsername(),
                        loginData.getPassword(),
                        loginData.getNama_user(),
                        loginData.getAlamat(),
                        loginData.getJenkel(),
                        loginData.getNo_telpl(),
                        loginData.getLevel());
                call.enqueue(new Callback<LoginRespone>() {
                    @Override
                    public void onResponse(retrofit2.Call<LoginRespone> call, Response<LoginRespone> response) {
                        view.hideProgress();
                        if (response.body() != null) {
                            if (response.body().getResult() == 1) {
                                view.showRegisterSuccess(response.body().getMessage());
                            } else {
                                view.showError(response.body().getMessage());
                            }
                        } else {
                            view.showError("Data is empety");
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<LoginRespone> call, Throwable t) {
                        view.hideProgress();
                        view.showError(t.getMessage());

                    }
                });

            } else {
                view.showError("Tidak boleh ada yang kosong");
            }
        }
    }
}
