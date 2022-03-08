package hm.assignment.app.koin

import android.content.Context
import hm.assignment.app.api.CountryLayerApi
import hm.assignment.app.api.repository.CountryLayerRepository
import hm.assignment.app.dao.CountryDao
import hm.assignment.app.dao.CountryLayerDatabase
import hm.assignment.app.di.networkModule
import hm.assignment.app.di.repositoryModule
import hm.assignment.app.di.viewModelModule
import hm.assignment.app.screens.countries.CountriesViewModel
import hm.assignment.app.screens.country.CountryViewModel
import hm.assignment.app.screens.favourites.FavouriteViewModel
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.koin.test.get
import org.mockito.Mockito.mock
import kotlin.test.assertNotNull

/**
 * Created on 2022-03-08.
 * CopyrightⒸ Kagge
 */
class HMKoinTest: KoinTest {
    private val context: Context = mock(Context::class.java)

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

    @Test
    fun verifyKoinModules() {
        koinApplication {
            androidContext(context)
            modules(networkModule, repositoryModule, viewModelModule)
            checkModules()
        }
    }

    @Test
    fun testNetworkInject() {
        val okHttp = get<OkHttpClient>()
        val api = get<CountryLayerApi>()

        assertNotNull(okHttp, "OkHttp not injected successfully")
        assertNotNull(api, "api not injected successfully")
    }

    @Test
    fun testRepositoryInject() {
        val countryLayerRepository = get<CountryLayerRepository>()
        val countryLayerDatabase = get<CountryLayerDatabase>()
        val countryLayerDao = get<CountryDao>()

        assertNotNull(countryLayerRepository, "countryLayerRepository not injected successfully")
        assertNotNull(countryLayerDatabase, "countryLayerDatabase not injected successfully")
        assertNotNull(countryLayerDao, "countryLayerDao not injected successfully")
    }

    @Test
    fun testViewModelInject() {
        val countriesVm = get<CountriesViewModel>()
        val countryVm = get<CountryViewModel>()
        val favouriteVm = get<FavouriteViewModel>()

        assertNotNull(countriesVm, "countriesVm not injected successfully")
        assertNotNull(countryVm, "countryVm not injected successfully")
        assertNotNull(favouriteVm, "favouriteVm not injected successfully")
    }

    @After
    fun afterTest() {
        stopKoin()
    }
}