package com.jtarini.zupcryptowatcher.model

import com.google.gson.annotations.SerializedName

class CoinsResponse(
  
  @SerializedName("data")
  var coins: List<Coin>

)