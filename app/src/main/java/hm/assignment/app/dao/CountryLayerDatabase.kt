package hm.assignment.app.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import hm.assignment.app.api.models.CountryModel

/**
 * Created on 2022-03-06.
 * CopyrightⒸ Kagge
 */

@Database(
    version = CountryLayerDatabase.DB_VERSION,
    entities = [
        CountryModel::class
    ],
    exportSchema = true
)
abstract class CountryLayerDatabase: RoomDatabase() {
    abstract fun countryDao(): CountryDao

    companion object {
        const val DB_VERSION = 2

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Countries ADD COLUMN favourite INTEGER NOT NULL DEFAULT(0)")
            }
        }
    }
}