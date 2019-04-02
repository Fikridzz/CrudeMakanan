package com.example.fikridzakwan.crudemakanan.UI.Fragment.makananbyuser;

import com.example.fikridzakwan.crudemakanan.Data.remote.ApiClient;
import com.example.fikridzakwan.crudemakanan.Data.remote.ApiInterface;
import com.example.fikridzakwan.crudemakanan.Model.Makanan.MakananResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakananByUserPresenter implements MakananByUserConstract.Presenter {

    private final MakananByUserConstract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public MakananByUserPresenter(MakananByUserConstract.View view) {
        this.view = view;
    }

    @Override
    public void getListFoodByUser(String idUser) {
        view.showProgress();

        if (idUser.isEmpty()) {
            view.showFailurMessage("ID user tidak ada");
            return;
        }

        Call<MakananResponse> call = apiInterface.getMakananByUser(Integer.valueOf(idUser));
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();

                if (response.body() != null) {
                    if (response.body().getResult() == 1) {
                        view.showListFoodByUser(response.body().getMakananDataList());
                    } else {
                        view.showFailurMessage(response.body().getMessage());
                    }
                } else {
                    view.showFailurMessage("Data kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.showFailurMessage(t.getMessage());
            }
        });
    }
}
