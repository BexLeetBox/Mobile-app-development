package ntnu.idatt2506.datastoring.entity

import androidx.room.PrimaryKey

@androidx.room.Entity
data class Movie(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val director: String,
    val actors: String
)
