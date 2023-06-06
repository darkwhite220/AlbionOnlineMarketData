package earth.darkwhite.albiononlinemarketdata.ui.screens.marketapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import earth.darkwhite.albiononlinemarketdata.ui.screens.results.results_route
import earth.darkwhite.albiononlinemarketdata.ui.screens.settings.settings_route

@Composable
fun rememberAppState(
  navController: NavHostController = rememberNavController(),
) = remember(
  navController
) {
  MarketAppState(
    navController
  )
}

@Stable
class MarketAppState(
  val navController: NavHostController
) {
  
  fun navigateToSettings() {
    onNavigateClick(settings_route)
  }
  
  fun navigateToResults(itemsId: String) {
    onNavigateClick("$results_route/$itemsId")
  }
  
  private fun onNavigateClick(route: String) {
    val navOption = navOptions {
      popUpTo(navController.graph.findStartDestination().id) {
        saveState = true
      }
      launchSingleTop = true
      restoreState = true
    }
    navController.navigate(route = route, navOptions = navOption)
  }
  
  fun onBackClick() {
    navController.popBackStack()
  }
}
