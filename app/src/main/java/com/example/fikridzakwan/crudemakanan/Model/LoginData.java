package com.example.fikridzakwan.crudemakanan.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LoginData implements Parcelable {

    @SerializedName("id_user")
    private String  id_user;

    @SerializedName("nama_user")
    private String nama_user;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("jenkel")
    private String jenkel;

    @SerializedName("no_telp")
    private String no_telpl;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("level")
    private String level;

    public LoginData() {
    }

    protected LoginData(Parcel in) {
        id_user = in.readString();
        nama_user = in.readString();
        alamat = in.readString();
        jenkel = in.readString();
        no_telpl = in.readString();
        username = in.readString();
        password = in.readString();
        level = in.readString();
    }

    public static final Creator<LoginData> CREATOR = new Creator<LoginData>() {
        @Override
        public LoginData createFromParcel(Parcel in) {
            return new LoginData(in);
        }

        @Override
        public LoginData[] newArray(int size) {
            return new LoginData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_user);
        dest.writeString(nama_user);
        dest.writeString(alamat);
        dest.writeString(jenkel);
        dest.writeString(no_telpl);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(level);
    }

    public static Creator<LoginData> getCREATOR() {
        return CREATOR;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJenkel() {
        return jenkel;
    }

    public void setJenkel(String jenkel) {
        this.jenkel = jenkel;
    }

    public String getNo_telpl() {
        return no_telpl;
    }

    public void setNo_telpl(String no_telpl) {
        this.no_telpl = no_telpl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
