package hm.assignment.app

import android.app.Application
import hm.assignment.app.di.networkModule
import hm.assignment.app.di.repositoryModule
import hm.assignment.app.di.viewModelModule
import hm.assignment.app.util.logError
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created on 2022-03-04.
 * CopyrightⒸ Kagge
 */
class AssignmentApplication: Application() {
    private val mDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()

    override fun onCreate() {
        super.onCreate()
        customUncaughtExceptionHandler()
        startKoin()
    }

    // Crash without stacktrace or info.
    private fun customUncaughtExceptionHandler() {
        val custom = Thread.UncaughtExceptionHandler { thread, throwable ->
            throwable.logError()
            mDefaultUncaughtExceptionHandler?.uncaughtException(thread, throwable)
        }
        Thread.setDefaultUncaughtExceptionHandler(custom)
    }

    private fun startKoin() {
        startKoin {
            androidContext(this@AssignmentApplication)
            modules(networkModule, repositoryModule, viewModelModule)
        }
    }
}