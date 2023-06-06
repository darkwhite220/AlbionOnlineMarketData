package earth.darkwhite.albiononlinemarketdata.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import earth.darkwhite.albiononlinemarketdata.database.model.Category
import earth.darkwhite.albiononlinemarketdata.database.model.GameItem
import earth.darkwhite.albiononlinemarketdata.database.model.GameItemSample
import earth.darkwhite.albiononlinemarketdata.util.Constant.CHINESE_LANGUAGE
import earth.darkwhite.albiononlinemarketdata.util.Constant.ENGLISH_LANGUAGE
import earth.darkwhite.albiononlinemarketdata.util.Constant.FRENCH_LANGUAGE
import earth.darkwhite.albiononlinemarketdata.util.Constant.GERMAN_LANGUAGE
import earth.darkwhite.albiononlinemarketdata.util.Constant.KOREAN_LANGUAGE
import earth.darkwhite.albiononlinemarketdata.util.Constant.POLISH_LANGUAGE
import earth.darkwhite.albiononlinemarketdata.util.Constant.PORTUGUESE_LANGUAGE
import earth.darkwhite.albiononlinemarketdata.util.Constant.RUSSIAN_LANGUAGE
import earth.darkwhite.albiononlinemarketdata.util.Constant.SPANISH_LANGUAGE
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDao {
  
  // Categories
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertNewCategory(category: Category)
  
  @Delete
  suspend fun deleteCategory(category: Category)
  
  @Query("SELECT * FROM category_table ORDER BY `index` ASC")
  fun getCategories(): Flow<List<Category>>
  
  // Game Items
  @Query("SELECT * FROM game_items_table WHERE en LIKE :query LIMIT :pageSize OFFSET :offset")
  suspend fun getGameItemsEN(query: String, pageSize: Int, offset: Int): List<GameItem>
  
  @Query("SELECT * FROM game_items_table WHERE de LIKE :query LIMIT :pageSize OFFSET :offset")
  suspend fun getGameItemsDE(query: String, pageSize: Int, offset: Int): List<GameItem>
  
  @Query("SELECT * FROM game_items_table WHERE fr LIKE :query LIMIT :pageSize OFFSET :offset")
  suspend fun getGameItemsFR(query: String, pageSize: Int, offset: Int): List<GameItem>
  
  @Query("SELECT * FROM game_items_table WHERE ru LIKE :query LIMIT :pageSize OFFSET :offset")
  suspend fun getGameItemsRU(query: String, pageSize: Int, offset: Int): List<GameItem>
  
  @Query("SELECT * FROM game_items_table WHERE pl LIKE :query LIMIT :pageSize OFFSET :offset")
  suspend fun getGameItemsPL(query: String, pageSize: Int, offset: Int): List<GameItem>
  
  @Query("SELECT * FROM game_items_table WHERE es LIKE :query LIMIT :pageSize OFFSET :offset")
  suspend fun getGameItemsES(query: String, pageSize: Int, offset: Int): List<GameItem>
  
  @Query("SELECT * FROM game_items_table WHERE pt LIKE :query LIMIT :pageSize OFFSET :offset")
  suspend fun getGameItemsPT(query: String, pageSize: Int, offset: Int): List<GameItem>
  
  @Query("SELECT * FROM game_items_table WHERE cn LIKE :query LIMIT :pageSize OFFSET :offset")
  suspend fun getGameItemsCN(query: String, pageSize: Int, offset: Int): List<GameItem>
  
  @Query("SELECT * FROM game_items_table WHERE kr LIKE :query LIMIT :pageSize OFFSET :offset")
  suspend fun getGameItemsKR(query: String, pageSize: Int, offset: Int): List<GameItem>
  
  suspend fun getGameItems(query: String, pageSize: Int, offset: Int, language: String): List<GameItem> {
    return when (language) {
      ENGLISH_LANGUAGE    -> getGameItemsEN(query, pageSize, offset)
      GERMAN_LANGUAGE     -> getGameItemsDE(query, pageSize, offset)
      FRENCH_LANGUAGE     -> getGameItemsFR(query, pageSize, offset)
      RUSSIAN_LANGUAGE    -> getGameItemsRU(query, pageSize, offset)
      POLISH_LANGUAGE     -> getGameItemsPL(query, pageSize, offset)
      SPANISH_LANGUAGE    -> getGameItemsES(query, pageSize, offset)
      PORTUGUESE_LANGUAGE -> getGameItemsPT(query, pageSize, offset)
      CHINESE_LANGUAGE    -> getGameItemsCN(query, pageSize, offset)
      KOREAN_LANGUAGE     -> getGameItemsKR(query, pageSize, offset)
      else                -> getGameItemsEN(query, pageSize, offset)
    }
  }
  
  suspend fun getGameItem(itemId: String, language: String): GameItemSample {
    return GameItemSample(
      itemID = itemId,
      name = when (language) {
        ENGLISH_LANGUAGE    -> getGameItemEN(itemId)
        GERMAN_LANGUAGE     -> getGameItemDE(itemId)
        FRENCH_LANGUAGE     -> getGameItemFR(itemId)
        RUSSIAN_LANGUAGE    -> getGameItemRU(itemId)
        POLISH_LANGUAGE     -> getGameItemPL(itemId)
        SPANISH_LANGUAGE    -> getGameItemES(itemId)
        PORTUGUESE_LANGUAGE -> getGameItemPT(itemId)
        CHINESE_LANGUAGE    -> getGameItemCN(itemId)
        KOREAN_LANGUAGE     -> getGameItemKR(itemId)
        else                -> getGameItemEN(itemId)
      }
    )
  }
  
  @Query("SELECT en FROM game_items_table WHERE itemId LIKE :query")
  suspend fun getGameItemEN(query: String): String
  
  @Query("SELECT de FROM game_items_table WHERE itemId LIKE :query")
  suspend fun getGameItemDE(query: String): String
  
  @Query("SELECT fr FROM game_items_table WHERE itemId LIKE :query")
  suspend fun getGameItemFR(query: String): String
  
  @Query("SELECT ru FROM game_items_table WHERE itemId LIKE :query")
  suspend fun getGameItemRU(query: String): String
  
  @Query("SELECT pl FROM game_items_table WHERE itemId LIKE :query")
  suspend fun getGameItemPL(query: String): String
  
  @Query("SELECT es FROM game_items_table WHERE itemId LIKE :query")
  suspend fun getGameItemES(query: String): String
  
  @Query("SELECT pt FROM game_items_table WHERE itemId LIKE :query")
  suspend fun getGameItemPT(query: String): String
  
  @Query("SELECT cn FROM game_items_table WHERE itemId LIKE :query")
  suspend fun getGameItemCN(query: String): String
  
  @Query("SELECT kr FROM game_items_table WHERE itemId LIKE :query")
  suspend fun getGameItemKR(query: String): String
  
}