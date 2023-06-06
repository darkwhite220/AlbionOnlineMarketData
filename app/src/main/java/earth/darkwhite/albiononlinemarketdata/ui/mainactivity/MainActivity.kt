package earth.darkwhite.albiononlinemarketdata.ui.mainactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import earth.darkwhite.albiononlinemarketdata.ui.screens.marketapp.MarketApp
import earth.darkwhite.albiononlinemarketdata.ui.theme.AlbionOnlineMarketDataTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  
    WindowCompat.setDecorFitsSystemWindows(window, false)
    
    setContent {
      AlbionOnlineMarketDataTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          MarketApp()
        }
      }
    }
  }
}
