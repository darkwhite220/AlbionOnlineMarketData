package earth.darkwhite.albiononlinemarketdata.ui.components.bottombar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import earth.darkwhite.albiononlinemarketdata.ui.components.customFabSlideDistance
import earth.darkwhite.albiononlinemarketdata.ui.screens.home.FabTransitionData
import earth.darkwhite.albiononlinemarketdata.ui.screens.home.HomeEvent
import earth.darkwhite.albiononlinemarketdata.ui.screens.home.components.CategoryCreationContent
import earth.darkwhite.albiononlinemarketdata.ui.screens.home.updateFabTransitionData
import earth.darkwhite.albiononlinemarketdata.ui.theme.AlbionOnlineMarketDataTheme


@Composable
@OptIn(ExperimentalAnimationApi::class)
fun HomeBottomBar(
  fabState: Boolean,
  fabTransitionData: FabTransitionData,
  newCategoryItems: MutableList<String>,
  onHomeEvent: (HomeEvent) -> Unit,
  onStateChange: (state: Boolean) -> Unit
) {
  val density = LocalDensity.current
  AnimatedVisibility(
    visible = fabState,
    enter = slideInVertically {
      with(density) { customFabSlideDistance.roundToPx() }
    } + scaleIn(),
    exit = slideOutVertically {
      with(density) { customFabSlideDistance.roundToPx() }
    } + scaleOut(),
    label = "CustomFab"
  ) {
    Surface(
      modifier = Modifier.padding(fabTransitionData.padding),
      shape = RoundedCornerShape(fabTransitionData.cornerSize),
      color = FloatingActionButtonDefaults.containerColor,
      contentColor = contentColorFor(FloatingActionButtonDefaults.containerColor),
      tonalElevation = fabTransitionData.elevation,
      shadowElevation = fabTransitionData.shadow,
    ) {
      CategoryCreationContent(
        newCategoryItems = newCategoryItems,
        onHomeEvent = { event ->
          onHomeEvent(event)
          if (event in listOf(HomeEvent.OnSaveNewCategory, HomeEvent.OnCloseSearch)) onStateChange(false)
        }
      )
    }
  }
}

@Preview
@Composable
fun PreviewHomeBottomBar() {
  val category = mutableListOf("T4_BAG", "T5_BAG", "T6_BAG", "T4_BAG", "T5_BAG", "T6_BAG", "T4_BAG", "T5_BAG", "T6_BAG")
  
  AlbionOnlineMarketDataTheme {
    HomeBottomBar(
      fabState = true,
      fabTransitionData = updateFabTransitionData(true),
      newCategoryItems = category,
      onHomeEvent = {},
      onStateChange = {}
    )
  }
}