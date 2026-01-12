package hu.toliver.whacook

import android.app.Application
import hu.toliver.whacook.data.di.startAppKoin
import hu.toliver.whacook.data.local.databaseApplicationContext
import org.koin.android.ext.koin.androidContext

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        databaseApplicationContext = this
        startAppKoin {
            androidContext(this@MainApplication)
        }
    }
}
