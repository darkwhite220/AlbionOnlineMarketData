package earth.darkwhite.albiononlinemarketdata.ui.screens.results

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import earth.darkwhite.albiononlinemarketdata.R
import earth.darkwhite.albiononlinemarketdata.domain.model.ItemData
import earth.darkwhite.albiononlinemarketdata.domain.model.QualityData
import earth.darkwhite.albiononlinemarketdata.ui.components.largePadValue
import earth.darkwhite.albiononlinemarketdata.ui.components.topbar.ResultsCenteredTopAppBar
import earth.darkwhite.albiononlinemarketdata.ui.screens.results.components.ResultItem
import earth.darkwhite.albiononlinemarketdata.ui.screens.results.components.Results
import earth.darkwhite.albiononlinemarketdata.ui.screens.results.components.ResultsInfoDialog
import earth.darkwhite.albiononlinemarketdata.ui.theme.AlbionOnlineMarketDataTheme

@Composable
fun ResultsScreen(
  onBackClick: () -> Unit,
  viewModel: ResultsViewModel = hiltViewModel()
) {
  val results by viewModel.resultsUiState.collectAsStateWithLifecycle()
  ResultsContent(
    results = results,
    onRefreshClick = viewModel::fetchPrices,
    onBackClick = onBackClick
  )
}

@Composable
fun ResultsContent(
  results: ResultsUiState,
  onRefreshClick: () -> Unit,
  onBackClick: () -> Unit
) {
  var showInfoDialog by remember { mutableStateOf(false) }
  
  Scaffold(
    topBar = {
      ResultsCenteredTopAppBar(
        titleID = R.string.results,
        resultsUiState = results,
        onInfoClick = { showInfoDialog = true },
        onRefreshClick = onRefreshClick,
        onBackClick = onBackClick
      )
    }
  ) { paddingValues ->
    Results(results) { items: List<ItemData> ->
      LazyColumn(
        modifier = Modifier.padding(paddingValues),
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.spacedBy(largePadValue),
        contentPadding = PaddingValues(vertical = largePadValue)
      ) {
        items(
          items = items,
          key = { item -> item.itemID },
          contentType = { "ResultItem" }
        ) { item ->
          ResultItem(item)
        }
      }
    }
  }
  
  if (showInfoDialog)
    ResultsInfoDialog(onClick = { showInfoDialog = false })
}

@Preview
@Composable
private fun PreviewResultsContent() {
  val data = listOf(
    ItemData(
      itemName = "Sac de l'adepte", itemID = "T4_BAG", itemTier = 4, itemEnchant = 0, qualityData = listOf(
        QualityData(
          buyPrices = listOf(2000, 549, 2373, 2018, 2256, 2114, 0, 2177),
          buyDates = listOf("5H46M", "5H21M","12H16M", "6H56M","1H16M", "41M", "16H1M", "1H36M"),
          sellPrices = listOf(3037, 2746, 3108, 3314, 2932, 2564, 2594, 8900000),
          sellDates = listOf("5H46M", "5H21M","12H16M", "6H56M","1H16M", "41M", "16H1M", "1H36M"),
        )
      )
    ),
    ItemData(
      itemName = "Sac de l'adepte", itemID = "T5_BAG", itemTier = 5, itemEnchant = 0, qualityData = listOf(
        QualityData(
          buyPrices = listOf(2000, 549, 2373, 2018, 2256, 2114, 0, 2177),
          buyDates = listOf("5H46M", "5H21M","12H16M", "6H56M","1H16M", "41M", "16H1M", "1H36M"),
          sellPrices = listOf(3037, 2746, 3108, 3314, 2932, 2564, 2594, 8900000),
          sellDates = listOf("5H46M", "5H21M","12H16M", "6H56M","1H16M", "41M", "16H1M", "1H36M"),
        )
      )
    )
  )
  AlbionOnlineMarketDataTheme {
    ResultsContent(
      results = ResultsUiState.Success(data),
      onRefreshClick = {},
      onBackClick = {}
    )
  }
}

@Preview
@Composable
private fun PreviewResultsContentLoading() {
  AlbionOnlineMarketDataTheme {
    ResultsContent(
      results = ResultsUiState.Loading,
      onRefreshClick = {},
      onBackClick = {}
    )
  }
}

@Preview
@Composable
private fun PreviewResultsContentError() {
  AlbionOnlineMarketDataTheme {
    ResultsContent(
      results = ResultsUiState.Error("Error message..."),
      onRefreshClick = {},
      onBackClick = {}
    )
  }
}