package com.example.fikridzakwan.crudemakanan.UI.Fragment.home;

import android.app.ProgressDialog;
import android.util.Log;

import com.example.fikridzakwan.crudemakanan.Data.remote.ApiClient;
import com.example.fikridzakwan.crudemakanan.Data.remote.ApiInterface;
import com.example.fikridzakwan.crudemakanan.Model.Makanan.MakananResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeConstract.Presenter {

    // TODO 1 Menyiapkan variable yang dibutuhkan
    private final HomeConstract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public HomePresenter(HomeConstract.View view) {
        this.view = view;
    }

    @Override
    public void getListFoodNews() {
        view.showProgress();

        Call<MakananResponse> call = apiInterface.getMakananBaru();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body() != null) {
                    if (response.body().getResult() == 1) {
                        view.showFoodNewsList(response.body().getMakananDataList());
                        view.showFailureMessage(response.body().getMessage());
                    }
                } else {
                    Log.i("cek news", response.message());
                    view.showFailureMessage("Data kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                Log.i("cek news", t.getMessage());
                view.hideProgress();
                view.showFailureMessage(t.getMessage());
            }
        });
    }

    @Override
    public void getListFoodPopular() {
        view.showProgress();

        Call<MakananResponse> call = apiInterface.getMakananPopuler();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body() != null) {
                    view.showFoodPopulerList(response.body().getMakananDataList());
                } else {
                    Log.i("cek populer", response.message());
                    view.showFailureMessage("Data kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                Log.i("cek populer", t.getMessage());
                view.showFailureMessage(t.getMessage());
                view.hideProgress();
            }
        });

    }

    @Override
    public void getListFoodKategori() {
        view.showProgress();

        Call<MakananResponse> call = apiInterface.getKategoriMakanan();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body() != null) {
                    view.showFoodKategoriList(response.body().getMakananDataList());
                } else {
                    Log.i("cek kategori", response.message());
                    view.showFailureMessage("Data kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                Log.i("cek populer", t.getMessage());
                view.showFailureMessage(t.getMessage());
                view.hideProgress();
            }
        });

    }
}
