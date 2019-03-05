package com.example.fikridzakwan.crudemakanan.UI.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.fikridzakwan.crudemakanan.Model.LoginData;
import com.example.fikridzakwan.crudemakanan.R;
import com.example.fikridzakwan.crudemakanan.UI.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {

    @BindView(R.id.edt_nama_user)
    EditText edtNamaUser;
    @BindView(R.id.edt_username)
    EditText edtUsername;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.edt_alamat)
    EditText edtAlamat;
    @BindView(R.id.rb_laki_laki)
    RadioButton rbLakiLaki;
    @BindView(R.id.rb_perempuan)
    RadioButton rbPerempuan;
    @BindView(R.id.edt_notelp)
    EditText edtNotelp;
    @BindView(R.id.rb_admin)
    RadioButton rbAdmin;
    @BindView(R.id.rb_user)
    RadioButton rbUser;
    @BindView(R.id.btn_register)
    Button btnRegister;

    private ProgressDialog progressDialog;
    private RegisterPresenter mRegisterPresenter = new RegisterPresenter(this);
    private String jenkel;
    private String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        // Set jenkel dan level default
        setRadio();
    }

    private void setRadio() {
        if (rbAdmin.isChecked()) {
            level = "1";
        } else {
            level = "0";
        }

        if (rbLakiLaki.isChecked()) {
            jenkel = "L";
        } else {
            jenkel = "P";
        }
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Get Data");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();

    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showRegisterSuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @OnClick({R.id.rb_laki_laki, R.id.rb_perempuan, R.id.rb_admin, R.id.rb_user, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_laki_laki:
                jenkel = "L";
                break;
            case R.id.rb_perempuan:
                jenkel = "P";
                break;
            case R.id.rb_admin:
                level = "1";
                break;
            case R.id.rb_user:
                level = "0";
                break;
            case R.id.btn_register:
                // Membuat object model call LoginData
                LoginData mLoginData = new LoginData();

                // Memasukan data ke dalam model LoginData
                mLoginData.setNama_user(edtNamaUser.getText().toString());
                mLoginData.setUsername(edtUsername.getText().toString());
                mLoginData.setPassword(edtPassword.getText().toString());
                mLoginData.setAlamat(edtAlamat.getText().toString());
                mLoginData.setNo_telpl(edtNotelp.getText().toString());
                mLoginData.setJenkel(jenkel);
                mLoginData.setLevel(level);

                // Kirimkan data ke Presenter
                mRegisterPresenter.doRegisterUser(mLoginData);
                break;
        }
    }
}
