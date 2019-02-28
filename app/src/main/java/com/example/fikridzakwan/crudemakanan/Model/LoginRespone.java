package com.example.fikridzakwan.crudemakanan.Model;

import com.google.gson.annotations.SerializedName;

public class LoginRespone {

    @SerializedName("result")
    private int result;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private String data;

    public int getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }
}
