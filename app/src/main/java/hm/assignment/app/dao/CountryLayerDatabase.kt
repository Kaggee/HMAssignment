package hm.assignment.app.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import hm.assignment.app.api.models.CountryModel

/**
 * Created on 2022-03-06.
 * CopyrightⒸ Kagge
 */

@Database(entities = [CountryModel::class], version = 1)
abstract class CountryLayerDatabase: RoomDatabase() {
    abstract fun countryDao(): CountryDao
}