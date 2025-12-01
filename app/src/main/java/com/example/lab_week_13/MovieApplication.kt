package com.example.lab_week_13

import android.app.Application
import androidx.work.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.example.lab_week_13.api.MovieService
import com.example.lab_week_13.database.MovieDatabase
import java.util.concurrent.TimeUnit

class MovieApplication : Application() {

    lateinit var movieDatabase: MovieDatabase
        private set

    lateinit var movieRepository: MovieRepository
        private set

    override fun onCreate() {
        super.onCreate()

        // ✔ Init Database
        movieDatabase = MovieDatabase.getInstance(applicationContext)

        // ✔ Init Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val movieService = retrofit.create(MovieService::class.java)

        // ✔ Init Repository
        movieRepository = MovieRepository(movieService, movieDatabase)

        // ✔ NOW setup WorkManager here
        setupBackgroundWorker()
    }

    private fun setupBackgroundWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<MovieWorker>(
            1, TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .addTag("movie-work")
            .build()

        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                "movie-work",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
    }
}
