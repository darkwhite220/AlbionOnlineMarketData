package earth.darkwhite.albiononlinemarketdata.ui.components.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow


@Composable
fun TitleText(itemName: String) {
  Text(
    text = itemName,
    style = MaterialTheme.typography.titleLarge,
    maxLines = 2,
    overflow = TextOverflow.Ellipsis
  )
}