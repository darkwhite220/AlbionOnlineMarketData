package earth.darkwhite.albiononlinemarketdata.ui.screens.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val settings_route = "settings_route"

fun NavGraphBuilder.settingsScreen(onBackClick: () -> Unit) {
  composable(route = settings_route) {
    SettingsScreen(
      onBackClick = onBackClick
    )
  }
}