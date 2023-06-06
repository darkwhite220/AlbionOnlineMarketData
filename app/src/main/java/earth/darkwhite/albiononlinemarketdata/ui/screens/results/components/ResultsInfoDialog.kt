package earth.darkwhite.albiononlinemarketdata.ui.screens.results.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import earth.darkwhite.albiononlinemarketdata.R
import earth.darkwhite.albiononlinemarketdata.ui.theme.AlbionOnlineMarketDataTheme
import earth.darkwhite.albiononlinemarketdata.util.Util.startActivityIntent


@Composable
fun ResultsInfoDialog(onClick: () -> Unit) {
  val context = LocalContext.current
  AlertDialog(
    onDismissRequest = onClick,
    confirmButton = {},
    title = {
      Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = stringResource(R.string.result_info),
          style = TextStyle(
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = MaterialTheme.colorScheme.onPrimaryContainer
          )
        )
      }
    },
    text = {
      val annotatedText = buildAnnotatedString {
        withStyle(
          style = SpanStyle(color = MaterialTheme.colorScheme.onPrimaryContainer)
        )
        {
          append(stringResource(R.string.result_info_desc))
        }
        withStyle(
          style = SpanStyle(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
          )
        ) {
          AppendLink(R.string.albion_online_data_project, R.string.albion_online_data_project_url)
        }
      }
      ClickableText(
        text = annotatedText,
        onClick = { offset ->
          annotatedText.onLinkClick(offset) { link ->
            startActivityIntent(context = context, uri = link)
          }
        },
        style = TextStyle(textAlign = TextAlign.Center)
      )
    }
  )
}

@Composable
private fun AnnotatedString.Builder.AppendLink(@StringRes linkText: Int, @StringRes linkUrl: Int) {
  val url = stringResource(linkUrl)
  pushStringAnnotation(tag = url, annotation = url)
  append(stringResource(linkText))
  pop()
}

private fun AnnotatedString.onLinkClick(offset: Int, onClick: (String) -> Unit) {
  getStringAnnotations(start = offset, end = offset).firstOrNull()?.let {
    onClick(it.item)
  }
}

@Preview
@Composable
fun PreviewResultsInfoDialog() {
  AlbionOnlineMarketDataTheme {
    ResultsInfoDialog {}
  }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewResultsInfoDialogNight() {
  AlbionOnlineMarketDataTheme {
    ResultsInfoDialog {}
  }
}