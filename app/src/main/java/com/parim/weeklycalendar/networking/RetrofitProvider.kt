package com.parim.weeklycalendar.networking
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.parim.weeklycalendar.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(OkhttpClient.client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> createAPI(service: Class<T>): T {
        return retrofit.create(service)
    }
}