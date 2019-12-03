package com.jtarini.zupcryptowatcher.global.util

import com.jtarini.zupcryptowatcher.BuildConfig

class Constants private constructor() {

    companion object {

        const val API_HEADER_NAME = "X-CMC_PRO_API_KEY"
        const val API_URL = BuildConfig.API_URL
        const val API_KEY = BuildConfig.API_KEY
        
    }
}