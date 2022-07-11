package com.example.retrofitexample.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Eldor Turgunov on 11.07.2022.
 * Retrofit Example
 * eldorturgunov777@gmail.com
 */
public class RetrofitInstance {
    public static String BASE_URL = "https://jsonplaceholder.typicode.com/";
    static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
