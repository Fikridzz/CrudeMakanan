package com.example.fikridzakwan.crudemakanan.UI.Fragment.makananbyuser;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fikridzakwan.crudemakanan.Data.adapter.MakananAdapter;
import com.example.fikridzakwan.crudemakanan.Model.Makanan.MakananData;
import com.example.fikridzakwan.crudemakanan.R;
import com.example.fikridzakwan.crudemakanan.UI.MakananByKategori.MakananByKategoriConstract;
import com.example.fikridzakwan.crudemakanan.Utilts.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MakananByUserFragment extends Fragment implements MakananByUserConstract.View {


    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.txt_info)
    TextView txtInfo;
    @BindView(R.id.rl_progress_by_user)
    RelativeLayout rlProgressByUser;
    @BindView(R.id.rv_makanan)
    RecyclerView rvMakanan;
    @BindView(R.id.sr_makanan_by_user)
    SwipeRefreshLayout srMakananByUser;
    Unbinder unbinder;

    private MakananByUserPresenter mMakananByUserPresenter = new MakananByUserPresenter(this);
    private String idUser;
;
    public MakananByUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_makanan_by_user, container, false);
        unbinder = ButterKnife.bind(this, view);

        // Megambil iduser dari shared preference
        SharedPreferences pref =  getContext().getSharedPreferences(Constants.preference_name,0);

        // Memasukan iduser yang sudah diambil dari shared pref
        idUser = pref.getString(Constants.KEY_USER_ID,"");

        // Merequest data makanan by user
        mMakananByUserPresenter.getListFoodByUser(idUser);

        srMakananByUser.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srMakananByUser.setRefreshing(false);
                mMakananByUserPresenter.getListFoodByUser(idUser);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showProgress() {
        rlProgressByUser.setVisibility(View.VISIBLE);
        srMakananByUser.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        rlProgressByUser.setVisibility(View.GONE);
        srMakananByUser.setVisibility(View.VISIBLE);
        rvMakanan.setVisibility(View.VISIBLE);
    }

    @Override
    public void showListFoodByUser(List<MakananData> foodByUserList) {
        rvMakanan.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMakanan.setAdapter(new MakananAdapter(MakananAdapter.TYPE_5, getContext(), foodByUserList));
    }

    @Override
    public void showFailurMessage(String msg) {
        srMakananByUser.setVisibility(View.VISIBLE);
        rlProgressByUser.setVisibility(View.VISIBLE);
        rvMakanan.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.VISIBLE);
        txtInfo.setText(msg);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMakananByUserPresenter.getListFoodByUser(idUser);
    }
}
