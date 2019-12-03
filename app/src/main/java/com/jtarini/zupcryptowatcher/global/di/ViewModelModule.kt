package com.jtarini.zupcryptowatcher.global.di

import com.jtarini.zupcryptowatcher.scene.coin.CoinViewModel
import com.jtarini.zupcryptowatcher.scene.select.SelectViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
  
  viewModel { SelectViewModel(get()) }
  viewModel { CoinViewModel() }
  
}