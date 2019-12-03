package com.jtarini.zupcryptowatcher.model

data class Coin(
  
  val id: Long = 0,
  val name: String = "",
  val symbol: String = "",
  val cmcRank: Int = 0,
  val coinQuote: CoinQuote? = null

)