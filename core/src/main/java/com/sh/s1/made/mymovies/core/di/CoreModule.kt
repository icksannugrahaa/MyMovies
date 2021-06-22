package com.sh.s1.made.mymovies.core.di

import androidx.room.Room
import com.sh.s1.made.mymovies.core.data.MovieRepository
import com.sh.s1.made.mymovies.core.data.source.local.LocalDataSource
import com.sh.s1.made.mymovies.core.data.source.local.room.MovieDatabase
import com.sh.s1.made.mymovies.core.data.source.remote.RemoteDataSource
import com.sh.s1.made.mymovies.core.data.source.remote.network.ApiService
import com.sh.s1.made.mymovies.core.domain.repository.IMovieRepository
import com.sh.s1.made.mymovies.core.utils.AppExecutors
import com.sh.s1.made.mymovies.core.utils.Consts
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object CoreModule {
    val databaseModule = module {
        factory { get<MovieDatabase>().movieDao() }
        single {
            val passphrase: ByteArray = SQLiteDatabase.getBytes("sh".toCharArray())
            val factory = SupportFactory(passphrase)
            Room.databaseBuilder(
                androidContext(),
                MovieDatabase::class.java, "Movie.db"
            ).fallbackToDestructiveMigration().openHelperFactory(factory).build()
        }
    }

    val networkModule = module {
        single {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
        }
        single {
            val retrofit = Retrofit.Builder()
                .baseUrl("${Consts.BASE_URL}/${Consts.API_VER}/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(get())
                .build()
            retrofit.create(ApiService::class.java)
        }
    }

    val repositoryModule = module {
        single { LocalDataSource(get()) }
        single { RemoteDataSource(get()) }
        factory { AppExecutors() }
        single<IMovieRepository> {
            MovieRepository(
                get(),
                get(),
                get()
            )
        }
    }

}