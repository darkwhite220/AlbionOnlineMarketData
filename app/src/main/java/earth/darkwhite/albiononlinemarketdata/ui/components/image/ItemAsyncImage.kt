package earth.darkwhite.albiononlinemarketdata.ui.components.image

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import earth.darkwhite.albiononlinemarketdata.ui.components.itemIconSizeLarge
import earth.darkwhite.albiononlinemarketdata.util.Util

@Composable
fun ItemAsyncImage(
  itemID: String,
  modifier: Modifier = Modifier,
  qualityIndex: Int = 1,
  size: Dp = itemIconSizeLarge
) {
  AsyncImage(
    modifier = modifier.size(size),
    model = Util.getImageUrl(itemID, qualityIndex),
    contentDescription = null
  )
}