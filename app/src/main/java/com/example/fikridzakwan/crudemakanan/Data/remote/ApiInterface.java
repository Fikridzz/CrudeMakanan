package com.example.fikridzakwan.crudemakanan.Data.remote;

import com.example.fikridzakwan.crudemakanan.Model.Upload.UploadResponse;
import com.example.fikridzakwan.crudemakanan.Model.Login.LoginRespone;
import com.example.fikridzakwan.crudemakanan.Model.Makanan.MakananResponse;
import com.example.fikridzakwan.crudemakanan.Model.DetailMakanan.DetailMakananResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    // Membuat endpoint login
    @FormUrlEncoded
    @POST("loginuser.php")
    Call<LoginRespone> loiginUser
    (@Field("username") String username, @Field("password") String password);

    // Membuat endpoint register
    @FormUrlEncoded
    @POST("registeruser.php")
    Call<LoginRespone> registerUser
    (@Field("username") String username,
     @Field("password") String password,
     @Field("namauser") String namauser,
     @Field("alamat") String alamat,
     @Field("jenkel") String jenkel,
     @Field("notelp") String notelp,
     @Field("level") String level);

    @FormUrlEncoded
    @POST("updateuser.php")
    Call<LoginRespone>  updateUser
            (@Field("iduser") int iduser,
             @Field("namauser") String namauser,
             @Field("alamat") String alamat,
             @Field("jenkel") String jenkel,
             @Field("notelp") String notelp);

    // Mengambil data kategori
    @GET("getkategori.php")
    Call<MakananResponse> getKategoriMakanan();

    // Mengambil data baru
    @GET("getmakananbaru.php")
    Call<MakananResponse> getMakananBaru();

    // Mengambil data populer
    @GET("getmakananpopuler.php")
    Call<MakananResponse> getMakananPopuler();

    // Mengupload makanan
    @Multipart
    @POST("uploadmakanan.php")
    Call<UploadResponse>  uploadMakanan(
            @Part("iduser") int idUser,
            @Part("idkategori") int idKategori,
            @Part("namamakanan") RequestBody namaMakanan,
            @Part("descmakanan") RequestBody descMakanan,
            @Part("timeinsert") RequestBody timeinsert,
            @Part MultipartBody.Part image
            );

    // Mengambil data detail makanan
    @GET("getdetailmakanan.php")
    Call<DetailMakananResponse> getDetailMakanan(@Query("idmakanan") int idMakanan);

    // Mengambil data makanana bedasarkan kategori
    @GET("getmakananbykategori.php")
    Call<MakananResponse> getDetailMakananByKategori(@Query("idkategori") int idKategori);

    // Megambi data makanan bedasrakn iduser
    @GET("getmakananbyuser.php")
    Call<MakananResponse> getMakananByUser(@Query("iduser") int idUser);

}
