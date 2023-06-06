package earth.darkwhite.albiononlinemarketdata.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import earth.darkwhite.albiononlinemarketdata.ui.components.cards.FontSizeRange


@Composable
fun RowScope.TableCell(
  text: String,
  bold: Boolean = false,
  weight: Float = .8f,
  align: TextAlign = TextAlign.End,
  color: Color = MaterialTheme.colorScheme.onPrimaryContainer,
  fontSizeRange: FontSizeRange = FontSizeRange(
    min = 9.sp,
    max = 13.sp,
  )
) {
  var fontSizeValue by remember { mutableStateOf(fontSizeRange.max.value) }
  var readyToDraw by remember { mutableStateOf(false) }
  Text(
    modifier = Modifier
      .weight(weight)
      .padding(vertical = smallPadValue)
      .drawWithContent { if (readyToDraw) drawContent() },
    text = text,
    textAlign = align,
    color = color,
    maxLines = 1,
    overflow = TextOverflow.Ellipsis,
    fontSize = fontSizeValue.sp,
    fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
    onTextLayout = {
      if (it.didOverflowHeight && !readyToDraw) {
        val nextFontSizeValue = fontSizeValue - fontSizeRange.step.value
        if (nextFontSizeValue <= fontSizeRange.min.value) {
          fontSizeValue = fontSizeRange.min.value
          readyToDraw = true
        } else {
          fontSizeValue = nextFontSizeValue
        }
      } else {
        readyToDraw = true
      }
    }
  )
}
