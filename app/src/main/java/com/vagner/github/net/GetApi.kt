package com.vagner.github.model

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class GetApi {

    companion object {
        fun Retrofit(path: String): Retrofit {
            return Retrofit.Builder().baseUrl(path).client(client())
                .addConverterFactory(GsonConverterFactory.create()).build()
        }

        fun client(): OkHttpClient {
            return OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS).build()
        }
    }
}
