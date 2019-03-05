package com.example.fikridzakwan.crudemakanan.UI.Fragment.profile;

import android.content.Context;

public class ProfilePresenter implements ProfileContract.Presenter {

    private final ProfileContract.View view;

    public ProfilePresenter(ProfileContract.View view) {
        this.view = view;
    }

    @Override
    public void getData(Context context) {

    }

    @Override
    public void logoutSesion(Context context) {

    }
}
