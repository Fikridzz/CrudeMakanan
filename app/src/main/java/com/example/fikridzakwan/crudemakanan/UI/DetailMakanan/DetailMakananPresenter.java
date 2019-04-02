package com.example.fikridzakwan.crudemakanan.UI.DetailMakanan;

import com.example.fikridzakwan.crudemakanan.Data.remote.ApiClient;
import com.example.fikridzakwan.crudemakanan.Data.remote.ApiInterface;
import com.example.fikridzakwan.crudemakanan.Model.DetailMakanan.DetailMakananResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMakananPresenter implements DetailMakananConstract.Presenter {

    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private final DetailMakananConstract.View view;

    public DetailMakananPresenter(DetailMakananConstract.View view) {
        this.view = view;
    }

    @Override
    public void getDetailMakanan(String idMakanan) {
        view.showProgress();
        if (idMakanan.isEmpty()) {
            view.showFailurMessage("Id makanan tidak ada");
            return;
        }

        Call<DetailMakananResponse> call = apiInterface.getDetailMakanan(Integer.valueOf(idMakanan));
        call.enqueue(new Callback<DetailMakananResponse>() {
            @Override
            public void onResponse(Call<DetailMakananResponse> call, Response<DetailMakananResponse> response) {
                view.hideProgress();
                if (response.body() != null) {
                    if (response.body().getResult() == 1) {
                        view.showDetailMakanan(response.body().getMakananData());
                    } else {
                        view.showFailurMessage(response.body().getMessage());
                    }
                } else {
                    view.showFailurMessage("Data kosong");
                }
            }

            @Override
            public void onFailure(Call<DetailMakananResponse> call, Throwable t) {

            }
        });
    }
}
