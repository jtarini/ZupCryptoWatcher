package com.jtarini.zupcryptowatcher.global.util.scheduler

object SchedulerUtils {
    
    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }
    
}
