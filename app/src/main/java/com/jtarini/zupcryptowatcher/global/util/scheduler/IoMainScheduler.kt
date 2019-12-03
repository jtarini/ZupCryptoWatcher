package com.jtarini.zupcryptowatcher.global.util.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class IoMainScheduler<T>: BaseScheduler<T>(Schedulers.io(), AndroidSchedulers.mainThread())
