package com.example.movielistapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.movielistapp.data.dao.MovieDatabase
import com.example.movielistapp.data.model.Movie
import com.example.movielistapp.data.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MovieRepository
    val allMovies: LiveData<List<Movie>>

    init {
        val movieDao = MovieDatabase.getDatabase(application).movieDao()
        repository = MovieRepository(movieDao)
        allMovies = repository.allMovies.asLiveData()
    }

    fun addMovie(title: String) {
        viewModelScope.launch {
            repository.insert(Movie(title = title))
        }
    }

    fun markAsWatched(movie: Movie) {
        viewModelScope.launch {
            repository.update(movie.copy(isWatched = true))
        }
    }

    fun deleteMovie(movie: Movie) {
        viewModelScope.launch {
            repository.delete(movie)
        }
    }
}
