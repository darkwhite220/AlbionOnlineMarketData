package earth.darkwhite.albiononlinemarketdata.ui.screens.settings

import earth.darkwhite.albiononlinemarketdata.domain.model.Server

sealed interface SettingsEvent {
  data class UpdateItemLanguage(val newValue: String) : SettingsEvent
  data class UpdateServerRegion(val newValue: Server) : SettingsEvent
}