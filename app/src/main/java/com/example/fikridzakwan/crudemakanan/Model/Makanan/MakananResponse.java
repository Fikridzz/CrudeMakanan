package com.example.fikridzakwan.crudemakanan.Model.Makanan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MakananResponse {

    @SerializedName("result")
    private int result;

    @SerializedName("message")
    private String message;

    @SerializedName("url")
    private String url;

    @SerializedName("name")
    private String name;

    @SerializedName("data")
    private List<MakananData> makananDataList;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;

    }

    public List<MakananData> getMakananDataList() {
        return makananDataList;
    }

    public void setMakananDataList(List<MakananData> makananDataList) {
        this.makananDataList = makananDataList;
    }
}
