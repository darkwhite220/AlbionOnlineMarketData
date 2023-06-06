package earth.darkwhite.albiononlinemarketdata.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import earth.darkwhite.albiononlinemarketdata.ui.theme.AlbionOnlineMarketDataTheme


@Composable
@OptIn(ExperimentalAnimationApi::class)
fun HomeFab(
  fabState: Boolean,
  searchState: Boolean,
  onClick: () -> Unit
) {
  val density = LocalDensity.current
  AnimatedVisibility(
    visible = !fabState && !searchState,
    enter = slideInVertically {
      with(density) { customFabSlideDistance.roundToPx() }
    } + scaleIn(),
    exit = slideOutVertically {
      with(density) { customFabSlideDistance.roundToPx() }
    } + scaleOut(),
    label = "Fab"
  ) {
    FloatingActionButton(onClick = onClick) {
      Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
    }
  }
}

@Preview
@Composable
fun PreviewHomeFab() {
  AlbionOnlineMarketDataTheme {
    HomeFab(fabState = false, searchState = false){}
  }
}