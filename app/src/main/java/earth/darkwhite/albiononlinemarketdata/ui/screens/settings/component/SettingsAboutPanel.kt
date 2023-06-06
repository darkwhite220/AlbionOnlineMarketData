package earth.darkwhite.albiononlinemarketdata.ui.screens.settings.component

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import earth.darkwhite.albiononlinemarketdata.R
import earth.darkwhite.albiononlinemarketdata.ui.components.cardElevation
import earth.darkwhite.albiononlinemarketdata.ui.components.largePadValue
import earth.darkwhite.albiononlinemarketdata.ui.components.largePadding
import earth.darkwhite.albiononlinemarketdata.util.Util


@Composable
fun SettingsAboutPanel() {
  val context = LocalContext.current
  Card(
    modifier = Modifier.largePadding(),
    elevation = CardDefaults.cardElevation(defaultElevation = cardElevation)
  ) {
    Column {
      // Source code
      SettingsAboutRow(
        startingIcon = Icons.Rounded.Info,
        textID = R.string.source_code,
        onClick = { Util.startActivityIntent(context, context.getString(R.string.source_code_url)) }
      )
      Divider()
      // ShareApp
      SettingsAboutRow(
        startingIcon = Icons.Rounded.Share,
        textID = R.string.share_app,
        onClick = { Util.shareApp(context) }
      )
      Divider()
      // RateApp
      SettingsAboutRow(
        startingIcon = Icons.Rounded.Star,
        textID = R.string.rate_us,
        onClick = { Util.onRateClick(context) }
      )
      Divider()
      // ContactUs
      SettingsAboutRow(
        startingIcon = Icons.Rounded.Email,
        textID = R.string.contact_me,
        onClick = { Util.contactSupport(context) }
      )
    }
  }
}

@Composable
private fun SettingsAboutRow(
  startingIcon: ImageVector,
  trailingIcon: ImageVector = Icons.Rounded.ArrowForward,
  @StringRes textID: Int,
  onClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .clickable { onClick() }
      .padding(largePadValue),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(largePadValue)
  ) {
    Icon(imageVector = startingIcon, contentDescription = null)
    Text(modifier = Modifier.weight(1f), text = stringResource(textID))
    Icon(imageVector = trailingIcon, contentDescription = null)
  }
}
