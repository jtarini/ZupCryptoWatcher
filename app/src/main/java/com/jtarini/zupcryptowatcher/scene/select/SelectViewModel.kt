package com.jtarini.zupcryptowatcher.scene.select

import androidx.lifecycle.MutableLiveData
import com.jtarini.zupcryptowatcher.model.Coin
import com.jtarini.zupcryptowatcher.scene.base.BaseViewModel
import com.jtarini.zupcryptowatcher.service.CoinService
import timber.log.Timber

class SelectViewModel(private val service: CoinService): BaseViewModel() {

  val items = MutableLiveData<List<Coin>>()
  val lazyItems: MutableLiveData<List<Coin>> by lazy {
    items.also {
      fetchData()
    }
  }
  
  fun fetchData() {
    val disposable = service.getAllCoinsLatest()
      .subscribe({ coinsResponse ->
        items.value = coinsResponse.coins
      }, { t ->
        Timber.e(t)
        errorMessage.value = t.localizedMessage
      })
  
    addSubscription(disposable)
  }
  
}