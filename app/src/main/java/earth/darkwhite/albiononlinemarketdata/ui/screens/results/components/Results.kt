package earth.darkwhite.albiononlinemarketdata.ui.screens.results.components

import androidx.compose.runtime.Composable
import earth.darkwhite.albiononlinemarketdata.domain.model.ItemData
import earth.darkwhite.albiononlinemarketdata.ui.components.text.ErrorText
import earth.darkwhite.albiononlinemarketdata.ui.components.ProgressBar
import earth.darkwhite.albiononlinemarketdata.ui.screens.results.ResultsUiState


@Composable
fun Results(
  results: ResultsUiState,
  resultsItemsContent: @Composable (items: List<ItemData>) -> Unit
) {
  when (results) {
    ResultsUiState.Loading    -> {
      ProgressBar()
    }
    
    is ResultsUiState.Error   -> {
      ErrorText(message = results.message)
    }
    
    is ResultsUiState.Success -> {
      resultsItemsContent(results.data)
    }
  }
}
