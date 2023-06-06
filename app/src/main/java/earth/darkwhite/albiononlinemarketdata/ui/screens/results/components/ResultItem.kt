package earth.darkwhite.albiononlinemarketdata.ui.screens.results.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import earth.darkwhite.albiononlinemarketdata.domain.model.ItemData
import earth.darkwhite.albiononlinemarketdata.domain.model.QualityData
import earth.darkwhite.albiononlinemarketdata.ui.components.cards.ResultItemCard
import earth.darkwhite.albiononlinemarketdata.ui.components.largePadValue
import earth.darkwhite.albiononlinemarketdata.ui.components.xLargePadValue
import earth.darkwhite.albiononlinemarketdata.ui.theme.AlbionOnlineMarketDataTheme


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ResultItem(item: ItemData) {
  HorizontalPager(
    pageCount = item.qualityData.size,
    state = rememberPagerState(),
    contentPadding = PaddingValues(horizontal = largePadValue),
    pageSpacing = xLargePadValue
  ) { index ->
    ResultItemCard(item, index)
  }
}

@Preview
@Composable
private fun PreviewResultItem() {
  val item = ItemData(
    itemName = "Sac de l'adepte", itemID = "T4_BAG", itemTier = 4, itemEnchant = 0, qualityData = listOf(
      QualityData(
        buyPrices = listOf(2000, 549, 2373, 2018, 2256, 2114, 0, 2177),
        buyDates = listOf("5H46M", "5H21M","12H16M", "6H56M","1H16M", "41M", "16H1M", "1H36M"),
        sellPrices = listOf(3037, 2746, 3108, 3314, 2932, 2564, 2594, 8900000),
        sellDates = listOf("5H46M", "5H21M","12H16M", "6H56M","1H16M", "41M", "16H1M", "1H36M"),
      ),
      QualityData(
        buyPrices = listOf(2000, 549, 2373, 2018, 2256, 2114, 0, 2177),
        buyDates = listOf("5H46M", "5H21M","12H16M", "6H56M","1H16M", "41M", "16H1M", "1H36M"),
        sellPrices = listOf(3037, 2746, 3108, 3314, 2932, 2564, 2594, 8900000),
        sellDates = listOf("5H46M", "5H21M","12H16M", "6H56M","1H16M", "41M", "16H1M", "1H36M"),
      )
    )
  )
  AlbionOnlineMarketDataTheme {
    ResultItem(
      item = item
    )
  }
}