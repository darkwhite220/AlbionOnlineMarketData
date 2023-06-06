package earth.darkwhite.albiononlinemarketdata.database.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey
import earth.darkwhite.albiononlinemarketdata.util.Constant

@Entity(tableName = "game_items_table")
@Immutable
data class GameItem(
  @PrimaryKey
  val itemId: String,
  val en: String,
  val de: String,
  val fr: String,
  val ru: String,
  val pl: String,
  val es: String,
  val pt: String,
  val cn: String,
  val kr: String,
)

fun GameItem.toGameItemSample(language: String): GameItemSample {
  return GameItemSample(
    itemID = itemId,
    name = when (language) {
      Constant.ENGLISH_LANGUAGE    -> en
      Constant.GERMAN_LANGUAGE     -> de
      Constant.FRENCH_LANGUAGE     -> fr
      Constant.RUSSIAN_LANGUAGE    -> ru
      Constant.POLISH_LANGUAGE     -> pl
      Constant.SPANISH_LANGUAGE    -> es
      Constant.PORTUGUESE_LANGUAGE -> pt
      Constant.CHINESE_LANGUAGE    -> cn
      Constant.KOREAN_LANGUAGE     -> kr
      else                         -> en
    }
  )
}