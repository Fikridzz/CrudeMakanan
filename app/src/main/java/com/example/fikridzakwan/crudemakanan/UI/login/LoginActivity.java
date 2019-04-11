package com.example.fikridzakwan.crudemakanan.UI.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fikridzakwan.crudemakanan.Model.Login.LoginData;
import com.example.fikridzakwan.crudemakanan.R;
import com.example.fikridzakwan.crudemakanan.UI.main.MainActivity;
import com.example.fikridzakwan.crudemakanan.UI.register.RegisterActivity;
import com.example.fikridzakwan.crudemakanan.Utilts.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginConstract.View{

    @BindView(R.id.edt_username)
    EditText edtUsername;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.button_login)
    Button buttonLogin;
    @BindView(R.id.text_create_account)
    TextView textCreateAccount;

    private ProgressDialog progressDialog;
    private LoginPresenter loginPresenter = new LoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        textCreateAccount.setPaintFlags(textCreateAccount.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        loginPresenter.cekLogin(this);
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
    public void loginSuccess(String msg, LoginData loginData) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        // Menyimpan data ke dalam Shared Preference
        loginPresenter.saveDataUser(this, loginData);

        LoginData mLoginData = new LoginData();

        mLoginData.setId_user(loginData.getId_user());
        mLoginData.setAlamat(loginData.getAlamat());
        mLoginData.setJenkel(loginData.getJenkel());
        mLoginData.setNama_user(loginData.getNama_user());
        mLoginData.setPassword(loginData.getPassword());
        mLoginData.setUsername(loginData.getUsername());
        mLoginData.setLevel(loginData.getLevel());
        mLoginData.setNo_telpl(loginData.getNo_telpl());

        startActivity(new Intent(this, MainActivity.class).putExtra(Constants.KEY_LOGIN, mLoginData));
        finish();
    }

    @Override
    public void loginFailure(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void usernameError(String msg) {
        edtUsername.setError(msg);
        edtUsername.setFocusable(true);
    }

    @Override
    public void passwordError(String msg) {
        edtPassword.setError(msg);
        edtPassword.setFocusable(true);

    }

    @Override
    public void isLogin() {
        // Berpindah halman apabila user sudah login
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @OnClick({R.id.button_login, R.id.text_create_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                loginPresenter.doLogin(edtUsername.getText().toString(), edtPassword.getText().toString());
                break;
            case R.id.text_create_account:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}
