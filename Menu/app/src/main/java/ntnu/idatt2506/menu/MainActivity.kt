package ntnu.idatt2506.menu
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_firstname -> {
                Log.w("Menu Click", "Fornavn clicked")
                return true
            }
            R.id.menu_lastname -> {
                Log.e("Menu Click", "Etternavn clicked")
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
