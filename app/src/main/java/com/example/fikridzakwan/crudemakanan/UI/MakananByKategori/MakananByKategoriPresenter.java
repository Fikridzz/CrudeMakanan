package com.example.fikridzakwan.crudemakanan.UI.MakananByKategori;

import com.example.fikridzakwan.crudemakanan.Data.remote.ApiClient;
import com.example.fikridzakwan.crudemakanan.Data.remote.ApiInterface;
import com.example.fikridzakwan.crudemakanan.Model.Makanan.MakananResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakananByKategoriPresenter implements MakananByKategoriConstract.Presenter {

    private final MakananByKategoriConstract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public MakananByKategoriPresenter(MakananByKategoriConstract.View view) {
        this.view = view;
    }

    @Override
    public void getListFoodByKategori(String idKategori) {
        view.showProgress();

        if (idKategori.isEmpty()) {
            view.showFailurMessage("ID kategori tidak ada");
            return;
        }

        Call<MakananResponse> call = apiInterface.getDetailMakananByKategori(Integer.valueOf(idKategori));
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body() != null) {
                    if (response.body().getResult() == 1) {
                        view.showFoodByKategoriList(response.body().getMakananDataList());
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
