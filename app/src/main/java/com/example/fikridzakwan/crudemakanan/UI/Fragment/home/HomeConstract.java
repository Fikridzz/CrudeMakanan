package com.example.fikridzakwan.crudemakanan.UI.Fragment.home;

import com.example.fikridzakwan.crudemakanan.Model.DataItem;

import java.util.List;

public interface HomeConstract {
    interface View {
        void showProgress();
        void hideProgress();
        void showDataList(List<DataItem> kategoriList);
        void showFailureMessage(String msg);
    }
    interface Presenter {
        void getListKategori();
    }
}
