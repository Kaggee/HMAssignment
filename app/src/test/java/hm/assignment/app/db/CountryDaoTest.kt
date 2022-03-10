package hm.assignment.app.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import hm.assignment.app.api.models.CountryModel
import hm.assignment.app.dao.CountryLayerDatabase
import hm.assignment.app.util.Constants
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Created on 2022-03-08.
 * CopyrightⒸ Kagge
 */

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
class CountryDaoTest {
    private val context: Context = Mockito.mock(Context::class.java)
    private val mainThreadSurrogate = newSingleThreadContext("UIThread")
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var database: CountryLayerDatabase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupDatabase() {
        Dispatchers.setMain(mainThreadSurrogate)
        database = Room
            .inMemoryDatabaseBuilder(context, CountryLayerDatabase::class.java)
            .addMigrations(CountryLayerDatabase.MIGRATION_1_2)
            .build()
    }

    @Test
    fun insert_Test() = runBlocking {
        val countries = arrayListOf<CountryModel>()
        countries.add(CountryModel("Western Sahara", "El Aaiún", "Africa", "EH"))
        countries.add(CountryModel("Jamaica", "Kingston", "Americas", "JM"))
        countries.add(CountryModel("Egypt", "Cairo", "Africa", "EG"))

        var country: CountryModel? = null
        database.countryDao().insertCountries(countries)
        country = database.countryDao().getCountry(countries[1].name)

        assertNotNull(country)
        assertEquals(countries[1], country)
    }

    @After
    fun tearDown() {
        database.close()
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}