package ntnu.idatt2506.datastoring.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ntnu.idatt2506.datastoring.entity.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAll(): List<Movie>

    @Query("SELECT * FROM movie")
    fun getAllMovies(): List<Movie>


    @Query("SELECT * FROM movie")
    fun getAllActors(): List<Movie>

    @Query("SELECT * FROM movie WHERE director = :director")
    fun getMoviesByDirector(director: String): List<Movie>

    @Insert
    fun insertAll(vararg movies: Movie)
}
