package earth.darkwhite.albiononlinemarketdata.domain.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import earth.darkwhite.albiononlinemarketdata.database.MyDao
import earth.darkwhite.albiononlinemarketdata.database.model.GameItem
import earth.darkwhite.albiononlinemarketdata.util.Constant.INITIAL_PAGE_INDEX
import earth.darkwhite.albiononlinemarketdata.util.Constant.SEARCH_PAGE_SIZE

class SearchPagingSource(
  private val dao: MyDao,
  private val query: String,
  private val language: String
) : PagingSource<Int, GameItem>() {
  
  override fun getRefreshKey(state: PagingState<Int, GameItem>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
        ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
    }
  }
  
  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameItem> {
    val position = params.key ?: INITIAL_PAGE_INDEX
    val posts = dao.getGameItems(
      query = query,
      pageSize = params.loadSize,
      offset = position * SEARCH_PAGE_SIZE,
      language = language
    )
    return LoadResult.Page(
      data = posts,
      prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
      nextKey = if (posts.isEmpty()) null else position + (params.loadSize / SEARCH_PAGE_SIZE)
    )
  }
}