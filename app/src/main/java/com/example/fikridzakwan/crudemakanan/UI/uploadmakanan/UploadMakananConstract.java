package com.example.fikridzakwan.crudemakanan.UI.uploadmakanan;

import android.content.Context;
import android.net.Uri;

import com.example.fikridzakwan.crudemakanan.Model.Makanan.MakananData;

import java.util.List;

public interface UploadMakananConstract {
    interface View {
        void showProgress();
        void hideProgress();
        void showMessage(String msg);
        void successUpload();
        void showSpinnerCategory(List<MakananData> categoryDataList);
    }
    interface Presenter {
        void getCategory();
        void uploadMakanan(Context context,
                           Uri fielPath,
                           String namaMakanan,
                           String descMakanan,
                           String idKategori);
    }
}
