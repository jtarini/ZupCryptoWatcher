package com.jtarini.zupcryptowatcher.scene.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel: ViewModel() {
  
  val errorMessage: MutableLiveData<String> = MutableLiveData()
  
  private val compositeDisposable = CompositeDisposable()
  
  fun addSubscription(disposable: Disposable) {
    compositeDisposable.add(disposable)
  }
  
  fun clear() {
    if (!compositeDisposable.isDisposed) {
      compositeDisposable.clear()
    }
  }
  
}