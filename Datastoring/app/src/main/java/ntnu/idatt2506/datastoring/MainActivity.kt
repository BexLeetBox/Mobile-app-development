package ntnu.idatt2506.datastoring


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**
        val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt("backgroundColor", 1)
        editor.apply()

        val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val bgColor = sharedPref.getInt("backgroundColor", defaultColor)
        */

        val button: Button = findViewById(R.id.loadMoviesButton)
        button.setOnClickListener {
            initDatabaseFromRawResource()
        }

    }

    private fun initDatabaseFromRawResource() {
        val db: SQLiteDatabase = openOrCreateDatabase("MoviesDB", MODE_PRIVATE, null)
        db.execSQL("CREATE TABLE IF NOT EXISTS movies(title VARCHAR, director VARCHAR, actor1 VARCHAR, actor2 VARCHAR)")

        try {
            val inputStream = resources.openRawResource(R.raw.movies)
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            inputStream.close()
            val json = String(buffer, Charsets.UTF_8)

            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val title = obj.getString("title")
                val director = obj.getString("director")
                val actorsArray = obj.getJSONArray("actors")
                val actor1 = actorsArray.getString(0)
                val actor2 = actorsArray.getString(1)

                db.execSQL("INSERT INTO movies VALUES(?, ?, ?, ?);", arrayOf(title, director, actor1, actor2))
            }
        } catch (e: IOException) {
            Log.e("MainActivity", "Error reading movies.json", e)
        } catch (e: Exception) {
            Log.e("MainActivity", "Error inserting into database", e)
        } finally {
            db.close()
        }
    }
}
