package ntnu.idatt2506.datastoring.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import ntnu.idatt2506.datastoring.dao.MovieDao
import kotlinx.coroutines.Dispatchers

class MovieViewModel(private val movieDao: MovieDao) : ViewModel() {

    // Fetch all movies
    val movies = liveData(Dispatchers.IO) {
        emit(movieDao.getAll())
    }

    // Fetch all actors from the movies
    val actors = liveData(Dispatchers.IO) {
        val allMovies = movieDao.getAllActors()
        val allActors = allMovies.map { it.actors }.distinct()
        emit(allActors)
    }

    // Fetch movies by director
    fun getMoviesByDirector(director: String) = liveData(Dispatchers.IO) {
        emit(movieDao.getMoviesByDirector(director))
    }
}
