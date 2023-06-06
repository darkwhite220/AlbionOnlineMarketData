package earth.darkwhite.albiononlinemarketdata.ui.screens.settings

sealed interface SettingsEvent {
  data class UpdateItemLanguage(val newValue: String) : SettingsEvent
  data class UpdateServerRegion(val newValue: Boolean) : SettingsEvent
}