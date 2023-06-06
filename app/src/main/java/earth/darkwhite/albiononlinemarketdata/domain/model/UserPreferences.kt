package earth.darkwhite.albiononlinemarketdata.domain.model

import earth.darkwhite.albiononlinemarketdata.util.Constant.ENGLISH_LANGUAGE
import earth.darkwhite.albiononlinemarketdata.util.Constant.WEST_SERVER

data class UserPreferences(
  val itemLanguage: String = ENGLISH_LANGUAGE,
  val westServer: Boolean = WEST_SERVER
)
