package com.example.fikridzakwan.crudemakanan.Data.remote;

import com.example.fikridzakwan.crudemakanan.Utilts.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;
    public static Retrofit getClient() {

        // Membuat object
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        // Set log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //Membuat object httpClient
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(logging);

        retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
        return retrofit;
    }
}
