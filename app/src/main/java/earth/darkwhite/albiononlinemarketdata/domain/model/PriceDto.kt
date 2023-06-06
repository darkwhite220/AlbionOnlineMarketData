package earth.darkwhite.albiononlinemarketdata.domain.model

import earth.darkwhite.albiononlinemarketdata.database.model.GameItemSample
import earth.darkwhite.albiononlinemarketdata.util.Constant.CITIES_COUNT
import earth.darkwhite.albiononlinemarketdata.util.Constant.DEFAULT_VALUE
import earth.darkwhite.albiononlinemarketdata.util.Constant.MAX_QUALITY
import earth.darkwhite.albiononlinemarketdata.util.Util
import earth.darkwhite.albiononlinemarketdata.util.Util.convertRawTimeToLongMinutes
import earth.darkwhite.albiononlinemarketdata.util.Util.extractItemEnchant

fun itemResponseDto(rawItems: List<ItemResponse>, namesList: List<GameItemSample>): List<ItemData> {
  val mainList: MutableList<ItemData> = mutableListOf()
  Util.setCurrentUtcDate()
  
  val sortedData = rawItems.sortedBy { it.quality }
//  sortedData.forEach { println("${it.itemId} ${it.city}, ${it.quality}") }
  // Data after sort
  /*
T4_BAG 5003, 1
18:08:02.344  I  T4_BAG Black Market, 1
18:08:02.344  I  T4_BAG Bridgewatch, 1
18:08:02.344  I  T4_BAG Caerleon, 1
18:08:02.344  I  T4_BAG Fort Sterling, 1
18:08:02.344  I  T4_BAG Lymhurst, 1
18:08:02.345  I  T4_BAG Martlock, 1
18:08:02.345  I  T4_BAG Thetford, 1
18:08:02.345  I  T5_BAG 5003, 1
18:08:02.345  I  T5_BAG Black Market, 1
18:08:02.345  I  T5_BAG Bridgewatch, 1
18:08:02.345  I  T5_BAG Caerleon, 1
18:08:02.345  I  T5_BAG Fort Sterling, 1
18:08:02.345  I  T5_BAG Lymhurst, 1
18:08:02.345  I  T5_BAG Martlock, 1
18:08:02.345  I  T5_BAG Thetford, 1
18:08:02.345  I  T8_MILK 5003, 1
18:08:02.345  I  T8_MILK Black Market, 1
18:08:02.345  I  T8_MILK Bridgewatch, 1
18:08:02.345  I  T8_MILK Caerleon, 1
18:08:02.345  I  T8_MILK Fort Sterling, 1
18:08:02.345  I  T8_MILK Lymhurst, 1
18:08:02.346  I  T8_MILK Martlock, 1
18:08:02.346  I  T8_MILK Thetford, 1
18:08:02.346  I  T4_BAG 5003, 2
18:08:02.346  I  T4_BAG Black Market, 2
18:08:02.346  I  T4_BAG Bridgewatch, 2
*/
  
  val allQualities = sortedData[sortedData.lastIndex].quality == MAX_QUALITY
  val leapCount = if (allQualities) CITIES_COUNT * MAX_QUALITY else CITIES_COUNT // 40 or 8
  val itemsCount = sortedData.size / leapCount
  val skipCount = itemsCount * CITIES_COUNT // start with item X quality 1 to 5, Then item X2...
//  println("itemsCount $itemsCount")
  
  // Loop as many items available $itemCount
  // Finish one item with all qualities then on to the next one
  for (e in 0 until skipCount step CITIES_COUNT) {
    val itemId: String = sortedData[e].itemId
    val itemName: String = namesList.find { it.itemID == itemId }?.name ?: DEFAULT_VALUE
    val itemTier: Int = Util.extractItemTier(itemId)
    val itemEnchant: Int = extractItemEnchant(itemId)
    
    val priceData = getQualityData(e, sortedData, skipCount)
    
    mainList.add(
      ItemData(
        itemName = itemName,
        itemID = itemId,
        itemTier = itemTier,
        itemEnchant = itemEnchant,
        qualityData = priceData
      )
    )
//    println("${mainList.size}")
//    println("${mainList[0].itemId}, ${mainList[0].qualitiesData.size}")
//    println("${mainList[0].qualitiesData[0].buyPrices.size}")
//    println("${mainList[0].qualitiesData[0].buyPrices[0]}")
  }
  return mainList
}

/**
 * Return a list of all qualities prices
 */
private fun getQualityData(
  e: Int,
  sortedData: List<ItemResponse>,
  skipCount: Int
): List<QualityData> {
  val qualityData: MutableList<QualityData> = mutableListOf()
  
  for (i in e until sortedData.size step skipCount) {
    qualityData.add(
      QualityData(
        buyPrices = getPrices(data = sortedData, i = i, isSellPrices = false),
        buyDates = getDates(data = sortedData, i = i, isSellDates = false),
        sellPrices = getPrices(data = sortedData, i = i, isSellPrices = true),
        sellDates = getDates(data = sortedData, i = i, isSellDates = true)
      )
    )
  }
  return qualityData
}

fun getPrices(data: List<ItemResponse>, i: Int, isSellPrices: Boolean): List<Int> {
  val priceList: MutableList<Int> = mutableListOf()
  for (city in 0 until CITIES_COUNT) {
    if (isSellPrices) priceList.add(data[i + city].sellPriceMin)
    else priceList.add(data[i + city].buyPriceMax)
  }
  return priceList
}

fun getDates(data: List<ItemResponse>, i: Int, isSellDates: Boolean): List<String> {
  val datesList: MutableList<String> = mutableListOf()
  for (city in 0 until CITIES_COUNT) {
    if (isSellDates) datesList.add(
      Util.convertLongMinutesTimeToString(
        convertRawTimeToLongMinutes(data[i + city].sellPriceMinDate)
      )
    )
    else datesList.add(
      Util.convertLongMinutesTimeToString(
        convertRawTimeToLongMinutes(data[i + city].buyPriceMaxDate)
      )
    )
  }
  return datesList
}
