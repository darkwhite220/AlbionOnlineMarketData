package earth.darkwhite.albiononlinemarketdata.ui.screens.results

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val results_route = "results_route"
const val FETCH_ITEMS_ID = "fetch_items_id"
const val results_route_with_arg = "${results_route}/{${FETCH_ITEMS_ID}}"

fun NavGraphBuilder.resultsScreen(onBackClick: () -> Unit) {
  composable(
    route = results_route_with_arg,
    arguments = listOf(navArgument(name = FETCH_ITEMS_ID) { type = NavType.StringType })
  ) {
    ResultsScreen(
      onBackClick = onBackClick
    )
  }
}