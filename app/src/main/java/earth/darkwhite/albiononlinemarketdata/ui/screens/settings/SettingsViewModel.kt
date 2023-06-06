package earth.darkwhite.albiononlinemarketdata.ui.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import earth.darkwhite.albiononlinemarketdata.domain.model.UserPreferences
import earth.darkwhite.albiononlinemarketdata.domain.repository.DataStoreRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class SettingsViewModel @Inject constructor(
  private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
  
  val preferences: StateFlow<SettingsUiState> = dataStoreRepository.preferences.map {
    SettingsUiState.Success(it)
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.Eagerly,
    initialValue = SettingsUiState.Loading
  )
  
  init {
    Log.d(TAG, "init: ")
  }
  
  fun updatePreferences(event: SettingsEvent) {
    when (event) {
      is SettingsEvent.UpdateItemLanguage -> {
        viewModelScope.launch { dataStoreRepository.setItemLanguage(newValue = event.newValue) }
      }
      
      is SettingsEvent.UpdateServerRegion -> {
        viewModelScope.launch { dataStoreRepository.setServerRegion(newValue = event.newValue) }
      }
    }
  }
  
  companion object {
    private const val TAG = "SettingsViewModel"
  }
}

sealed interface SettingsUiState {
  object Loading : SettingsUiState
  data class Success(val data: UserPreferences) : SettingsUiState
}