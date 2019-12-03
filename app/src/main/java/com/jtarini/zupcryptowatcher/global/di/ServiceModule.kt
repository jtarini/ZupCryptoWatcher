package com.jtarini.zupcryptowatcher.global.di

import com.jtarini.zupcryptowatcher.service.CoinService
import org.koin.dsl.module

val serviceModule = module {
  
  single { CoinService(get()) }
  
}