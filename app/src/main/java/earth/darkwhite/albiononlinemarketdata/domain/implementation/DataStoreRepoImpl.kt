package earth.darkwhite.albiononlinemarketdata.domain.implementation

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import earth.darkwhite.albiononlinemarketdata.domain.implementation.DataStoreRepoImpl.DefaultValues.ITEM_LANGUAGE_DEFAULT
import earth.darkwhite.albiononlinemarketdata.domain.implementation.DataStoreRepoImpl.DefaultValues.SERVER_REGION_DEFAULT
import earth.darkwhite.albiononlinemarketdata.domain.model.UserPreferences
import earth.darkwhite.albiononlinemarketdata.domain.repository.DataStoreRepository
import earth.darkwhite.albiononlinemarketdata.util.Constant.ENGLISH_LANGUAGE
import earth.darkwhite.albiononlinemarketdata.util.Constant.WEST_SERVER
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class DataStoreRepoImpl @Inject constructor(
  private val dataStore: DataStore<Preferences>
) : DataStoreRepository {
  
  init {
    Log.d(TAG, "init: ")
  }
  
  override val preferences: Flow<UserPreferences> = dataStore.data
    .catch {
      Log.e(TAG, "Preferences: ${it.message}")
    }
    .map { preferences ->
      UserPreferences(
        itemLanguage = preferences[PreferencesKeys.ITEM_LANGUAGE] ?: ITEM_LANGUAGE_DEFAULT,
        westServer = preferences[PreferencesKeys.SERVER_REGION] ?: SERVER_REGION_DEFAULT
      )
    }
  
  override suspend fun setItemLanguage(newValue: String) {
    dataStore.edit { preferences ->
      preferences[PreferencesKeys.ITEM_LANGUAGE] = newValue
    }
  }
  
  override suspend fun setServerRegion(newValue: Boolean) {
    dataStore.edit { preferences ->
      preferences[PreferencesKeys.SERVER_REGION] = newValue
    }
  }
  
  private object PreferencesKeys {
    val ITEM_LANGUAGE = stringPreferencesKey("item_language")
    val SERVER_REGION = booleanPreferencesKey("server_region")
  }
  
  private object DefaultValues {
    const val ITEM_LANGUAGE_DEFAULT = ENGLISH_LANGUAGE
    const val SERVER_REGION_DEFAULT = WEST_SERVER
  }
  
  companion object {
    private const val TAG = "DataStoreRepoImpl"
  }
}