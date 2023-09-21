package ntnu.idatt2506.oving44

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ntnu.idatt2506.oving44.entity.Item
import ntnu.idatt2506.oving44.fragment.DetailFragment
import ntnu.idatt2506.oving44.fragment.ListFragment


class MainActivity : AppCompatActivity(), ListFragment.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    override fun onItemSelected(selectedItem: Item) {
        val detailFragment = supportFragmentManager.findFragmentById(R.id.detailFragmentContainer) as? DetailFragment
        detailFragment?.updateItem(selectedItem)
    }

}

