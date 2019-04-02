package com.example.fikridzakwan.crudemakanan.UI.Fragment.makananbyuser;

import com.example.fikridzakwan.crudemakanan.Model.Makanan.MakananData;

import java.util.List;

public interface MakananByUserConstract {
    interface View {
        void showProgress();
        void hideProgress();
        void showListFoodByUser(List<MakananData> foodByUserList);
        void showFailurMessage(String msg);
    }
    interface Presenter {
        void getListFoodByUser(String idUser);
    }
}
