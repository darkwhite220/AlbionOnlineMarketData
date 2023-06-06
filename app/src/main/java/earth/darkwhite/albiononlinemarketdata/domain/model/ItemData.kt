package earth.darkwhite.albiononlinemarketdata.domain.model

import androidx.compose.runtime.Immutable


@Immutable
data class ItemData(
  val itemName: String,
  val itemID: String,
  val itemTier: Int,
  val itemEnchant: Int,
  val qualityData: List<QualityData>
)

@Immutable
data class QualityData(
  val buyPrices: List<Int>,
  val buyDates: List<String>,
  val sellPrices: List<Int>,
  val sellDates: List<String>,
) {
  fun minBuyPrice():Int = buyPrices.filter { it != 0 }.minOrNull() ?: -1
  fun maxBuyPrice():Int = buyPrices.filter { it != 0 }.maxOrNull() ?: -1
  fun minSellPrice():Int = sellPrices.filter { it != 0 }.minOrNull() ?: -1
  fun maxSellPrice():Int = sellPrices.filter { it != 0 }.maxOrNull() ?: -1
}
