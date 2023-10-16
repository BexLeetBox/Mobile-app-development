package ntnu.idatt2506.datastoring

import ntnu.idatt2506.datastoring.entity.Movie

sealed class FilterResult {
    data class MoviesAndActors(val list: List<Movie>) : FilterResult()
    data class Movies(val list: List<Movie>) : FilterResult()  // Added this line
    data class Actors(val list: List<String>) : FilterResult()
}
