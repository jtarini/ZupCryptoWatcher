package com.jtarini.zupcryptowatcher.model

data class CoinQuoteUSD(
  
  val price: Double = 0.0,
  val volume24h: Double = 0.0,
  val percentChange1h: Double = 0.0,
  val percentChange24h: Double = 0.0,
  val percentChange7d: Double = 0.0,
  val marketCap: Double = 0.0

)