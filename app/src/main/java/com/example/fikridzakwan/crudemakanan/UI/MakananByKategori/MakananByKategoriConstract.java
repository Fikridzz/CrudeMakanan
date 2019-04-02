package com.example.fikridzakwan.crudemakanan.UI.MakananByKategori;

import com.example.fikridzakwan.crudemakanan.Model.Makanan.MakananData;

import java.util.List;

public interface MakananByKategoriConstract {
    interface View {
        void showProgress();
        void hideProgress();
        void showFoodByKategoriList(List<MakananData> foodNewsList);
        void showFailurMessage(String msg);
    }
    interface Presenter {
        void getListFoodByKategori(String idKategori);
    }
}
