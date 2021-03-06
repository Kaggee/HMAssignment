package hm.assignment.app.di

import hm.assignment.app.screens.countries.CountriesViewModel
import hm.assignment.app.screens.country.CountryViewModel
import hm.assignment.app.screens.favourites.FavouriteViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created on 2022-03-05.
 * Copyrightâ’¸ Kagge
 */

val viewModelModule = module {
    viewModel { CountriesViewModel(get(), get()) }
    viewModel { CountryViewModel(get()) }
    viewModel { FavouriteViewModel(get(), Dispatchers.IO) }
}