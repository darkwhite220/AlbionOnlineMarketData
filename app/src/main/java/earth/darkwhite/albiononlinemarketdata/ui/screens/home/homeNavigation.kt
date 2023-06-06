package earth.darkwhite.albiononlinemarketdata.ui.screens.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val home_route = "home_route"

fun NavGraphBuilder.homeScreen(
  onSettingsClick: () -> Unit,
  onFetchClick: (itemsId: String) -> Unit,
) {
  composable(route = home_route) {
    HomeScreen(
      onSettingsClick = onSettingsClick,
      onFetchClick = onFetchClick
    )
  }
}