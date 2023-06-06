package earth.darkwhite.albiononlinemarketdata.ui.components.text

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import earth.darkwhite.albiononlinemarketdata.ui.components.largePadding

@Composable
fun ErrorText(message: String) {
  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    Text(
      modifier = Modifier.largePadding(),
      text = message
    )
  }
}