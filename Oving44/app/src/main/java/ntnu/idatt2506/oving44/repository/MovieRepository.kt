package ntnu.idatt2506.oving44.repository

import ntnu.idatt2506.oving44.entity.Item

import android.content.Context
import ntnu.idatt2506.oving44.R

object MovieRepository {

    val movies = listOf(
        Item("Harry Potter", "Description for Harry Potter from strings.xml", R.drawable.hp),
        Item("Lord of the Rings", "Description for Lord of the Rings from strings.xml", R.drawable.lotr)
        // ... add more items if needed
    )
    fun getMovies(context: Context) = listOf(
        Item("Harry Potter", context.getString(R.string.description_harry_potter), R.drawable.hp),
        Item("Lord of the Rings", context.getString(R.string.description_lotr), R.drawable.lotr)
        // ... add more items if needed
    )
}
