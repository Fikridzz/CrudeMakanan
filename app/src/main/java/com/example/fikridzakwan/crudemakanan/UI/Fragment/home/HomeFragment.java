package com.example.fikridzakwan.crudemakanan.UI.Fragment.home;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fikridzakwan.crudemakanan.Data.adapter.MakananAdapter;
import com.example.fikridzakwan.crudemakanan.Model.Makanan.MakananData;
import com.example.fikridzakwan.crudemakanan.R;
import com.example.fikridzakwan.crudemakanan.UI.uploadmakanan.UploadMakananActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeConstract.View {


    @BindView(R.id.rv_makanan_news)
    RecyclerView rvMakananNews;
    @BindView(R.id.rv_makanan_populer)
    RecyclerView rvMakananPopuler;
    @BindView(R.id.rv_kategori)
    RecyclerView rvKategori;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;

    // TODO 1 Menyiapkan variable yang dibutuhkan
    private HomePresenter mHomePresenter = new HomePresenter(this);

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);

        // TODO 2 Mengambil data foodnews, popular, dan kategori
        mHomePresenter.getListFoodNews();
        mHomePresenter.getListFoodKategori();
        mHomePresenter.getListFoodPopular();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHomePresenter.getListFoodNews();
                mHomePresenter.getListFoodKategori();
                mHomePresenter.getListFoodPopular();
            }
        });
        return view;
    }

    @Override
    public void showProgress() {
        swipeRefresh.setRefreshing(true);

    }

    @Override
    public void hideProgress() {
        swipeRefresh.setRefreshing(false);

    }

    @Override
    public void showFoodNewsList(List<MakananData> foodNewsList) {
        rvMakananNews.setAdapter(new MakananAdapter(MakananAdapter.TYPE_1, getContext(), foodNewsList));
        rvMakananNews.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

    }

    @Override
    public void showFoodPopulerList(List<MakananData> foodPopulerList) {
        rvMakananPopuler.setAdapter(new MakananAdapter(MakananAdapter.TYPE_2, getContext(), foodPopulerList));
        rvMakananPopuler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

    }

    @Override
    public void showFoodKategoriList(List<MakananData> foodKategoriList) {
        rvKategori.setAdapter(new MakananAdapter(MakananAdapter.TYPE_3, getContext(), foodKategoriList));
        rvKategori.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

    }

    @Override
    public void showFailureMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.floating_action_button)
    public void onViewClicked() {
        startActivity(new Intent(getContext(), UploadMakananActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        mHomePresenter.getListFoodNews();
        mHomePresenter.getListFoodPopular();
        mHomePresenter.getListFoodKategori();
    }
}
