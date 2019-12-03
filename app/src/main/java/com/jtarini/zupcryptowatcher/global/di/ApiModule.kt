package com.jtarini.zupcryptowatcher.global.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jtarini.zupcryptowatcher.BuildConfig
import com.jtarini.zupcryptowatcher.networking.ApplicationApi
import com.jtarini.zupcryptowatcher.global.util.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

val apiModule = module {
  
  single {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = if (BuildConfig.DEBUG)
      HttpLoggingInterceptor.Level.BODY
    else
      HttpLoggingInterceptor.Level.NONE
  
    loggingInterceptor
  }
  
  single {
    val client = OkHttpClient.Builder()
    client.addInterceptor(get<HttpLoggingInterceptor>())
  
    client.addInterceptor(object: Interceptor {
      @Throws(IOException::class)
      override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val newRequest = chain.request().newBuilder()
          .addHeader(Constants.API_HEADER_NAME, Constants.API_KEY)
          .build()
        return chain.proceed(newRequest)
      }
    })
  
    client.build()
  }
  
  single {
    GsonBuilder()
      .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
      .setPrettyPrinting()
      .create()
  }
  
  single {
    Retrofit.Builder()
      .baseUrl(Constants.API_URL)
      .client(get<OkHttpClient>())
      .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .build()
  }
  
  single {
    val retrofit: Retrofit = get()
    retrofit.create(ApplicationApi::class.java)
  }
  
}