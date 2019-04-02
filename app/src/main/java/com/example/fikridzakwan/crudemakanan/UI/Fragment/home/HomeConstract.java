package com.example.fikridzakwan.crudemakanan.UI.Fragment.home;

import com.example.fikridzakwan.crudemakanan.Model.Makanan.MakananData;
import com.example.fikridzakwan.crudemakanan.Model.Makanan.MakananResponse;

import java.util.List;

public interface HomeConstract {
    interface View {
        void showProgress();
        void hideProgress();
        void showFoodNewsList(List<MakananData> foodNewsList);
        void showFoodPopulerList(List<MakananData> foodPopulerList);
        void showFoodKategoriList(List<MakananData> foodKategoriList);
        void showFailureMessage(String msg);
    }
    interface Presenter {
        void getListFoodNews();
        void getListFoodPopular();
        void getListFoodKategori();
    }
}
