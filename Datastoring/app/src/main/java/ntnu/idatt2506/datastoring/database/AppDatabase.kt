package ntnu.idatt2506.datastoring.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ntnu.idatt2506.datastoring.dao.MovieDao
import ntnu.idatt2506.datastoring.entity.Movie

@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
