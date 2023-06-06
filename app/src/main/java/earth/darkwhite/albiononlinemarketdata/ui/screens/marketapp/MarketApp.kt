package earth.darkwhite.albiononlinemarketdata.ui.screens.marketapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import earth.darkwhite.albiononlinemarketdata.ui.navigation.MarketNavHost

@Composable
fun MarketApp(
  appState: MarketAppState = rememberAppState()
) {
  Scaffold { paddingValues ->
    MarketNavHost(
      navController = appState.navController,
      onSettingsClick = appState::navigateToSettings,
      onFetchClick = appState::navigateToResults,
      onBackClick = appState::onBackClick,
      modifier = Modifier.padding(paddingValues)
    )
  }
}

