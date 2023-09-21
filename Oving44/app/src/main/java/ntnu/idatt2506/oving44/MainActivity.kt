package ntnu.idatt2506.oving44

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import ntnu.idatt2506.oving44.entity.Item
import ntnu.idatt2506.oving44.fragment.DetailFragment
import ntnu.idatt2506.oving44.fragment.ListFragment
import ntnu.idatt2506.oving44.repository.MovieRepository


class MainActivity : AppCompatActivity(), ListFragment.OnItemSelectedListener {
    private lateinit var movies: List<Item>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movies = MovieRepository.getMovies(this)
        // Check if this is the first time we're creating this Activity instance
        if (savedInstanceState == null) {
            setupFragments()
        }
    }

    private fun setupFragments() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // List Fragment
        val listFragment = ListFragment()
        fragmentTransaction.replace(R.id.listFragmentContainer, listFragment)

        // Detail Fragment (can be added with a placeholder or with default data)
        val detailFragment = DetailFragment()
        fragmentTransaction.replace(R.id.detailFragmentContainer, detailFragment)

        fragmentTransaction.commit()
    }

    // This function is called when an item in the list is clicked
    override fun onItemSelected(item: Item) {
        val detailFragment = supportFragmentManager.findFragmentById(R.id.detailFragmentContainer) as? DetailFragment
        currentMovieIndex = movies.indexOf(item) // Set the current index when an item is clicked.
        detailFragment?.updateItem(item)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private var currentMovieIndex = 0

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val detailFragment = supportFragmentManager.findFragmentById(R.id.detailFragmentContainer) as? DetailFragment

        when (item.itemId) {
            R.id.action_prev -> {
                if (currentMovieIndex > 0) {
                    currentMovieIndex--
                    detailFragment?.updateItem(movies[currentMovieIndex])
                }
            }
            R.id.action_next -> {
                if (currentMovieIndex < movies.size - 1) {
                    currentMovieIndex++
                    detailFragment?.updateItem(movies[currentMovieIndex])
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

