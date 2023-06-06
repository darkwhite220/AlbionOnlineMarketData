package earth.darkwhite.albiononlinemarketdata.ui.screens.results

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import earth.darkwhite.albiononlinemarketdata.domain.model.ItemData
import earth.darkwhite.albiononlinemarketdata.domain.model.Result
import earth.darkwhite.albiononlinemarketdata.domain.model.UserPreferences
import earth.darkwhite.albiononlinemarketdata.domain.repository.DataStoreRepository
import earth.darkwhite.albiononlinemarketdata.domain.repository.ResultsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class ResultsViewModel @Inject constructor(
  private val savedStateHandle: SavedStateHandle,
  private val resultsRepository: ResultsRepository,
  private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
  
  private lateinit var userPreferences: UserPreferences
  
  private var itemsIds: String = ""
  
  var resultsUiState = MutableStateFlow<ResultsUiState>(ResultsUiState.Loading)
    private set
  
  init {
    Log.d(TAG, "init: ")
    getUserPreferences()
  }
  
  private fun getUserPreferences() = viewModelScope.launch {
    dataStoreRepository.preferences.collect { preferences ->
      userPreferences = preferences
      fetchPrices()
    }
  }
  
  fun fetchPrices() {
    resultsUiState.value = ResultsUiState.Loading
    
    itemsIds = getFetchItemsId()
    
    if (itemsIds.isNotEmpty()) {
      resultsRepository.getItemsData(itemsIds, userPreferences).map { data ->
        resultsUiState.value = when (data) {
          Result.Loading                                                                                    -> ResultsUiState.Loading
          is Result.Error -> ResultsUiState.Error(data.e)
          is Result.Success -> ResultsUiState.Success(data.data)
        }
      }.launchIn(viewModelScope)
    } else {
      ResultsUiState.Error("Failed to retrieve items ids.")
    }
  }
  
  private fun getFetchItemsId(): String {
    return try {
      itemsIds = checkNotNull(savedStateHandle.get<String>(FETCH_ITEMS_ID)) { "ItemsId must be set beforehand" }
      check(itemsIds.isNotEmpty()) { "ItemsId must be non-empty" }
      return itemsIds
    } catch (e: Exception) {
      Log.e(TAG, "FetchItemsId: itemsId was null")
      ""
    }
  }
  
  
  companion object {
    private const val TAG = "ResultsViewModel"
  }
}

sealed interface ResultsUiState {
  object Loading : ResultsUiState
  data class Success(val data: List<ItemData>) : ResultsUiState
  data class Error(val message: String) : ResultsUiState
}