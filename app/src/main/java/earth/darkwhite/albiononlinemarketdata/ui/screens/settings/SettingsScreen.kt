package earth.darkwhite.albiononlinemarketdata.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import earth.darkwhite.albiononlinemarketdata.R
import earth.darkwhite.albiononlinemarketdata.domain.model.UserPreferences
import earth.darkwhite.albiononlinemarketdata.ui.components.HeightSpacer
import earth.darkwhite.albiononlinemarketdata.ui.components.image.SettingsAppImage
import earth.darkwhite.albiononlinemarketdata.ui.components.text.SettingsSectionTitle
import earth.darkwhite.albiononlinemarketdata.ui.components.topbar.SettingsCenteredTopAppBar
import earth.darkwhite.albiononlinemarketdata.ui.screens.settings.component.SettingsAboutPanel
import earth.darkwhite.albiononlinemarketdata.ui.screens.settings.component.UserPreferencesContent
import earth.darkwhite.albiononlinemarketdata.ui.theme.AlbionOnlineMarketDataTheme

@Composable
fun SettingsScreen(
  onBackClick: () -> Unit,
  viewModel: SettingsViewModel = hiltViewModel()
) {
  val preferences by viewModel.preferences.collectAsStateWithLifecycle()
  SettingsScreenContent(
    preferences = preferences,
    onSettingsEvent = viewModel::updatePreferences,
    onBackClick = onBackClick
  )
}

@Composable
fun SettingsScreenContent(
  preferences: SettingsUiState,
  onSettingsEvent: (event: SettingsEvent) -> Unit,
  onBackClick: () -> Unit,
) {
  Scaffold(
    topBar = {
      SettingsCenteredTopAppBar(
        titleID = R.string.settings,
        onBackClick = onBackClick
      )
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(paddingValues),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // App icon
      SettingsAppImage()
      Text(text = stringResource(R.string.app_version))
      HeightSpacer()
      // Settings
      SettingsSectionTitle(R.string.settings)
      UserPreferencesContent(preferences, onSettingsEvent)
      // About
      SettingsSectionTitle(R.string.about)
      SettingsAboutPanel()
    }
  }
}

@Preview
@Composable
fun PreviewSettingsScreenContent() {
  AlbionOnlineMarketDataTheme {
    SettingsScreenContent(
      preferences = SettingsUiState.Success(UserPreferences()),
      onSettingsEvent = {},
      onBackClick = {})
  }
}