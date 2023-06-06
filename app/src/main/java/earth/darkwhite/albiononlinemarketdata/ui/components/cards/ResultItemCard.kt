package earth.darkwhite.albiononlinemarketdata.ui.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import earth.darkwhite.albiononlinemarketdata.R
import earth.darkwhite.albiononlinemarketdata.domain.model.ItemData
import earth.darkwhite.albiononlinemarketdata.domain.model.QualityData
import earth.darkwhite.albiononlinemarketdata.ui.components.SearchResultItem
import earth.darkwhite.albiononlinemarketdata.ui.components.TableCell
import earth.darkwhite.albiononlinemarketdata.ui.components.cardElevation
import earth.darkwhite.albiononlinemarketdata.ui.components.largePadValue
import earth.darkwhite.albiononlinemarketdata.ui.components.mediumPadValue
import earth.darkwhite.albiononlinemarketdata.ui.theme.AlbionOnlineMarketDataTheme
import earth.darkwhite.albiononlinemarketdata.ui.theme.greenColor
import earth.darkwhite.albiononlinemarketdata.ui.theme.redColor
import earth.darkwhite.albiononlinemarketdata.util.Constant.DEFAULT_VALUE

@Composable
fun ResultItemCard(item: ItemData, qualityIndex: Int) {
  Card(elevation = CardDefaults.cardElevation(defaultElevation = cardElevation)) {
    item.apply {
      SearchResultItem(
        itemID = itemID,
        itemName = itemName,
        modifier = Modifier.padding(largePadValue),
        qualityIndex = qualityIndex,
        showQualityText = true
      )
      
      TableHeader()
      
      TableData(qualityIndex)
      
      Spacer(modifier = Modifier.height(mediumPadValue))
    }
  }
}

@Composable
private fun ItemData.TableData(qualityIndex: Int) {
  citiesNames.forEachIndexed { i, textId ->
    Row(
      modifier = Modifier.padding(horizontal = mediumPadValue),
      horizontalArrangement = Arrangement.spacedBy(mediumPadValue)
    ) {
      qualityData[qualityIndex].apply {
        TableCell(text = stringResource(id = textId), weight = 1f, align = TextAlign.Start)
        TableCell(text = sellDates[i])
        TableCell(
          text = if (sellPrices[i] != 0) "${sellPrices[i]}" else DEFAULT_VALUE,
          color = if (sellPrices[i] == minSellPrice()) greenColor
          else if (sellPrices[i] == maxSellPrice()) redColor
          else MaterialTheme.colorScheme.onPrimaryContainer,
          bold = sellPrices[i] != 0
        )
        TableCell(text = buyDates[i])
        TableCell(
          text = if (buyPrices[i] != 0) "${buyPrices[i]}" else DEFAULT_VALUE,
          color = if (buyPrices[i] == minBuyPrice()) greenColor
          else if (buyPrices[i] == maxBuyPrice()) redColor
          else MaterialTheme.colorScheme.onPrimaryContainer,
          bold = buyPrices[i] != 0
        )
      }
    }
    if (i.plus(1) < citiesNames.size)
      Divider(modifier = Modifier.padding(horizontal = largePadValue))
  }
}

@Composable
private fun TableHeader() {
  Row(
    modifier = Modifier.padding(horizontal = mediumPadValue),
    horizontalArrangement = Arrangement.spacedBy(mediumPadValue)
  ) {
    tableHeader.forEachIndexed { i, textID ->
      TableCell(
        text = stringResource(id = textID),
        bold = true,
        weight = if (i == 0) 1f else .8f,
        align = if (i == 0) TextAlign.Start else TextAlign.End,
        fontSizeRange = FontSizeRange(
          min = 10.sp,
          max = 14.sp,
        )
      )
    }
  }
}

private val tableHeader = listOf(
  R.string.city,
  R.string.update,
  R.string.sell,
  R.string.update,
  R.string.buy
)
private val citiesNames = listOf(
  R.string.brecilien,
  R.string.blackmarket,
  R.string.bridgewatch,
  R.string.caerleon,
  R.string.fortsterling,
  R.string.lymhurst,
  R.string.martlock,
  R.string.thetford
)

data class FontSizeRange(
  val min: TextUnit,
  val max: TextUnit,
  val step: TextUnit = DEFAULT_TEXT_STEP,
) {
  init {
    require(min < max) { "min should be less than max, $this" }
    require(step.value > 0) { "step should be greater than 0, $this" }
  }
  
  companion object {
    private val DEFAULT_TEXT_STEP = 1.sp
  }
}

@Preview
@Composable
private fun PreviewResultItemCard() {
  val item = ItemData(
    itemName = "Sac de l'adepte", itemID = "T4_BAG", itemTier = 4, itemEnchant = 0, qualityData = listOf(
      QualityData(
        buyPrices = listOf(2000, 549, 2373, 2018, 2256, 2114, 0, 2177),
        buyDates = listOf("5H46M", "5H21M", "12H16M", "6H56M", "1H16M", "41M", "16H1M", "1H36M"),
        sellPrices = listOf(3037, 2746, 3108, 3314, 2932, 2564, 2594, 8900000),
        sellDates = listOf("5H46M", "5H21M", "12H16M", "6H56M", "1H16M", "41M", "16H1M", "1H36M"),
      )
    )
  )
  AlbionOnlineMarketDataTheme {
    ResultItemCard(
      item = item,
      qualityIndex = 0
    )
  }
}