package hm.assignment.app.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import hm.assignment.app.api.models.CountryModel
import hm.assignment.app.dao.CountryLayerDatabase
import hm.assignment.app.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import kotlin.test.assertEquals

/**
 * Created on 2022-03-08.
 * CopyrightⒸ Kagge
 */

@ExperimentalCoroutinesApi
class CountryDaoTest {
    private val context: Context = Mockito.mock(Context::class.java)
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var database: CountryLayerDatabase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupDatabase() {
        Dispatchers.setMain(testDispatcher)
        database = Room
            .inMemoryDatabaseBuilder(context, CountryLayerDatabase::class.java)
            .addMigrations(CountryLayerDatabase.MIGRATION_1_2)
            .build()
    }

    @After
    fun closeDatabase() {
        database.close()
        Dispatchers.resetMain()
    }

    @Test
    fun insert_Test() = runTest(testDispatcher) {
        val countries = arrayListOf<CountryModel>()
        countries.add(CountryModel("Western Sahara", "El Aaiún", "Africa", "EH"))
        countries.add(CountryModel("Jamaica", "Kingston", "Americas", "JM"))
        countries.add(CountryModel("Egypt", "Cairo", "Africa", "EG"))


            database.countryDao().insertCountries(countries)
            val getTest = database.countryDao().getCountry(countries[1].name)
            assertEquals(countries[1], getTest)
    }
}