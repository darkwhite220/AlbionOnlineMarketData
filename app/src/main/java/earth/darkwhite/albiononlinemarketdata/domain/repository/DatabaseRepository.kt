package earth.darkwhite.albiononlinemarketdata.domain.repository

import androidx.paging.PagingData
import earth.darkwhite.albiononlinemarketdata.database.model.Category
import earth.darkwhite.albiononlinemarketdata.database.model.GameItemSample
import kotlinx.coroutines.flow.Flow

typealias CategoryWrapper = Flow<List<Category>>

interface DatabaseRepository {
  
  suspend fun fetchGameItem(query: String, language: String): Flow<PagingData<GameItemSample>>
  
  fun getCategories(): CategoryWrapper
  suspend fun createNewCategory(categoryItems: List<String>, categoryIndex: Long)
  
  suspend fun deleteCategory(category: Category)
}