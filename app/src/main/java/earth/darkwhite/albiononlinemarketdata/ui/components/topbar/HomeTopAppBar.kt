package earth.darkwhite.albiononlinemarketdata.ui.components.topbar

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import earth.darkwhite.albiononlinemarketdata.R
import earth.darkwhite.albiononlinemarketdata.database.model.GameItemSample
import earth.darkwhite.albiononlinemarketdata.ui.components.mediumPadValue
import earth.darkwhite.albiononlinemarketdata.ui.screens.home.components.SearchContent
import earth.darkwhite.albiononlinemarketdata.ui.theme.AlbionOnlineMarketDataTheme
import earth.darkwhite.albiononlinemarketdata.util.Constant
import kotlinx.coroutines.flow.flowOf


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
  searchState: Boolean,
  query: String,
  searchResult: LazyPagingItems<GameItemSample>,
  onActiveSearchState: () -> Unit,
  onSearchStateChange: () -> Unit,
  onQueryChange: (newValue: String) -> Unit,
  onItemClick: (item: GameItemSample) -> Unit,
  onSettingsClick: () -> Unit
) {
  val focusRequester = remember { FocusRequester() }
  val listState = rememberLazyListState()
  val paddingAnimation = animateDpAsState(targetValue = if (searchState) 0.dp else mediumPadValue)
  
  Column(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    SearchBar(
      modifier = Modifier
        .focusRequester(focusRequester)
        .padding(bottom = paddingAnimation.value),
      query = query,
      onQueryChange = onQueryChange,
      onSearch = {},
      active = searchState,
      onActiveChange = { onActiveSearchState() },
      placeholder = { Text(text = stringResource(R.string.search_placeholder)) },
      leadingIcon = {
        IconButton(onClick = {
          onQueryChange(Constant.EMPTY_STRING)
          onSearchStateChange()
        }) {
          Icon(
            imageVector = if (searchState) Icons.Rounded.ArrowBack else Icons.Rounded.Search,
            contentDescription = null
          )
        }
      },
      trailingIcon = {
        IconButton(onClick = {
          if (searchState) {
            onQueryChange(Constant.EMPTY_STRING)
            if (query.isBlank()) onSearchStateChange()
          } else onSettingsClick()
        }) {
          Icon(
            imageVector = if (searchState) Icons.Rounded.Close else Icons.Rounded.Settings,
            contentDescription = null
          )
        }
      }
    ) {
      SearchContent(
        listState = listState,
        searchResult = searchResult,
        onItemClick = onItemClick
      )
    }
  }
  
  LaunchedEffect(key1 = query) {
    listState.animateScrollToItem(0)
  }
  
  BackHandler(enabled = searchState) {
    if (searchState) focusRequester.freeFocus()
    onSearchStateChange()
  }
  
  LaunchedEffect(searchState) {
    if (searchState) focusRequester.requestFocus()
    else {
      focusRequester.freeFocus()
      onQueryChange(Constant.EMPTY_STRING)
    }
  }
}

@Preview
@Composable
fun PreviewHomeTopAppBar() {
  val searchResultData = listOf(
    GameItemSample(itemID = "T4_BAG", name = "Bag"),
    GameItemSample(itemID = "T5_BAG", name = "Bag"),
    GameItemSample(itemID = "T6_BAG", name = "Bag")
  )
  
  val pagingItems = flowOf(PagingData.from(searchResultData)).collectAsLazyPagingItems()
  AlbionOnlineMarketDataTheme {
    HomeTopAppBar(
      searchState = true,
      query = "Bag",
      searchResult = pagingItems,
      onActiveSearchState = { },
      onSearchStateChange = { },
      onQueryChange = {},
      onItemClick = {},
      onSettingsClick = {}
    )
  }
}

@Preview
@Composable
fun PreviewHomeTopAppBarSearch() {
  val searchResultData = listOf(
    GameItemSample(itemID = "T4_BAG", name = "Bag"),
    GameItemSample(itemID = "T5_BAG", name = "Bag"),
    GameItemSample(itemID = "T6_BAG", name = "Bag")
  )
  
  val pagingItems = flowOf(PagingData.from(searchResultData)).collectAsLazyPagingItems()
  AlbionOnlineMarketDataTheme {
    HomeTopAppBar(
      searchState = false,
      query = "Bag",
      searchResult = pagingItems,
      onActiveSearchState = { },
      onSearchStateChange = { },
      onQueryChange = {},
      onItemClick = {},
      onSettingsClick = {}
    )
  }
}