package com.example.fikridzakwan.crudemakanan.Data.remote;

import com.example.fikridzakwan.crudemakanan.Model.LoginRespone;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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
}
