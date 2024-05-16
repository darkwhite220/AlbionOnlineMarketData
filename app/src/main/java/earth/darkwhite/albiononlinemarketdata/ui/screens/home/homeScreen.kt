package earth.darkwhite.albiononlinemarketdata.ui.screens.home

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import earth.darkwhite.albiononlinemarketdata.database.model.Category
import earth.darkwhite.albiononlinemarketdata.database.model.GameItemSample
import earth.darkwhite.albiononlinemarketdata.ui.components.HomeFab
import earth.darkwhite.albiononlinemarketdata.ui.components.topbar.HomeTopAppBar
import earth.darkwhite.albiononlinemarketdata.ui.screens.home.components.CategoryContent
import earth.darkwhite.albiononlinemarketdata.ui.theme.AlbionOnlineMarketDataTheme
import kotlinx.coroutines.flow.flowOf


@Composable
fun HomeScreen(
  onSettingsClick: () -> Unit,
  onFetchClick: (itemsIDs: String) -> Unit,
  viewModel: HomeViewModel = hiltViewModel()
) {
  val homeUiState by viewModel.homeUiState.collectAsStateWithLifecycle()
  val searchResult = viewModel.searchResult.collectAsLazyPagingItems()
  val query by viewModel.query.collectAsStateWithLifecycle()
  val newCategoryItems = viewModel.newCategoryItems
  val fetchItemsIDs = viewModel.fetchItemsIDs
  
  HomeContent(
    homeUiState = homeUiState,
    query = query,
    searchResult = searchResult,
    newCategoryItems = newCategoryItems,
    onHomeEvent = viewModel::onEvent,
    onSettingsClick = onSettingsClick
  )
  
  if (fetchItemsIDs.isNotEmpty()) {
    viewModel.onEvent(HomeEvent.OnFetchClick(onFetchClick))
  }
}

@Composable
fun HomeContent(
  homeUiState: HomeUiState,
  query: String,
  searchResult: LazyPagingItems<GameItemSample>,
  newCategoryItems: MutableList<String>,
  onHomeEvent: (HomeEvent) -> Unit,
  onSettingsClick: () -> Unit
) {
  var searchState by remember { mutableStateOf(false) }
  var fabState by remember { mutableStateOf(false) }
  val fabTransitionData = updateFabTransitionData(fabState)
  
  Scaffold(
    contentWindowInsets = WindowInsets(0, 0, 0, 0),
    modifier = Modifier
      .navigationBarsPadding()
      .imePadding(),
    topBar = {
      HomeTopAppBar(
        searchState = searchState,
        query = query,
        searchResult = searchResult,
        onActiveSearchState = { searchState = true },
        onSearchStateChange = {
          onHomeEvent(HomeEvent.OnCloseSearch)
          searchState = !searchState
          fabState = false
        },
        onQueryChange = { onHomeEvent(HomeEvent.OnQueryChange(it)) },
        onItemClick = { gameItem ->
          if (fabState) {
            onHomeEvent(HomeEvent.OnAddNewCategoryItem(GameItemSample = gameItem))
          } else {
            onHomeEvent(HomeEvent.OnDataClick(GameItemSample = gameItem))
            searchState = false
          }
        },
        onSettingsClick = onSettingsClick,
        fabState = fabState,
        fabTransitionData = fabTransitionData,
        newCategoryItems = newCategoryItems,
        onHomeEvent = onHomeEvent,
        onStateChange = { state ->
          searchState = state
          fabState = state
        }
      )
    },
    floatingActionButton = {
      HomeFab(
        fabState = fabState,
        searchState = searchState,
        onClick = {
          searchState = true
          fabState = true
        }
      )
    },
    floatingActionButtonPosition = FabPosition.Center,
  ) { paddingValues ->
    CategoryContent(
      paddingValues = paddingValues,
      homeUiState = homeUiState,
      onHomeEvent = { event ->
        if (event is HomeEvent.OnEditCategory) {
          searchState = true
          fabState = true
        }
        onHomeEvent(event)
      }
    )
  }
}

class FabTransitionData(
  elevation: State<Dp>,
  shadow: State<Dp>,
  padding: State<Dp>,
  cornerSize: State<Dp>
) {
  val elevation by elevation
  val shadow by shadow
  val padding by padding
  val cornerSize by cornerSize
}

@Composable
fun updateFabTransitionData(fabState: Boolean): FabTransitionData {
  val transition = updateTransition(targetState = fabState, label = "customFabTransition")
  val elevation = transition.animateDp(label = "Elevation") {
    if (it) 0.dp else 8.dp
  }
  val shadow = transition.animateDp(label = "Shadow") {
    if (it) 0.dp else 4.dp
  }
  val paddings = transition.animateDp(label = "paddings") {
    if (it) 0.dp else 16.dp
  }
  val cornerSize = transition.animateDp(label = "cornerSize") {
    if (it) 0.dp else 16.dp
  }
  return remember(transition) { FabTransitionData(elevation, shadow, paddings, cornerSize) }
}

@Preview
@Composable
fun PreviewHomeContent() {
  val categoriesList = listOf(
    Category(
      index = 0,
      name = "Category 1",
      items = listOf("T4_BAG", "T5_BAG", "T6_BAG")
    ),
    Category(
      index = 1,
      name = "Category 2",
      items = listOf(
        "T4_BAG",
        "T5_BAG",
        "T6_BAG",
        "T4_BAG",
        "T5_BAG",
        "T6_BAG",
        "T4_BAG",
        "T5_BAG",
        "T6_BAG"
      )
    ),
    Category(
      index = 2,
      name = "Category 3",
      items = listOf("T4_BAG", "T5_BAG", "T6_BAG")
    ),
  )
  val searchResultData = listOf(
    GameItemSample(itemID = "T4_BAG", name = "Bag"),
    GameItemSample(itemID = "T5_BAG", name = "Bag"),
    GameItemSample(itemID = "T6_BAG", name = "Bag")
  )
  
  val pagingItems = flowOf(PagingData.from(searchResultData)).collectAsLazyPagingItems()
  val itemsIDs = mutableListOf(
    "T4_BAG",
    "T5_BAG",
    "T6_BAG",
    "T4_BAG",
    "T5_BAG",
    "T6_BAG",
    "T4_BAG",
    "T5_BAG",
    "T6_BAG"
  )
  AlbionOnlineMarketDataTheme {
    HomeContent(
      homeUiState = HomeUiState.Success(categoriesList),
      query = "",
      searchResult = pagingItems,
      newCategoryItems = itemsIDs,
      onHomeEvent = {},
      onSettingsClick = {}
    )
  }
}