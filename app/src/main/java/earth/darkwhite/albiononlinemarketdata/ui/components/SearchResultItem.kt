package earth.darkwhite.albiononlinemarketdata.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import earth.darkwhite.albiononlinemarketdata.R
import earth.darkwhite.albiononlinemarketdata.ui.components.image.ItemAsyncImage
import earth.darkwhite.albiononlinemarketdata.ui.components.text.TitleText


@Composable
fun SearchResultItem(
  itemID: String,
  itemName: String,
  modifier: Modifier = Modifier,
  qualityIndex: Int = 0,
  showQualityText: Boolean = false,
  onClick: () -> Unit = {}
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(enabled = !showQualityText) { onClick() }
      .then(modifier),
    horizontalArrangement = Arrangement.spacedBy(mediumPadValue),
    verticalAlignment = Alignment.CenterVertically
  ) {
    ItemAsyncImage(itemID = itemID, qualityIndex = qualityIndex.plus(1))
    
    Column(verticalArrangement = Arrangement.spacedBy(smallPadValue)) {
      TitleText(itemName)
      if (showQualityText)
        Text(
          text = stringResource(
            when (qualityIndex) {
              0 -> R.string.normal_quality
              1 -> R.string.good_quality
              2 -> R.string.outstanding_quality
              3 -> R.string.excellent_quality
              else -> R.string.masterpiece_quality
            }
          ),
          style = MaterialTheme.typography.bodyMedium
        )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun SearchResultItemPreview() {
  SearchResultItem("test", "Item Name")
}
