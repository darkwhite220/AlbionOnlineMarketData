package earth.darkwhite.albiononlinemarketdata.ui.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import earth.darkwhite.albiononlinemarketdata.database.model.Category
import earth.darkwhite.albiononlinemarketdata.database.model.GameItemSample
import earth.darkwhite.albiononlinemarketdata.domain.model.Result
import earth.darkwhite.albiononlinemarketdata.domain.model.UserPreferences
import earth.darkwhite.albiononlinemarketdata.domain.model.asResult
import earth.darkwhite.albiononlinemarketdata.domain.repository.DataStoreRepository
import earth.darkwhite.albiononlinemarketdata.domain.repository.DatabaseRepository
import earth.darkwhite.albiononlinemarketdata.util.Constant.CATEGORY_DEFAULT_INDEX
import earth.darkwhite.albiononlinemarketdata.util.Constant.CATEGORY_MAX_ITEMS_SIZE
import earth.darkwhite.albiononlinemarketdata.util.Constant.EMPTY_STRING
import earth.darkwhite.albiononlinemarketdata.util.Constant.ITEMS_IDS_SEPARATOR
import earth.darkwhite.albiononlinemarketdata.util.Constant.STOP_TIMEOUT_MILLIS
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
  private val dataBaseRepository: DatabaseRepository,
  private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
  
  private var userPreferences: UserPreferences = UserPreferences()
  
  var query = MutableStateFlow(EMPTY_STRING)
    private set
  
  val searchResult: Flow<PagingData<GameItemSample>> = query.flatMapLatest { query ->
    dataBaseRepository.fetchGameItem(query, userPreferences.itemLanguage)
      .cachedIn(viewModelScope)
      .distinctUntilChanged()
  }
  
  var newCategoryItems = mutableStateListOf<String>()
    private set
  
  private var editCategoryIndex: Long = CATEGORY_DEFAULT_INDEX
  
  var fetchItemsIDs: String by mutableStateOf(EMPTY_STRING)
    private set
  
  val homeUiState: StateFlow<HomeUiState> = homeUiStateStream(dataBaseRepository = dataBaseRepository)
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
      initialValue = HomeUiState.Loading
    )
  
  init {
    Log.d(TAG, "init: ")
    getUserPreferences()
    mutableSetOf("f")
  }
  
  
  private fun getUserPreferences() = viewModelScope.launch {
    dataStoreRepository.preferences.collect { preferences ->
      userPreferences = preferences
    }
  }
  
  fun onEvent(event: HomeEvent) {
    when (event) {
      is HomeEvent.OnQueryChange           -> {
        query.value = event.newValue
      }
      
      is HomeEvent.OnAddNewCategoryItem    -> {
        if (newCategoryItems.size < CATEGORY_MAX_ITEMS_SIZE
          && !newCategoryItems.contains(event.GameItemSample.itemID)
        )
          newCategoryItems.add(event.GameItemSample.itemID)
      }
      
      is HomeEvent.OnRemoveNewCategoryItem -> {
        newCategoryItems.remove(event.itemsID)
      }
      
      is HomeEvent.OnDataClick             -> {
        if (event.GameItemSample != null) {
          fetchItemsIDs = event.GameItemSample.itemID
        } else if (event.categoryItem != null) {
          fetchItemsIDs = event.categoryItem.items.joinToString(separator = ITEMS_IDS_SEPARATOR)
        }
      }
      
      is HomeEvent.OnDeleteCategory        -> {
        viewModelScope.launch {
          dataBaseRepository.deleteCategory(event.categoryItem)
          resetNewCategoryItems()
        }
      }
      
      is HomeEvent.OnEditCategory          -> {
        resetNewCategoryItems(editCategoryIndex = event.categoryItem.index)
        newCategoryItems.addAll(event.categoryItem.items)
      }
      
      is HomeEvent.OnFetchClick            -> {
        event.onClick(fetchItemsIDs)
        fetchItemsIDs = EMPTY_STRING
      }
      
      HomeEvent.OnSaveNewCategory          -> {
        viewModelScope.launch {
          dataBaseRepository.createNewCategory(newCategoryItems, editCategoryIndex)
          resetNewCategoryItems()
        }
      }
      
      HomeEvent.OnResetNewCategoryItems    -> {
        resetNewCategoryItems()
      }
      
      HomeEvent.OnCloseSearch              -> {
        resetNewCategoryItems()
      }
    }
  }
  
  private fun resetNewCategoryItems(editCategoryIndex: Long = CATEGORY_DEFAULT_INDEX) {
    this.editCategoryIndex = editCategoryIndex
    newCategoryItems.clear()
  }
  
  companion object {
    private const val TAG = "HomeViewModel"
  }
}

private fun homeUiStateStream(dataBaseRepository: DatabaseRepository): Flow<HomeUiState> {
  return dataBaseRepository.getCategories().asResult().map { result ->
    when (result) {
      Result.Loading    -> HomeUiState.Loading
      is Result.Error   -> HomeUiState.Error(result.e)
      is Result.Success -> HomeUiState.Success(result.data)
    }
  }
}

sealed interface HomeUiState {
  object Loading : HomeUiState
  data class Success(val data: List<Category>) : HomeUiState
  data class Error(val message: String) : HomeUiState
}