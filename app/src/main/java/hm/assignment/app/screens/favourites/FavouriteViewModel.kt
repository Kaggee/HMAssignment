package hm.assignment.app.screens.favourites

import androidx.lifecycle.viewModelScope
import hm.assignment.app.api.models.CountryModel
import hm.assignment.app.dao.CountryDao
import hm.assignment.app.screens.BaseViewModel
import hm.assignment.app.util.UiState
import kotlinx.coroutines.*

/**
 * Created on 2022-03-07.
 * Copyrightâ’¸ Kagge
 */
class FavouriteViewModel(
    private val countryDb: CountryDao,
    private val ioDispatcher: CoroutineDispatcher
): BaseViewModel<List<CountryModel>>() {

    fun getFavourites() {
        viewModelScope.launch(ioDispatcher) {
            // Delay, "cheating" version for letting transition animation finish.
            delay(750)
            val countries = countryDb.getFavourites()
            withContext(Dispatchers.Main) {
                viewModelUiState.value = UiState.Success(countries)
            }
        }
    }
}