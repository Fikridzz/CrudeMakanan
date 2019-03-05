package com.example.fikridzakwan.crudemakanan.UI.Fragment.profile;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.fikridzakwan.crudemakanan.Model.LoginData;
import com.example.fikridzakwan.crudemakanan.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProfileContract.View {


    @BindView(R.id.picture)
    CircleImageView picture;
    @BindView(R.id.fabChoosePic)
    FloatingActionButton fabChoosePic;
    @BindView(R.id.layoutPicture)
    RelativeLayout layoutPicture;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_alamat)
    EditText edtAlamat;
    @BindView(R.id.edt_no_telp)
    EditText edtNoTelp;
    @BindView(R.id.spin_gender)
    Spinner spinGender;
    @BindView(R.id.layoutProfil)
    CardView layoutProfil;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    @BindView(R.id.layoutJenkel)
    CardView layoutJenkel;
    Unbinder unbinder;

    private ProfilePresenter mProfilePresenter = new ProfilePresenter(this);

    private String idUser, nama, alamat, noTelp;
    private int gender;
    private Menu action;

    private int mGender = 0;
    private static final int GENDER_MALE = 1;
    private static final int GENDER_FEMALE = 2;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        // Menampilkan Option di fragment
        setHasOptionsMenu(true);
        // Merequest data yang di kerjakan oleh presenter
        mProfilePresenter.getData(getContext());
        return view;
    }

    @Override
    public void showDataUser(LoginData loginData) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_logout)
    public void onViewClicked() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_editor, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit:

                edtMode();

                return true;
            case R.id.menu_save:

                if (TextUtils.isEmpty(idUser)) {
                    if (TextUtils.isEmpty(edtName.getText().toString()) ||
                            TextUtils.isEmpty(edtAlamat.getText().toString()) ||
                            TextUtils.isEmpty(edtNoTelp.getText().toString())) {
                        // Menampilkan alert dialog untuk memberi hu user tidak boleh ada yang field yang kosong
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                        alertDialog.setMessage("Please complete the field");
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        alertDialog.show();

                    } else {
                        readMode();
                    }
                } else {
                    readMode();
                }

                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("RestrictedApi")
    private void readMode() {

        edtName.setFocusableInTouchMode(false);
        edtAlamat.setFocusableInTouchMode(false);
        edtNoTelp.setFocusableInTouchMode(false);
        edtName.setFocusable(false);
        edtAlamat.setFocusable(false);
        edtNoTelp.setFocusable(false);

        spinGender.setEnabled(false);
        fabChoosePic.setVisibility(View.INVISIBLE);

        action.findItem(R.id.menu_edit).setVisible(true);
        action.findItem(R.id.menu_save).setVisible(false);
    }

    @SuppressLint("RestrictedApi")
    private void edtMode() {

        edtName.setFocusableInTouchMode(true);
        edtAlamat.setFocusableInTouchMode(true);
        edtNoTelp.setFocusableInTouchMode(true);

        spinGender.setEnabled(true);
        fabChoosePic.setVisibility(View.VISIBLE);

        action.findItem(R.id.menu_edit).setVisible(false);
        action.findItem(R.id.menu_save).setVisible(true);
    }
}
