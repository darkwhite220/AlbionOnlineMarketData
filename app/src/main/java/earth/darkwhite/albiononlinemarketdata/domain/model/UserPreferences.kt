package earth.darkwhite.albiononlinemarketdata.domain.model

import earth.darkwhite.albiononlinemarketdata.util.Constant.ENGLISH_LANGUAGE

data class UserPreferences(
  val itemLanguage: String = ENGLISH_LANGUAGE,
  val server: Server = Server.AMERICA
)
