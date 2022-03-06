package hm.assignment.app.screens.country

import androidx.lifecycle.viewModelScope
import hm.assignment.app.api.models.CountryModel
import hm.assignment.app.api.repository.CountryLayerRepository
import hm.assignment.app.dao.CountryDao
import hm.assignment.app.screens.BaseViewModel
import hm.assignment.app.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created on 2022-03-05.
 * CopyrightⒸ Kagge
 */
class CountryViewModel(private val countryDatabase: CountryDao): BaseViewModel<CountryModel>() {
    fun getCountry(countryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            val country = countryDatabase.getCountry(countryName)
            withContext(Dispatchers.Main) {
                viewModelUiState.value = UiState.Success(country)
            }
        }
    }
}