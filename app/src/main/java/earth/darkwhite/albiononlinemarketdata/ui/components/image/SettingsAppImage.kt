package earth.darkwhite.albiononlinemarketdata.ui.components.image

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import earth.darkwhite.albiononlinemarketdata.R
import earth.darkwhite.albiononlinemarketdata.ui.components.mediumPadding


@Composable
fun SettingsAppImage() {
    Icon(
        modifier = Modifier
          .size(90.dp)
          .mediumPadding(),
        painter = painterResource(R.drawable.app_icon),
        contentDescription = null
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsAppImage() {
    SettingsAppImage()
}