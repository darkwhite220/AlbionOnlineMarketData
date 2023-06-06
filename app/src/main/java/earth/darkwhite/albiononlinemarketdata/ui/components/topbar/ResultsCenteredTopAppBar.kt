package earth.darkwhite.albiononlinemarketdata.ui.components.topbar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import earth.darkwhite.albiononlinemarketdata.R
import earth.darkwhite.albiononlinemarketdata.ui.screens.results.ResultsUiState
import earth.darkwhite.albiononlinemarketdata.ui.theme.AlbionOnlineMarketDataTheme


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ResultsCenteredTopAppBar(
  @StringRes titleID: Int,
  resultsUiState: ResultsUiState,
  onInfoClick: () -> Unit,
  onRefreshClick: () -> Unit,
  onBackClick: () -> Unit
) {
  CenterAlignedTopAppBar(
    title = { Text(text = stringResource(titleID)) },
    navigationIcon = {
      IconButton(onClick = onBackClick) { Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null) }
    },
    actions = {
      IconButton(onClick = onInfoClick) {
        Icon(imageVector = Icons.Rounded.Info, contentDescription = null)
      }
      IconButton(
        onClick = onRefreshClick,
        enabled = when (resultsUiState) {
          ResultsUiState.Loading -> false
          else                   -> true
        }
      ) {
        Icon(imageVector = Icons.Rounded.Refresh, contentDescription = null)
      }
    }
  )
}

@Preview
@Composable
fun PreviewResultsCenteredTopAppBar() {
  AlbionOnlineMarketDataTheme {
    ResultsCenteredTopAppBar(
      titleID = R.string.results,
      resultsUiState = ResultsUiState.Loading,
      onInfoClick = { },
      onRefreshClick = { },
      onBackClick = {}
    )
  }
}