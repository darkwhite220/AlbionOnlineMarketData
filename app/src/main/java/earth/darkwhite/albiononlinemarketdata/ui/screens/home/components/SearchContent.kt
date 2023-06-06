package earth.darkwhite.albiononlinemarketdata.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import earth.darkwhite.albiononlinemarketdata.database.model.GameItemSample
import earth.darkwhite.albiononlinemarketdata.ui.components.SearchResultItem
import earth.darkwhite.albiononlinemarketdata.ui.components.largePadValue
import earth.darkwhite.albiononlinemarketdata.ui.components.mediumPadValue
import earth.darkwhite.albiononlinemarketdata.util.Constant


@Composable
fun SearchContent(
  listState: LazyListState,
  searchResult: LazyPagingItems<GameItemSample>,
  onItemClick: (item: GameItemSample) -> Unit
) {
  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    state = listState,
    contentPadding = PaddingValues(vertical = largePadValue),
    verticalArrangement = Arrangement.spacedBy(mediumPadValue)
  ) {
    items(
      count = searchResult.itemCount,
      key = searchResult.itemKey { it.itemID },
      contentType = searchResult.itemContentType { Constant.CONTENT_TYPE_GAME_ITEM }
    ) { index ->
      SearchItem(
        item = searchResult[index],
        onClick = { onItemClick(it) }
      )
    }
  }
}

@Composable
private fun SearchItem(
  item: GameItemSample?,
  onClick: (item: GameItemSample) -> Unit
) {
  item?.let {
    SearchResultItem(
      itemID = item.itemID,
      itemName = item.name,
      modifier = Modifier.padding(horizontal = largePadValue),
      onClick = { onClick(item) }
    )
  }
}
