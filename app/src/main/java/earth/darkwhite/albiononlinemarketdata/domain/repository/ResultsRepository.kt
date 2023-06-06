package earth.darkwhite.albiononlinemarketdata.domain.repository

import earth.darkwhite.albiononlinemarketdata.database.model.GameItemSample
import earth.darkwhite.albiononlinemarketdata.domain.model.Result
import earth.darkwhite.albiononlinemarketdata.domain.model.ItemData
import earth.darkwhite.albiononlinemarketdata.domain.model.ItemResponse
import earth.darkwhite.albiononlinemarketdata.domain.model.ResultSample
import earth.darkwhite.albiononlinemarketdata.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

typealias ItemResponseWrapper = ResultSample<List<ItemResponse>>
typealias GameItemSampleWrapper = ResultSample<List<GameItemSample>>
typealias ItemDataWrapper = Result<List<ItemData>>

interface ResultsRepository {
  
  fun getItemsData(itemsSting: String, preferences: UserPreferences): Flow<ItemDataWrapper>
}