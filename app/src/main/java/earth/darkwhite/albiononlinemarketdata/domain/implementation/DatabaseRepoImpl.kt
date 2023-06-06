package earth.darkwhite.albiononlinemarketdata.domain.implementation

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import earth.darkwhite.albiononlinemarketdata.database.MyDao
import earth.darkwhite.albiononlinemarketdata.database.model.Category
import earth.darkwhite.albiononlinemarketdata.database.model.GameItemSample
import earth.darkwhite.albiononlinemarketdata.database.model.toGameItemSample
import earth.darkwhite.albiononlinemarketdata.domain.pagingSource.SearchPagingSource
import earth.darkwhite.albiononlinemarketdata.domain.repository.CategoryWrapper
import earth.darkwhite.albiononlinemarketdata.domain.repository.DatabaseRepository
import earth.darkwhite.albiononlinemarketdata.util.Constant.CATEGORY_DEFAULT_INDEX
import earth.darkwhite.albiononlinemarketdata.util.Constant.SEARCH_PAGE_SIZE
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DatabaseRepoImpl @Inject constructor(
  private val myDao: MyDao
) : DatabaseRepository {
  
  override suspend fun fetchGameItem(query: String, language: String): Flow<PagingData<GameItemSample>> {
    val dbQuery = "%$query%"
    
    return Pager(
      config = PagingConfig(pageSize = SEARCH_PAGE_SIZE),
      pagingSourceFactory = { SearchPagingSource(dao = myDao, query = dbQuery, language = language) }
    )
      .flow
      .map { pagingData ->
        pagingData.map {
          it.toGameItemSample(language)
        }
      }
  }
  
  override fun getCategories(): CategoryWrapper = myDao.getCategories()
  
  override suspend fun createNewCategory(categoryItems: List<String>, categoryIndex: Long) {
    val category = when (categoryIndex) {
      CATEGORY_DEFAULT_INDEX -> Category(
        name = "Category name",
        items = categoryItems.map { it }
      )
      
      else                   -> Category(
        index = categoryIndex,
        name = "Category name",
        items = categoryItems.map { it }
      )
    }
    myDao.insertNewCategory(
      category = category
    )
  }
  
  override suspend fun deleteCategory(category: Category) {
    myDao.deleteCategory(category = category)
  }
  
}