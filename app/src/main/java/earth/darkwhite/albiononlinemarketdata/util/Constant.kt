package earth.darkwhite.albiononlinemarketdata.util

object Constant {
  const val DB_ASSET_FILE_NAME = "game_items_table_v4.db"
  const val INITIAL_PAGE_INDEX = 0
  const val SEARCH_PAGE_SIZE = 30
  
  const val CATEGORY_MAX_ITEMS_SIZE = 30
  const val STOP_TIMEOUT_MILLIS = 5_000L
  const val CATEGORY_DEFAULT_INDEX = -1L
  
  const val EMPTY_STRING = ""
  const val ITEMS_IDS_SEPARATOR = ","
  
  const val CONTENT_TYPE_CATEGORY_ITEM = "category_item"
  const val CONTENT_TYPE_GAME_ITEM = "game_item"
  
  const val WEST_SERVER_BASE_URL = "https://west.online-data.com/api/v2/"
  const val EAST_SERVER_BASE_URL = "https://east.albion-online-data.com/api/v2/"
  const val EUROPE_SERVER_BASE_URL = "https://europe.albion-online-data.com/api/v2/"
  
  const val DEFAULT_TIME = "0001-01-01T00:00:00"
  const val DEFAULT_OFFSET = "0000"
  const val AT = "@"
  
  const val DEFAULT_VALUE = "N/A"
  
  // Default game data
  const val MAX_QUALITY = 5
  const val CITIES_COUNT = 8
  
  // Preferences
  const val PREFERENCES_FILE = "settings.preferences_pb"
  
  // Item languages
  const val ENGLISH_LANGUAGE = "en"
  const val GERMAN_LANGUAGE = "de"
  const val FRENCH_LANGUAGE = "fr"
  const val RUSSIAN_LANGUAGE = "ru"
  const val POLISH_LANGUAGE = "pl"
  const val SPANISH_LANGUAGE = "es"
  const val PORTUGUESE_LANGUAGE = "pt"
  const val CHINESE_LANGUAGE = "cn"
  const val KOREAN_LANGUAGE = "kr"
}