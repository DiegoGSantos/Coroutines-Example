package com.diego.coroutinessample.restClient

import com.diego.coroutinesexample.Constants
import com.diego.coroutinesexample.restClient.BasicAuthInterceptor
import com.diego.coroutinesexample.restClient.response.Event
import com.diego.coroutinesexample.restClient.response.Token
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory

interface Service {
    @retrofit2.http.GET("oauth2/token")
    fun requestToken() : Call<Token>

    @retrofit2.http.GET("publico/eventos")
    fun getEvents(@retrofit2.http.Header("authorization") auth: String) : Call<List<Event>>

    companion object Factory {
        fun create(): Service {

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val httpClient = OkHttpClient.Builder()

            httpClient.addInterceptor(logging)
            httpClient.addInterceptor(BasicAuthInterceptor(Constants.CLIENT_ID, Constants.CLIENT_PASSWORD))

            val retrofit = retrofit2.Retrofit.Builder()
                    .addCallAdapterFactory(retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
                    .client(httpClient.build())
                    .baseUrl(Constants.BASE_URL)
                    .build()

            return retrofit.create(Service::class.java);
        }
    }
}