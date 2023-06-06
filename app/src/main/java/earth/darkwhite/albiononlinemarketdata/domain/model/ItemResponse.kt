package earth.darkwhite.albiononlinemarketdata.domain.model

import com.google.gson.annotations.SerializedName


data class ItemResponse(
  @SerializedName("buy_price_max")
  val buyPriceMax: Int,
  @SerializedName("buy_price_max_date")
  val buyPriceMaxDate: String,
//  @SerializedName("buy_price_min")
//  val buyPriceMin: Int,
//  @SerializedName("buy_price_min_date")
//  val buyPriceMinDate: String,
  @SerializedName("city")
  val city: String,
  @SerializedName("item_id")
  val itemId: String,
  @SerializedName("quality")
  val quality: Int,
//  @SerializedName("sell_price_max")
//  val sellPriceMax: Int,
//  @SerializedName("sell_price_max_date")
//  val sellPriceMaxDate: String,
  @SerializedName("sell_price_min")
  val sellPriceMin: Int,
  @SerializedName("sell_price_min_date")
  val sellPriceMinDate: String
)
