package earth.darkwhite.albiononlinemarketdata.ui.components.topbar

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SettingsCenteredTopAppBar(
  @StringRes titleID: Int,
  onBackClick: () -> Unit
) {
  CenterAlignedTopAppBar(
    title = { Text(text = stringResource(titleID)) },
    navigationIcon = {
      IconButton(onClick = onBackClick) { Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null) }
    }
  )
}
