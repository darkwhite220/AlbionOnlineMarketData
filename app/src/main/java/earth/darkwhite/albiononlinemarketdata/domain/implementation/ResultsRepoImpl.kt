package earth.darkwhite.albiononlinemarketdata.domain.implementation

import earth.darkwhite.albiononlinemarketdata.database.MyDao
import earth.darkwhite.albiononlinemarketdata.domain.model.Result
import earth.darkwhite.albiononlinemarketdata.domain.model.ResultSample
import earth.darkwhite.albiononlinemarketdata.domain.model.UserPreferences
import earth.darkwhite.albiononlinemarketdata.domain.model.itemResponseDto
import earth.darkwhite.albiononlinemarketdata.domain.network.AlbionApiEast
import earth.darkwhite.albiononlinemarketdata.domain.network.AlbionApiWest
import earth.darkwhite.albiononlinemarketdata.domain.repository.GameItemSampleWrapper
import earth.darkwhite.albiononlinemarketdata.domain.repository.ItemDataWrapper
import earth.darkwhite.albiononlinemarketdata.domain.repository.ItemResponseWrapper
import earth.darkwhite.albiononlinemarketdata.domain.repository.ResultsRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class ResultsRepoImpl @Inject constructor(
  private val apiWest: AlbionApiWest,
  private val apiEast: AlbionApiEast,
  private val dao: MyDao
) : ResultsRepository {
  
  override fun getItemsData(itemsSting: String, preferences: UserPreferences): Flow<ItemDataWrapper> {
    val itemPrices = getItemPrices(itemsSting, preferences.westServer)
    val gameItemSample = getGameItemSample(itemsSting, preferences.itemLanguage)
    // Expecting from each flow to return one time, else we use combineTransform
    return itemPrices.combine(gameItemSample) { priceItem, gameItem ->
      when {
        priceItem is ResultSample.Success && gameItem is ResultSample.Success -> {
          Result.Success(itemResponseDto(priceItem.data, gameItem.data))
        }
        
        priceItem is ResultSample.Failure                                     -> {
          Result.Error(priceItem.e)
        }
        
        gameItem is ResultSample.Failure                                      -> {
          Result.Error(gameItem.e)
        }
        
        else                                                                      -> {
          Result.Error("Combine item price with data exception")
        }
      }
    }
  }
  
  private fun getGameItemSample(itemsSting: String, language: String): Flow<GameItemSampleWrapper> = flow {
    emit(
      try {
        ResultSample.Success(itemsSting.split(",").map { itemId ->
          dao.getGameItem(itemId, language)
        })
      } catch (e: Exception) {
        ResultSample.Failure(e.localizedMessage ?: "GameItemSample exception")
      }
    )
  }
  
  private fun getItemPrices(itemsSting: String, westServer: Boolean): Flow<ItemResponseWrapper> = flow {
    emit(
      try {
        ResultSample.Success(
          if (westServer) apiWest.getServerMainCitiesPrices(itemsSting)
          else apiEast.getServerMainCitiesPrices(itemsSting)
        )
      } catch (e: Exception) {
        ResultSample.Failure(e.localizedMessage ?: "ItemsPrices exception")
      }
    )
  }
  
  companion object {
//    private const val TAG = "ResultsRepoImpl"
  }
}