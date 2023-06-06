package earth.darkwhite.albiononlinemarketdata.ui.screens.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import earth.darkwhite.albiononlinemarketdata.R
import earth.darkwhite.albiononlinemarketdata.ui.components.image.ItemAsyncImage
import earth.darkwhite.albiononlinemarketdata.ui.components.itemIconSize
import earth.darkwhite.albiononlinemarketdata.ui.components.mediumPadValue
import earth.darkwhite.albiononlinemarketdata.ui.components.mediumPadding
import earth.darkwhite.albiononlinemarketdata.ui.components.smallPadValue
import earth.darkwhite.albiononlinemarketdata.ui.screens.home.HomeEvent
import earth.darkwhite.albiononlinemarketdata.util.Constant


@Composable
fun CategoryCreationContent(
  newCategoryItems: MutableList<String>,
  onHomeEvent: (HomeEvent) -> Unit
) {
  Column(modifier = Modifier.animateContentSize()) {
    CategoryCreationHeader(newCategoryItems, onHomeEvent)
    CategoryCreatingFooter(newCategoryItems, onHomeEvent)
  }
}

@Composable
private fun CategoryCreationHeader(
  newCategoryItems: MutableList<String>,
  onHomeEvent: (HomeEvent) -> Unit
) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Text(
      modifier = Modifier
        .mediumPadding()
        .weight(1f),
      text = stringResource(R.string.category_items_count, newCategoryItems.size, Constant.CATEGORY_MAX_ITEMS_SIZE)
    )
    IconButton(
      onClick = { onHomeEvent(HomeEvent.OnSaveNewCategory) },
      enabled = newCategoryItems.isNotEmpty()
    ) {
      Icon(imageVector = Icons.Rounded.Done, contentDescription = null)
    }
    IconButton(
      onClick = { onHomeEvent(HomeEvent.OnResetNewCategoryItems) },
      enabled = newCategoryItems.isNotEmpty()
    ) {
      Icon(imageVector = Icons.Rounded.Refresh, contentDescription = null)
    }
    IconButton(onClick = { onHomeEvent(HomeEvent.OnCloseSearch) }) {
      Icon(imageVector = Icons.Rounded.Close, contentDescription = null)
    }
  }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun ColumnScope.CategoryCreatingFooter(
  newCategoryItems: MutableList<String>,
  onHomeEvent: (HomeEvent) -> Unit
) {
  AnimatedVisibility(visible = newCategoryItems.isNotEmpty()) {
    val listState = rememberLazyListState()
    Column {
      LazyRow(
        modifier = Modifier.fillMaxWidth(),
        state = listState,
        contentPadding = PaddingValues(smallPadValue),
        verticalAlignment = Alignment.CenterVertically
      ) {
        item { Spacer(modifier = Modifier.width(mediumPadValue)) }
        items(newCategoryItems) { itemID ->
          ItemAsyncImage(
            modifier = Modifier
              .clip(MaterialTheme.shapes.medium)
              .clickable { onHomeEvent(HomeEvent.OnRemoveNewCategoryItem(itemID)) }
              .animateItemPlacement(tween()),
            itemID = itemID,
            size = itemIconSize)
        }
        if (newCategoryItems.size == 1) {
          item { Text(text = stringResource(R.string.click_to_delete)) }
        }
        item { Spacer(modifier = Modifier.width(mediumPadValue)) }
      }
      Spacer(modifier = Modifier.height(mediumPadValue))
    }
    LaunchedEffect(key1 = newCategoryItems.size) {
      if (newCategoryItems.isNotEmpty()) listState.animateScrollToItem(newCategoryItems.lastIndex)
    }
  }
}
