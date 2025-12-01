package com.example.lab_week_13

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.example.lab_week_13.api.MovieService
import com.example.lab_week_13.database.MovieDatabase

class MovieApplication : Application() {

    // Jangan inisialisasi database di sini!
    // Karena applicationContext masih null pada tahap ini
    lateinit var movieDatabase: MovieDatabase
        private set

    lateinit var movieRepository: MovieRepository
        private set

    override fun onCreate() {
        super.onCreate()

        // ✔ Inisialisasi database DI SINI
        movieDatabase = MovieDatabase.getInstance(applicationContext)

        // ✔ Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val movieService = retrofit.create(MovieService::class.java)

        // ✔ Repository instance
        movieRepository = MovieRepository(movieService, movieDatabase)
    }
}
