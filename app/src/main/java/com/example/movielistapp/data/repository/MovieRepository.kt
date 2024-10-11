package com.example.movielistapp.data.repository

import com.example.movielistapp.data.dao.MovieDao
import com.example.movielistapp.data.model.Movie
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {

    val allMovies: Flow<List<Movie>> = movieDao.getAllMovies()

    suspend fun insert(movie: Movie) {
        movieDao.insertMovie(movie)
    }

    suspend fun update(movie: Movie) {
        movieDao.updateMovie(movie)
    }

    suspend fun delete(movie: Movie) {
        movieDao.deleteMovie(movie)
    }
}
