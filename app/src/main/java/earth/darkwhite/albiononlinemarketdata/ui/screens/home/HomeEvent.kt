package earth.darkwhite.albiononlinemarketdata.ui.screens.home

import earth.darkwhite.albiononlinemarketdata.database.model.Category
import earth.darkwhite.albiononlinemarketdata.database.model.GameItemSample

sealed interface HomeEvent {
  data class OnQueryChange(val newValue: String) : HomeEvent
  data class OnAddNewCategoryItem(val GameItemSample: GameItemSample) : HomeEvent
  data class OnRemoveNewCategoryItem(val itemsID: String) : HomeEvent
  data class OnDataClick(val GameItemSample: GameItemSample? = null, val categoryItem: Category? = null) : HomeEvent
  data class OnFetchClick(val onClick: (itemsIDs: String) -> Unit) : HomeEvent
  
  data class OnDeleteCategory(val categoryItem: Category) : HomeEvent
  data class OnEditCategory(val categoryItem: Category) : HomeEvent
  
  object OnSaveNewCategory : HomeEvent
  object OnResetNewCategoryItems : HomeEvent
  object OnCloseSearch : HomeEvent
}