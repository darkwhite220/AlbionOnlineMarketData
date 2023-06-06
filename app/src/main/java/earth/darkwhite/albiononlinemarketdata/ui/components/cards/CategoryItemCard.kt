package earth.darkwhite.albiononlinemarketdata.ui.components.cards

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import earth.darkwhite.albiononlinemarketdata.R
import earth.darkwhite.albiononlinemarketdata.database.model.Category
import earth.darkwhite.albiononlinemarketdata.ui.components.image.ItemAsyncImage
import earth.darkwhite.albiononlinemarketdata.ui.components.mediumPadValue
import earth.darkwhite.albiononlinemarketdata.ui.components.smallItemIconSize
import earth.darkwhite.albiononlinemarketdata.ui.screens.home.HomeEvent
import earth.darkwhite.albiononlinemarketdata.ui.theme.AlbionOnlineMarketDataTheme
import earth.darkwhite.albiononlinemarketdata.ui.theme.redColor
import earth.darkwhite.albiononlinemarketdata.ui.theme.greenColor


@Composable
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
fun CategoryItemCard(
  modifier: Modifier = Modifier,
  categoryItem: Category,
  onHomeEvent: (HomeEvent) -> Unit
) {
  var expanded by remember { mutableStateOf(false) }
  val zRotationAnim by animateFloatAsState(
    targetValue = if (expanded) 180f else 0f,
    animationSpec = tween(),
    label = "zRotation"
  )
  
  val configuration = LocalConfiguration.current
  val screenWidthDp = configuration.screenWidthDp
  
  val availableSpace = screenWidthDp.minus(32).minus(16) // outer/inner paddings
  val itemsCountInRow = availableSpace.div(50) // icon size
  val padding = availableSpace.rem(50).div(itemsCountInRow.minus(1)).dp
  val listSize = categoryItem.items.size
  
  var onEditSwipe by remember { mutableStateOf(false) }
  
  if (onEditSwipe) {
    onHomeEvent(HomeEvent.OnEditCategory(categoryItem = categoryItem))
    onEditSwipe = false
  }
  
  val dismissState = rememberDismissState(
    positionalThreshold = { 100.dp.toPx() },
    confirmValueChange = { dismissValue ->
      when (dismissValue) {
        DismissValue.DismissedToStart -> {
          onEditSwipe = true
          false
        }
        
        DismissValue.DismissedToEnd   -> {
          onHomeEvent(HomeEvent.OnDeleteCategory(categoryItem = categoryItem))
          true
        }
        
        else                          -> {
          false
        }
      }
    }
  )
  
  SwipeToDismiss(
    modifier = modifier,
    state = dismissState,
    background = {
      val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
      val color by animateColorAsState(
        when (dismissState.targetValue) {
          DismissValue.Default          -> when (direction) {
            DismissDirection.StartToEnd -> redColor
            DismissDirection.EndToStart -> greenColor
          }
          
          DismissValue.DismissedToEnd   -> redColor
          DismissValue.DismissedToStart -> greenColor
        }
      )
      val alignment = when (direction) {
        DismissDirection.StartToEnd -> Alignment.CenterStart
        DismissDirection.EndToStart -> Alignment.CenterEnd
      }
      val icon = when (direction) {
        DismissDirection.StartToEnd -> Icons.Rounded.Delete
        DismissDirection.EndToStart -> Icons.Rounded.Edit
      }
      val scale by animateFloatAsState(
        if (dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f
      )
      
      Box(
        modifier = Modifier
          .clip(MaterialTheme.shapes.medium)
          .fillMaxSize()
          .background(color)
          .padding(horizontal = 25.dp),
        contentAlignment = alignment
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          modifier = Modifier.scale(scale)
        )
      }
    },
    dismissContent = {
      Card(
        modifier = Modifier
          .clip(MaterialTheme.shapes.medium)
          .clickable { onHomeEvent(HomeEvent.OnDataClick(categoryItem = categoryItem)) },
        shape = MaterialTheme.shapes.medium
      ) {
        FlowRow(
          modifier = Modifier
            .padding(mediumPadValue)
            .fillMaxWidth()
            .animateContentSize(tween()),
          horizontalArrangement = Arrangement.Start,
        ) {
          var i = 0
          categoryItem.items
            .take(
              if (expanded || listSize == itemsCountInRow) listSize else itemsCountInRow - 1
            )
            .forEach { itemID ->
              i += 1
              ItemAsyncImage(
                modifier = Modifier.padding(
                  end = if (listSize == itemsCountInRow && i == itemsCountInRow) 0.dp
                  else if (i % itemsCountInRow != 0) padding
                  else 0.dp
                ),
                itemID = itemID,
                size = smallItemIconSize
              )
              
              if (i == itemsCountInRow.minus(1) && listSize > itemsCountInRow) {
                IconButton(onClick = { expanded = !expanded }) {
                  Icon(
                    modifier = Modifier.graphicsLayer { rotationZ = zRotationAnim },
                    painter = painterResource(id = R.drawable.expand_circle_down),
                    contentDescription = null
                  )
                }
                i += 1
              }
              
            }
        }
      }
    }
  )
}

@Preview
@Composable
fun PreviewCategoryItemCard() {
  val category = Category(
    name = "Name",
    items = listOf("T4_GAB", "T5_GAB", "T6_GAB")
  )
  AlbionOnlineMarketDataTheme {
    CategoryItemCard(
      modifier = Modifier,
      categoryItem = category,
      onHomeEvent = {},
    )
  }
}