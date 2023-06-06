package earth.darkwhite.albiononlinemarketdata.ui.screens.home.components

//import androidx.compose.foundation.lazy.grid.LazyGridItemScopeImpl.animateItemPlacement
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import earth.darkwhite.albiononlinemarketdata.database.model.Category
import earth.darkwhite.albiononlinemarketdata.ui.components.ProgressBar
import earth.darkwhite.albiononlinemarketdata.ui.components.cards.CategoryItemCard
import earth.darkwhite.albiononlinemarketdata.ui.components.largePadValue
import earth.darkwhite.albiononlinemarketdata.ui.components.text.ErrorText
import earth.darkwhite.albiononlinemarketdata.ui.screens.home.HomeEvent
import earth.darkwhite.albiononlinemarketdata.ui.screens.home.HomeUiState
import earth.darkwhite.albiononlinemarketdata.ui.theme.AlbionOnlineMarketDataTheme
import earth.darkwhite.albiononlinemarketdata.util.Constant


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryContent(
  paddingValues: PaddingValues,
  homeUiState: HomeUiState,
  onHomeEvent: (HomeEvent) -> Unit
) {
  CategoryStateContent(
    homeUiState = homeUiState
  ) { categoryList ->
    LazyColumn(
      modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize(),
      contentPadding = PaddingValues(largePadValue),
      verticalArrangement = Arrangement.spacedBy(largePadValue)
    ) {
      items(
        items = categoryList,
        key = { it.index },
        contentType = { Constant.CONTENT_TYPE_CATEGORY_ITEM }
      ) { categoryItem ->
        CategoryItemCard(
          modifier = Modifier.animateItemPlacement(animationSpec = tween()),
          categoryItem = categoryItem,
          onHomeEvent = onHomeEvent
        )
      }
    }
  }
}

@Composable
private fun CategoryStateContent(
  homeUiState: HomeUiState,
  categoryContent: @Composable (List<Category>) -> Unit
) {
  when (homeUiState) {
    HomeUiState.Loading    -> {
      ProgressBar()
    }
    
    is HomeUiState.Error   -> {
      ErrorText(message = homeUiState.message)
    }
    
    is HomeUiState.Success -> {
      categoryContent(homeUiState.data)
    }
  }
}

@Preview
@Composable
fun PreviewCategoryContent() {
  val data = listOf(
    Category(
      index = 0,
      name = "Category 1",
      items = mutableListOf("T4_BAG", "T5_BAG", "T6_BAG")
    ),
    Category(
      index = 1,
      name = "Category 2",
      items = mutableListOf("T4_BAG", "T5_BAG", "T6_BAG", "T4_BAG", "T5_BAG", "T6_BAG", "T4_BAG", "T5_BAG", "T6_BAG")
    ),
    Category(
      index = 2,
      name = "Category 3",
      items = mutableListOf("T4_BAG", "T5_BAG", "T6_BAG")
    ),
  )
  AlbionOnlineMarketDataTheme {
    CategoryContent(
      paddingValues = PaddingValues(),
      homeUiState = HomeUiState.Success(data),
      onHomeEvent = {}
    )
  }
}

@Preview
@Composable
fun PreviewCategoryContentLoading() {
  AlbionOnlineMarketDataTheme {
    CategoryContent(
      paddingValues = PaddingValues(),
      homeUiState = HomeUiState.Loading,
      onHomeEvent = {}
    )
  }
}

@Preview
@Composable
fun PreviewCategoryContentError() {
  AlbionOnlineMarketDataTheme {
    CategoryContent(
      paddingValues = PaddingValues(),
      homeUiState = HomeUiState.Error("Error message"),
      onHomeEvent = {}
    )
  }
}