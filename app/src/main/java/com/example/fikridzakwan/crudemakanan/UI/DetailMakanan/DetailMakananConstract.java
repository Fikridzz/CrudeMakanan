package com.example.fikridzakwan.crudemakanan.UI.DetailMakanan;

import com.example.fikridzakwan.crudemakanan.Model.Makanan.MakananData;

public interface DetailMakananConstract {
    interface View {
        void showProgress();
        void hideProgress();
        void showDetailMakanan(MakananData makananData);
        void showFailurMessage(String msg);
    }
    interface Presenter {
        void getDetailMakanan(String idMakanan);
    }
}
