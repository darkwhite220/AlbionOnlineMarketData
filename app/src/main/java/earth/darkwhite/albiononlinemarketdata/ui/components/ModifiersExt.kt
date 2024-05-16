package earth.darkwhite.albiononlinemarketdata.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

const val homeStaggeredGridCellsCount = 2
val customFabSlideDistance = 88.dp
val cardElevation = 4.dp
val smallPadValue = 4.dp
val mediumPadValue = 8.dp
val largePadValue = 16.dp
val xLargePadValue = 32.dp
val smallItemIconSize = 50.dp
val itemIconSize = 56.dp
val itemIconSizeLarge = 72.dp

fun Modifier.mediumPadding(): Modifier = this.padding(mediumPadValue)
fun Modifier.largePadding(): Modifier = this.padding(largePadValue)

@Composable
fun HeightSpacer() {
  Spacer(modifier = Modifier.height(mediumPadValue))
}
