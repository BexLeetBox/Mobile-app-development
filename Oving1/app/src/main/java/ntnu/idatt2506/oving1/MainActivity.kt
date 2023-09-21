
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ntnu.idatt2506.oving1.R

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
                Log.w("Advarsel", "Ditt fornavn: [DITT_FORNAVN]")
                return true
            }
            R.id.menu_lastname -> {
                Log.e("Feilmelding", "Ditt etternavn: [DITT_ETTERNAVN]")
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
