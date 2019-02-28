package com.example.fikridzakwan.crudemakanan.Model;

import com.google.gson.annotations.SerializedName;

public class LoginData {

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

    public String getId_user() {
        return id_user;
    }

    public String getNama_user() {
        return nama_user;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getJenkel() {
        return jenkel;
    }

    public String getNo_telpl() {
        return no_telpl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getLevel() {
        return level;
    }
}
