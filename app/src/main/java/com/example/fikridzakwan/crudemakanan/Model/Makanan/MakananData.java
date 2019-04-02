package com.example.fikridzakwan.crudemakanan.Model.Makanan;

import com.google.gson.annotations.SerializedName;

public class MakananData {

    @SerializedName("id_makanan")
    private String idMakanan;

    @SerializedName("id_user")
    private String idUser;

    @SerializedName("id_kategori")
    private String idKategori;

    @SerializedName("nama_makanan")
    private String namaMakanan;

    @SerializedName("desc_makanan")
    private String descMakanan;

    @SerializedName("foto_makanan")
    private String fotoMakanan;

    @SerializedName("insert_time")
    private String insertTime;

    @SerializedName("view")
    private String view;

    @SerializedName("nama_user")
    private String namaUser;

    @SerializedName("nama_kategori")
    private String namaKategori;

    @SerializedName("url_makanan")
    private String urlMakanan;

    public String getIdMakanan() {
        return idMakanan;
    }

    public void setIdMakanan(String idMakanan) {
        this.idMakanan = idMakanan;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdKategori() {
        return idKategori;
    }

    public void setIdKategori(String idKategori) {
        this.idKategori = idKategori;
    }

    public String getNamaMakanan() {
        return namaMakanan;
    }

    public void setNamaMakanan(String namaMakanan) {
        this.namaMakanan = namaMakanan;
    }

    public String getDescMakanan() {
        return descMakanan;
    }

    public void setDescMakanan(String descMakanan) {
        this.descMakanan = descMakanan;
    }

    public String getFotoMakanan() {
        return fotoMakanan;
    }

    public void setFotoMakanan(String fotoMakanan) {
        this.fotoMakanan = fotoMakanan;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public String getUrlMakanan() {
        return urlMakanan;
    }

    public void setUrlMakanan(String urlMakanan) {
        this.urlMakanan = urlMakanan;
    }
}
