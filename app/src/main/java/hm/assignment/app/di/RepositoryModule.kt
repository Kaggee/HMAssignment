package hm.assignment.app.di

import android.content.Context
import androidx.room.Room
import hm.assignment.app.api.repository.CountryLayerRepository
import hm.assignment.app.dao.CountryLayerDatabase
import hm.assignment.app.util.Constants
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Created on 2022-03-05.
 * CopyrightⒸ Kagge
 */

val repositoryModule = module {
    single { CountryLayerRepository(get()) }
    single { provideCountryLayerDatabase(androidContext()) }
    single { get<CountryLayerDatabase>().countryDao() }
}

fun provideCountryLayerDatabase(context: Context): CountryLayerDatabase {
    return Room
        .databaseBuilder(context, CountryLayerDatabase::class.java, Constants.RoomDBName)
        .addMigrations(CountryLayerDatabase.MIGRATION_1_2)
        .build()
}