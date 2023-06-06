package earth.darkwhite.albiononlinemarketdata.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import earth.darkwhite.albiononlinemarketdata.ui.screens.home.homeScreen
import earth.darkwhite.albiononlinemarketdata.ui.screens.home.home_route
import earth.darkwhite.albiononlinemarketdata.ui.screens.results.resultsScreen
import earth.darkwhite.albiononlinemarketdata.ui.screens.settings.settingsScreen


@Composable
fun MarketNavHost(
  navController: NavHostController,
  onSettingsClick: () -> Unit,
  onFetchClick: (itemsId: String) -> Unit,
  onBackClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  NavHost(
    navController = navController,
    startDestination = home_route
  ) {
    homeScreen(
      onSettingsClick = onSettingsClick,
      onFetchClick = onFetchClick
    )
    settingsScreen(onBackClick = onBackClick)
    resultsScreen(onBackClick = onBackClick)
  }
}