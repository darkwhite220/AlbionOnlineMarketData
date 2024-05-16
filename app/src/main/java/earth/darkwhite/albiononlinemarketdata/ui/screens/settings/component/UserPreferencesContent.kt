package earth.darkwhite.albiononlinemarketdata.ui.screens.settings.component

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import earth.darkwhite.albiononlinemarketdata.R
import earth.darkwhite.albiononlinemarketdata.domain.model.Server
import earth.darkwhite.albiononlinemarketdata.ui.components.ProgressBar
import earth.darkwhite.albiononlinemarketdata.ui.components.cardElevation
import earth.darkwhite.albiononlinemarketdata.ui.components.largePadValue
import earth.darkwhite.albiononlinemarketdata.ui.screens.settings.SettingsEvent
import earth.darkwhite.albiononlinemarketdata.ui.screens.settings.SettingsUiState
import earth.darkwhite.albiononlinemarketdata.util.Constant


@Composable
fun UserPreferencesContent(
  preferences: SettingsUiState,
  onSettingsEvent: (event: SettingsEvent) -> Unit
) {
  Card(
    modifier = Modifier.padding(largePadValue),
    elevation = CardDefaults.cardElevation(defaultElevation = cardElevation)
  ) {
    when (preferences) {
      SettingsUiState.Loading -> {
        ProgressBar()
      }
      
      is SettingsUiState.Success -> {
        ItemLanguageRow(
          itemLanguage = preferences.data.itemLanguage,
          onItemLanguageChange = onSettingsEvent
        )
        Divider(modifier = Modifier.padding(horizontal = largePadValue))
        ServerRow(
          currentServer = preferences.data.server,
          onClick = onSettingsEvent
        )
      }
    }
  }
}

@Composable
private fun ItemLanguageRow(
  itemLanguage: String,
  onItemLanguageChange: (event: SettingsEvent) -> Unit
) {
  var languageDropDownState by remember { mutableStateOf(false) }
  Row(
    modifier = Modifier.clickable { languageDropDownState = true },
    horizontalArrangement = Arrangement.spacedBy(largePadValue)
  ) {
    Text(
      modifier = Modifier
        .weight(1f)
        .padding(PaddingValues(start = largePadValue, top = largePadValue, bottom = largePadValue)),
      text = stringResource(R.string.language)
    )
    
    Box {
      Text(
        modifier = Modifier
          .padding(PaddingValues(end = largePadValue, top = largePadValue, bottom = largePadValue)),
        text = stringResource(id = gameLanguageList.first { it.symbol == itemLanguage }.name)
      )
      DropdownMenu(
        expanded = languageDropDownState,
        onDismissRequest = { languageDropDownState = false }
      ) {
        gameLanguageList.forEach { item ->
          DropdownMenuItem(
            onClick = {
              onItemLanguageChange(SettingsEvent.UpdateItemLanguage(item.symbol))
              languageDropDownState = false
            },
            text = { Text(text = stringResource(id = item.name)) },
            leadingIcon = { Text(text = stringResource(id = item.flag)) },
            trailingIcon = {
              if (item.symbol == itemLanguage) Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = null
              )
            }
          )
        }
      }
    }
  }
}

@Composable
private fun ServerRow(
  currentServer: Server,
  onClick: (event: SettingsEvent) -> Unit
) {
  var serverDropDownState by remember { mutableStateOf(false) }
  Row(
    modifier = Modifier.clickable { serverDropDownState = true },
    horizontalArrangement = Arrangement.spacedBy(largePadValue)
  ) {
    Text(
      modifier = Modifier
        .weight(1f)
        .padding(PaddingValues(start = largePadValue, top = largePadValue, bottom = largePadValue)),
      text = stringResource(R.string.server)
    )
    val serverText = when (currentServer) {
      Server.AMERICA -> R.string.west
      Server.ASIA -> R.string.east
      Server.EUROPE -> R.string.europe
    }
    Box {
      Text(
        modifier = Modifier
          .padding(PaddingValues(end = largePadValue, top = largePadValue, bottom = largePadValue)),
        text = stringResource(id = serverText)
      )
      DropdownMenu(
        expanded = serverDropDownState,
        onDismissRequest = { serverDropDownState = false }
      ) {
        serversList.forEach { item ->
          DropdownMenuItem(
            onClick = {
              onClick(SettingsEvent.UpdateServerRegion(item.server))
              serverDropDownState = false
            },
            text = { Text(text = stringResource(id = item.textId)) },
            trailingIcon = {
              if (item.server == currentServer) Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = null
              )
            }
          )
        }
      }
    }
  }
}

data class ServerItem(
  val server: Server,
  @StringRes val textId: Int,
)

private val serversList = listOf(
  ServerItem(
    Server.AMERICA,
    R.string.west
  ),
  ServerItem(
    Server.EUROPE,
    R.string.europe
  ),
  ServerItem(
    Server.ASIA,
    R.string.east
  ),
)

data class GameLanguage(val symbol: String, @StringRes val name: Int, @StringRes val flag: Int)

private val gameLanguageList = listOf(
  GameLanguage(
    symbol = Constant.ENGLISH_LANGUAGE,
    name = R.string.english,
    flag = R.string.flag_us
  ),
  GameLanguage(
    symbol = Constant.GERMAN_LANGUAGE,
    name = R.string.deutsch,
    flag = R.string.flag_german
  ),
  GameLanguage(
    symbol = Constant.FRENCH_LANGUAGE,
    name = R.string.francais,
    flag = R.string.flag_french
  ),
  GameLanguage(
    symbol = Constant.RUSSIAN_LANGUAGE,
    name = R.string.russian,
    flag = R.string.flag_russian
  ),
  GameLanguage(
    symbol = Constant.POLISH_LANGUAGE,
    name = R.string.polish,
    flag = R.string.flag_polish
  ),
  GameLanguage(
    symbol = Constant.SPANISH_LANGUAGE,
    name = R.string.spanish,
    flag = R.string.flag_spanish
  ),
  GameLanguage(
    symbol = Constant.PORTUGUESE_LANGUAGE,
    name = R.string.portuguese,
    flag = R.string.flag_portuguese
  ),
  GameLanguage(
    symbol = Constant.CHINESE_LANGUAGE,
    name = R.string.chinese,
    flag = R.string.flag_chinese
  ),
  GameLanguage(
    symbol = Constant.KOREAN_LANGUAGE,
    name = R.string.korean,
    flag = R.string.flag_korean
  )
)