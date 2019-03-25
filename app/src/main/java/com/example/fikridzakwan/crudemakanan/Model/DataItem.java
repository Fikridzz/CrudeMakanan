package com.example.fikridzakwan.crudemakanan.Model;

import com.google.gson.annotations.SerializedName;

public class DataItem {

    @SerializedName("id_makanan")
    private String id_makanan;

    @SerializedName("id_user")
    private String id_user;

    @SerializedName("id_kategori")
    private String id_kategori;

    @SerializedName("nama_makanan")
    private String nama_makanan;

    @SerializedName("desc_makanan")
    private String desc_makanan;

    @SerializedName("foto_makanan")
    private String foto_makanan;

    @SerializedName("insert_time")
    private String insert_time;

    @SerializedName("view")
    private String view;

    @SerializedName("nama_user")
    private String nama_user;

    @SerializedName("nama_kategori")
    private String nama_kategori;

    @SerializedName("url_makanan")
    private String url_makanan;

    public String getId_makanan() {
        return id_makanan;
    }

    public void setId_makanan(String id_makanan) {
        this.id_makanan = id_makanan;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getNama_makanan() {
        return nama_makanan;
    }

    public void setNama_makanan(String nama_makanan) {
        this.nama_makanan = nama_makanan;
    }

    public String getDesc_makanan() {
        return desc_makanan;
    }

    public void setDesc_makanan(String desc_makanan) {
        this.desc_makanan = desc_makanan;
    }

    public String getFoto_makanan() {
        return foto_makanan;
    }

    public void setFoto_makanan(String foto_makanan) {
        this.foto_makanan = foto_makanan;
    }

    public String getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(String insert_time) {
        this.insert_time = insert_time;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    public String getUrl_makanan() {
        return url_makanan;
    }

    public void setUrl_makanan(String url_makanan) {
        this.url_makanan = url_makanan;
    }
}
