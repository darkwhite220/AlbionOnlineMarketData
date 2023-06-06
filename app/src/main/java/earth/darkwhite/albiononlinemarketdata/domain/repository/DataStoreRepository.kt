package earth.darkwhite.albiononlinemarketdata.domain.repository

import earth.darkwhite.albiononlinemarketdata.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
  
  val preferences: Flow<UserPreferences>
  
  suspend fun setItemLanguage(newValue: String)
  suspend fun setServerRegion(newValue: Boolean)
}