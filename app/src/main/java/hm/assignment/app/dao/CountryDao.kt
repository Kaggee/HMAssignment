package hm.assignment.app.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hm.assignment.app.api.models.CountryModel
import hm.assignment.app.util.Constants

/**
 * Created on 2022-03-06.
 * CopyrightⒸ Kagge
 */

@Dao
interface CountryDao {
    @Query("SELECT * FROM ${Constants.CountriesTable}")
    fun getAllCountries(): List<CountryModel>

    @Query("SELECT * FROM ${Constants.CountriesTable} WHERE region LIKE :region")
    fun getAllCountriesByRegion(region: String): List<CountryModel>

    @Query("SELECT * FROM ${Constants.CountriesTable} WHERE name like :name")
    fun getCountry(name: String): CountryModel

    @Query("SELECT * FROM ${Constants.CountriesTable} WHERE name like :name || '%'")
    fun searchCountry(name: String): List<CountryModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountries(countries: List<CountryModel>)
}