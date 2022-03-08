package hm.assignment.app.koin.viewmodels

import android.content.Context
import hm.assignment.app.api.models.CountryModel
import hm.assignment.app.dao.CountryDao
import hm.assignment.app.di.networkModule
import hm.assignment.app.di.repositoryModule
import hm.assignment.app.di.viewModelModule
import hm.assignment.app.screens.favourites.FavouriteViewModel
import hm.assignment.app.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mockito
import kotlin.test.assertEquals

/**
 * Created on 2022-03-08.
 * CopyrightⒸ Kagge
 */

@ExperimentalCoroutinesApi
class FavouriteViewModelTest: KoinTest {
    private val context: Context = Mockito.mock(Context::class.java)
    private val countryDao: CountryDao by inject()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun before() {
        startKoin {
            androidContext(context)
            modules(
                networkModule,
                repositoryModule,
                viewModelModule
            )
        }
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun afterTest() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun getFavourites_Test() = runTest {
        val vm = FavouriteViewModel(countryDao, testDispatcher)
        assertEquals(UiState.Loading, vm.uiState.value)
        vm.getFavourites()
        assertEquals(UiState.Success(listOf()), vm.uiState.value)
    }
}