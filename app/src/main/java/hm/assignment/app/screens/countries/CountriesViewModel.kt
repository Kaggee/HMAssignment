package hm.assignment.app.screens.countries

import androidx.lifecycle.viewModelScope
import hm.assignment.app.api.ApiResultWrapper
import hm.assignment.app.api.models.CountryModel
import hm.assignment.app.api.repository.CountryLayerRepository
import hm.assignment.app.dao.CountryDao
import hm.assignment.app.screens.BaseViewModel
import hm.assignment.app.util.Constants
import hm.assignment.app.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created on 2022-03-05.
 * CopyrightⒸ Kagge
 */
class CountriesViewModel(
    private val repository: CountryLayerRepository,
    private val countryDao: CountryDao
): BaseViewModel<List<CountryModel>>() {
    private var mCurrentCountries = ArrayList<CountryModel>()
    private var mRegions = arrayListOf(Constants.DropdownRegionAll)
    private var mSearchWord = ""
    private var mFilteredRegion = Constants.DropdownRegionAll

    private fun getCountriesFromApiOrDb() {
        viewModelScope.launch(Dispatchers.IO) {
            // Delay, "cheating" version for letting transition animation finish.
            delay(750)
            val countries = countryDao.getAllCountries()
            if(countries.isEmpty()) {
                when(val result = repository.getAllCountries()) {
                    is ApiResultWrapper.Success -> {
                        countryDao.insertCountries(result.value)
                        sortAllRegions(countries)
                        setSuccessWithSearch(result.value)
                    }
                    is ApiResultWrapper.GenericError -> viewModelUiState.value = UiState.ApiError
                    is ApiResultWrapper.NetworkError -> viewModelUiState.value = UiState.NetworkError
                }
            } else {
                sortAllRegions(countries)
                setSuccessWithSearch(countries)
            }
        }
    }

    private fun sortAllRegions(countries: List<CountryModel>) {
        countries.distinctBy { it.region }.map { it.region }.forEach { region ->
            val notEmptyRegion = region.ifEmpty { Constants.NoRegion }
            if(!mRegions.contains(notEmptyRegion)) {
                mRegions.add(notEmptyRegion)
            }
        }
    }

    private suspend fun setSuccessWithSearch(countries: List<CountryModel>) {
        mCurrentCountries.clear()
        mCurrentCountries.addAll(countries)
        val countriesFiltered = countries.filter { it.name.lowercase().contains(mSearchWord.lowercase()) }
        withContext(Dispatchers.Main) {
            viewModelUiState.value = UiState.Success(countriesFiltered)
            viewModelObjectState.value = UiState.Loading
        }
    }

    fun filterByRegion(region: String) {
        viewModelUiState.value = UiState.Loading
        mFilteredRegion = region

        // If the region is all, the LoadingState will trigger a reload.
        if (region != Constants.DropdownRegionAll) {
            viewModelScope.launch(Dispatchers.IO) {
                val regionToFilter = if (region == Constants.NoRegion) "" else region
                val countriesFiltered = countryDao.getAllCountriesByRegion(regionToFilter)
                setSuccessWithSearch(countriesFiltered)
            }
        }
    }

    fun setFavourite(countryName: String, favourite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            countryDao.setCountryFavourite(countryName, favourite)
            mCurrentCountries.first { it.name == countryName }.favourite = favourite
        }
    }

    fun filterByWord(searchWord: String) {
        viewModelObjectState.value = UiState.Loading
        mSearchWord = searchWord
        val newCountries = mCurrentCountries.filter { it.name.lowercase().contains(searchWord.lowercase()) }
        viewModelObjectState.value = UiState.Success(newCountries)
    }

    fun getCountries() {
        if(mFilteredRegion == Constants.DropdownRegionAll) {
            getCountriesFromApiOrDb()
        }
    }

    fun setStateLoading() {
        viewModelUiState.value = UiState.Loading
    }

    fun getCurrentCountries(): List<CountryModel> {
        return mCurrentCountries
    }

    fun getSearchWord(): String {
        return mSearchWord
    }

    fun getFilterRegion(): String {
        return mFilteredRegion
    }

    fun getAllRegions(): List<String> {
        return mRegions
    }
}