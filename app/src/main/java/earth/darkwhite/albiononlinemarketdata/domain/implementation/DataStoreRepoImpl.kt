package earth.darkwhite.albiononlinemarketdata.domain.implementation

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import earth.darkwhite.albiononlinemarketdata.domain.implementation.DataStoreRepoImpl.DefaultValues.ITEM_LANGUAGE_DEFAULT
import earth.darkwhite.albiononlinemarketdata.domain.implementation.DataStoreRepoImpl.DefaultValues.NEW_SERVER_REGION_DEFAULT
import earth.darkwhite.albiononlinemarketdata.domain.model.Server
import earth.darkwhite.albiononlinemarketdata.domain.model.UserPreferences
import earth.darkwhite.albiononlinemarketdata.domain.repository.DataStoreRepository
import earth.darkwhite.albiononlinemarketdata.util.Constant.ENGLISH_LANGUAGE
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
      val server = preferences[PreferencesKeys.NEW_SERVER_REGION]?.let {
        Server.valueOf(it)
      } ?: preferences[PreferencesKeys.SERVER_REGION]?.let {
        val temp = if (it) Server.AMERICA else Server.ASIA
        setServerRegion(temp)
        temp
      } ?: NEW_SERVER_REGION_DEFAULT
      
      UserPreferences(
        itemLanguage = preferences[PreferencesKeys.ITEM_LANGUAGE] ?: ITEM_LANGUAGE_DEFAULT,
        server = server
      )
    }
  
  override suspend fun setItemLanguage(newValue: String) {
    dataStore.edit { preferences ->
      preferences[PreferencesKeys.ITEM_LANGUAGE] = newValue
    }
  }
  
  override suspend fun setServerRegion(newValue: Server) {
    dataStore.edit { preferences ->
      preferences[PreferencesKeys.NEW_SERVER_REGION] = newValue.name
    }
  }
  
  private object PreferencesKeys {
    val ITEM_LANGUAGE = stringPreferencesKey("item_language")
    val SERVER_REGION = booleanPreferencesKey("server_region")
    val NEW_SERVER_REGION = stringPreferencesKey("new_server_region")
  }
  
  private object DefaultValues {
    const val ITEM_LANGUAGE_DEFAULT = ENGLISH_LANGUAGE
    val NEW_SERVER_REGION_DEFAULT = Server.AMERICA
  }
  
  companion object {
    private const val TAG = "DataStoreRepoImpl"
  }
}