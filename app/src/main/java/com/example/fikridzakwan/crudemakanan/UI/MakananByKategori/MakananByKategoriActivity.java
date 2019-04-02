package com.example.fikridzakwan.crudemakanan.UI.MakananByKategori;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fikridzakwan.crudemakanan.Data.adapter.MakananAdapter;
import com.example.fikridzakwan.crudemakanan.Model.Makanan.MakananData;
import com.example.fikridzakwan.crudemakanan.R;
import com.example.fikridzakwan.crudemakanan.Utilts.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MakananByKategoriActivity extends AppCompatActivity implements MakananByKategoriConstract.View {

    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.txt_info)
    TextView txtInfo;
    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
    @BindView(R.id.rv_makanan)
    RecyclerView rvMakanan;
    @BindView(R.id.sr_makanan)
    SwipeRefreshLayout srMakanan;

    private final MakananByKategoriPresenter mMakananByKategoriPresenter = new MakananByKategoriPresenter(this);
    private String idKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makanan_by_kategori);
        ButterKnife.bind(this);

        // Mengambil idKategori dari kiriman halaman sebelumnya
        idKategori = getIntent().getStringExtra(Constants.KEY_EXTRA_ID_KATEGORI);

        // Merequest id makanan by kategori
        mMakananByKategoriPresenter.getListFoodByKategori(idKategori);

        // Mensetting swiperefresh menghilangkan loading dan memanggil ulang data makanan by kategori
        srMakanan.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srMakanan.setRefreshing(false);
                mMakananByKategoriPresenter.getListFoodByKategori(idKategori);
            }
        });
    }

    @Override
    public void showProgress() {
        rlProgress.setVisibility(View.VISIBLE);
        srMakanan.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        rlProgress.setVisibility(View.GONE);
        rvMakanan.setVisibility(View.VISIBLE);
        srMakanan.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFoodByKategoriList(List<MakananData> foodNewsList) {
        rvMakanan.setLayoutManager(new LinearLayoutManager(this));
        rvMakanan.setAdapter(new MakananAdapter(MakananAdapter.TYPE_4, this, foodNewsList));
    }

    @Override
    public void showFailurMessage(String msg) {
        srMakanan.setVisibility(View.VISIBLE);
        rvMakanan.setVisibility(View.GONE);
        pbLoading.setVisibility(View.GONE);
        txtInfo.setText(msg);
    }
}
