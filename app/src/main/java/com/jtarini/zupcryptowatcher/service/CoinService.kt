package com.jtarini.zupcryptowatcher.service

import com.jtarini.zupcryptowatcher.model.CoinsResponse
import com.jtarini.zupcryptowatcher.networking.ApplicationApi
import com.jtarini.zupcryptowatcher.global.util.scheduler.SchedulerUtils
import io.reactivex.Single

class CoinService(private val api: ApplicationApi) {
  
  fun getAllCoinsLatest(): Single<CoinsResponse> {
    return api.getAllCoinsLatest()
      .compose(SchedulerUtils.ioToMain())
  }

}