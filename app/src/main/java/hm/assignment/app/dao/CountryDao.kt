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
    suspend fun getAllCountries(): List<CountryModel>

    @Query("SELECT * FROM ${Constants.CountriesTable} WHERE region LIKE :region")
    suspend fun getAllCountriesByRegion(region: String): List<CountryModel>

    @Query("SELECT * FROM ${Constants.CountriesTable} WHERE name like :name")
    suspend fun getCountry(name: String): CountryModel

    @Query("SELECT * FROM ${Constants.CountriesTable} WHERE favourite = 1")
    suspend fun getFavourites(): List<CountryModel>

    @Query("UPDATE ${Constants.CountriesTable} SET favourite = :favourite WHERE name = :countryName")
    suspend fun setCountryFavourite(countryName: String, favourite: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countries: List<CountryModel>)
}