package com.jtarini.zupcryptowatcher.appstart

import android.app.Application
import com.jtarini.zupcryptowatcher.BuildConfig
import com.jtarini.zupcryptowatcher.global.di.apiModule
import com.jtarini.zupcryptowatcher.global.di.serviceModule
import com.jtarini.zupcryptowatcher.global.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import timber.log.Timber

class App: Application() {
  
  override fun onCreate() {
    super.onCreate()
  
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  
    startKoin {
      logger(object: Logger() {
        override fun log(level: Level, msg: MESSAGE) {
          when (level) {
            Level.DEBUG -> Timber.d(msg)
            Level.INFO -> Timber.i(msg)
            Level.ERROR -> Timber.e(msg)
          }
        }
      })
      androidContext(this@App)
      modules(listOf(viewModelModule, serviceModule, apiModule))
    }
  }
  
}