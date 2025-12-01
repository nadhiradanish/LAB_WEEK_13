package com.example.lab_week_13

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lab_week_13.model.Movie

object RecyclerViewBinding {

    @JvmStatic
    @BindingAdapter("list")
    fun bindMovies(view: RecyclerView, movies: List<Movie>?) {

        // kalau adapter belum ada -> bikin adapter baru
        if (view.adapter == null) {
            view.adapter = MovieAdapter(object : MovieAdapter.MovieClickListener {
                override fun onMovieClick(movie: Movie) {
                    // TODO: kamu bisa isi callback ini nanti
                }
            })
        }

        val adapter = view.adapter as MovieAdapter
        adapter.addMovies(movies ?: emptyList())
    }
}
