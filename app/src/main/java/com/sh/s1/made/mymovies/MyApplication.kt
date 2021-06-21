package com.sh.s1.made.mymovies

import android.app.Application
import com.sh.s1.made.mymovies.core.di.CoreModule.databaseModule
import com.sh.s1.made.mymovies.core.di.CoreModule.networkModule
import com.sh.s1.made.mymovies.core.di.CoreModule.repositoryModule
import com.sh.s1.made.mymovies.di.AppModule.useCaseModule
import com.sh.s1.made.mymovies.di.AppModule.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}