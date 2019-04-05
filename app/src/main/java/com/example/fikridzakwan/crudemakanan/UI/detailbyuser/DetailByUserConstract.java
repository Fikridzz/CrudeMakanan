package com.example.fikridzakwan.crudemakanan.UI.detailbyuser;

import android.content.Context;
import android.net.Uri;

import com.example.fikridzakwan.crudemakanan.Model.Makanan.MakananData;

import java.util.List;

public interface DetailByUserConstract {
    interface View {
        void showProgress();
        void hideProgress();
        void showDetailMakanan(MakananData makananData);
        void showMessage(String msg);
        void successDelete();
        void successUpdate();
        void showSpinnerKategori(List<MakananData> kategoriDataList);
    }
    interface Presenter {
        void getKategori();
        void getDetailMakanan(String idMakanan);
        void updateDetailMakanan(Context context, Uri filePath, String namaMakanan, String descMakanan, String idKategori, String namaFotoMakanan, String idMakanan);
        void deleteMakanan(String idMakanan, String namaFotoMakanan);
    }
}
