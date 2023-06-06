package earth.darkwhite.albiononlinemarketdata.ui.components.text

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import earth.darkwhite.albiononlinemarketdata.ui.components.largePadValue


@Composable
fun SettingsSectionTitle(@StringRes textID: Int) {
  Text(
    text = stringResource(id = textID),
    style = MaterialTheme.typography.titleLarge,
    modifier = Modifier
      .padding(horizontal = largePadValue)
      .fillMaxWidth()
  )
}
