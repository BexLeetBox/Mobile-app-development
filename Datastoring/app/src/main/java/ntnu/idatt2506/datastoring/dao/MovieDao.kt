package ntnu.idatt2506.datastoring.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ntnu.idatt2506.datastoring.entity.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAll(): List<Movie>

    @Insert
    fun insertAll(vararg movies: Movie)
}
