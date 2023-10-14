package ntnu.idatt2506.datastoring

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import java.io.IOException
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize SharedPreferences
        val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        // Get current saved background color
        val mainLayout: View = findViewById(R.id.mainLayout) // Assuming you have a root layout with this ID
        val bgColor = sharedPref.getInt("backgroundColor", resources.getColor(R.color.black, null))
        mainLayout.setBackgroundColor(bgColor)

        // Set up the spinner
        val spinner: Spinner = findViewById(R.id.colorSpinner)
        val adapter = ArrayAdapter.createFromResource(this, R.array.color_names, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        // Get saved color position
        val colorNames = resources.getIntArray(R.array.color_values) // Assuming you have an integer-array of color values
        val savedColorPosition = colorNames.indexOf(bgColor)

        // Initialize without firing onItemSelected
        spinner.setSelection(savedColorPosition, false)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedColor = when (position) {
                    0 -> resources.getColor(R.color.purple_200, null)
                    1 -> resources.getColor(R.color.purple_500, null)
                    2 -> resources.getColor(R.color.purple_700, null)
                    3 -> resources.getColor(R.color.teal_200, null)
                    4 -> resources.getColor(R.color.teal_700, null)
                    5 -> resources.getColor(R.color.black, null)
                    6 -> resources.getColor(R.color.white, null)
                    else -> resources.getColor(R.color.white, null)
                }

                mainLayout.setBackgroundColor(selectedColor)
                editor.putInt("backgroundColor", selectedColor).apply()
            }
        }
    }

    private fun saveMoviesToFile() {
        val movieList = listOf(
            mapOf("title" to "Movie 1", "director" to "Director 1", "actors" to listOf("Actor 1", "Actor 2")),
            // ... add more movies
        )

        val json = JSONArray(movieList).toString()

        applicationContext.openFileOutput("movies.json", Context.MODE_PRIVATE).use {
            val writer = OutputStreamWriter(it)
            writer.write(json)
            writer.close()
        }
    }

    private fun loadMoviesFromFile(): List<Map<String, Any>> {
        val json = applicationContext.openFileInput("movies.json").bufferedReader().use {
            it.readText()
        }
        return JSONArray(json).let {
            List(it.length()) { index ->
                it.getJSONObject(index).let { jsonObj ->
                    mapOf(
                        "title" to jsonObj.getString("title"),
                        "director" to jsonObj.getString("director"),
                        "actors" to jsonObj.getJSONArray("actors").let { jsonArr ->
                            List(jsonArr.length()) { arrIndex ->
                                jsonArr.getString(arrIndex)
                            }
                        }
                    )
                }
            }
        }
    }

    private fun initDatabaseFromRawResource() {
        // Using SQLite database to store movie data
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
