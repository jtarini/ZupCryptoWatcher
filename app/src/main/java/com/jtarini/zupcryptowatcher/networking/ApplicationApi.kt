package com.jtarini.zupcryptowatcher.networking

import com.jtarini.zupcryptowatcher.model.Coin
import com.jtarini.zupcryptowatcher.model.CoinsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApplicationApi {
  
  @GET("v1/cryptocurrency/listings/latest")
  fun getAllCoinsLatest(): Single<CoinsResponse>
  
  @GET("v1/cryptocurrency/quotes/latest")
  fun getCoinLatestBySymbol(@Query("symbol") symbol: String): Single<Coin>
  
}