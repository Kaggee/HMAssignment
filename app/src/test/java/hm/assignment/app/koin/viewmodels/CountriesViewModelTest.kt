package hm.assignment.app.koin.viewmodels

import android.content.Context
import hm.assignment.app.di.networkModule
import hm.assignment.app.di.repositoryModule
import hm.assignment.app.di.viewModelModule
import hm.assignment.app.screens.countries.CountriesViewModel
import hm.assignment.app.util.UiState
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

class CountriesViewModelTest: KoinTest {
    private val context: Context = Mockito.mock(Context::class.java)
    private val vm: CountriesViewModel by inject()

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
    }

    @After
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun search() {
        val word = "search"
        vm.filterByWord(word)
        assertEquals(word, vm.getSearchWord())
        assertEquals(UiState.Success(listOf()), vm.objectState.value)
    }

    @Test
    fun setStateLoading_Test() {
        vm.setStateLoading()
        assertEquals(UiState.Loading, vm.uiState.value)
    }
}