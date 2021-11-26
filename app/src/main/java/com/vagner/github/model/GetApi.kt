package com.vagner.github.model

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
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

        fun gson(): Gson = GsonBuilder().create()


    }
}

interface ServicesApi{


    @GET("repositories")
    fun listAll(@QueryMap filtros : Map<String,String>): Call<Github>

    @GET("repositories")
    fun listAllSortStar(
        @Query("q") language: String,
        @Query("q") name: String,
        @Query("sort") sort: String,
        @Query("page") page: String
    ): Call<Github>

}