package hm.assignment.app.screens.favourites

import androidx.lifecycle.viewModelScope
import hm.assignment.app.api.models.CountryModel
import hm.assignment.app.dao.CountryDao
import hm.assignment.app.screens.BaseViewModel
import hm.assignment.app.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created on 2022-03-07.
 * CopyrightⒸ Kagge
 */
class FavouriteViewModel(private val countryDb: CountryDao): BaseViewModel<List<CountryModel>>() {

    fun getFavourites() {
        viewModelScope.launch(Dispatchers.IO) {
            // Delay, "cheating" version for letting transition animation finish.
            delay(750)
            val countries = countryDb.getFavourites()
            withContext(Dispatchers.Main) {
                viewModelUiState.value = UiState.Success(countries)
            }
        }
    }
}