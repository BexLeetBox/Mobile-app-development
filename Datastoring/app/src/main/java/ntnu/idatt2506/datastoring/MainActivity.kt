package ntnu.idatt2506.datastoring

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import ntnu.idatt2506.datastoring.adapter.MovieAdapter
import ntnu.idatt2506.datastoring.database.AppDatabase
import ntnu.idatt2506.datastoring.entity.Movie
import org.json.JSONArray
import java.io.IOException
import java.io.OutputStreamWriter
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    // Define a Job which can be used to cancel all coroutines started by this activity
    private val job = Job()
    private lateinit var movieRecyclerView: RecyclerView


    // Define the default dispatcher
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        job.cancel() // Cancel the job and all associated coroutines when the activity is destroyed
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize SharedPreferences
        val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        // Initialize movieRecyclerView here
        movieRecyclerView = findViewById(R.id.movieRecyclerView)
        movieRecyclerView.layoutManager = LinearLayoutManager(this)

        // Get current saved background color
        val mainLayout: View =
            findViewById(R.id.mainLayout) // Assuming you have a root layout with this ID
        val bgColor = sharedPref.getInt("backgroundColor", resources.getColor(R.color.black, null))
        mainLayout.setBackgroundColor(bgColor)

        // Set up the spinner
        val spinnerColor: Spinner = findViewById(R.id.colorSpinner)
        val spinnerFilter: Spinner = findViewById(R.id.infoFilterSpinner)

        spinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }


            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                launch {
                    val movies = withContext(Dispatchers.IO) {
                        filterData(position)
                    }
                    val adapter = MovieAdapter(movies)
                    movieRecyclerView.adapter = adapter
                }

            }
        }




        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.color_names,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerColor.adapter = adapter
        // Get saved color position
        val colorNames =
            resources.getIntArray(R.array.color_values) // Assuming you have an integer-array of color values
        val savedColorPosition = colorNames.indexOf(bgColor)

        // Initialize without firing onItemSelected
        spinnerColor.setSelection(savedColorPosition, false)

        spinnerColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
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



        val movieRecyclerView: RecyclerView = findViewById(R.id.movieRecyclerView)
        movieRecyclerView.layoutManager = LinearLayoutManager(this)
        val db: AppDatabase = Room.databaseBuilder(
            applicationContext, AppDatabase::class.java,
            "movies.db"
        ).build()

        val fetchDataButton: Button = findViewById(R.id.fetchDataButton)
        fetchDataButton.setOnClickListener {
            // Execute on a background thread since Room doesn't allow database operations on the main thread
            Thread {
                val movies = db.movieDao().getAllMovies()
                runOnUiThread {
                    val adapter = MovieAdapter(movies)
                    movieRecyclerView.adapter = adapter
                }
            }.start()
        }
        saveMoviesToFile()
        loadMoviesFromFile()
        initDatabaseFromRawResource()

    }

    private fun filterData(selectedPosition: Int): List<Movie> {
        val db: AppDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "movies.db").build()
        return when(selectedPosition) {
            0 -> db.movieDao().getAllMovies() // All movies
            3 -> db.movieDao().getMoviesByDirector("Christopher Nolan")
            else -> db.movieDao().getAllMovies() // For now, default to all movies
        }
    }


    private fun saveMoviesToFile() {

        val movieList = listOf(
            mapOf("title" to "Movie 1", "director" to "Director 1", "actors" to listOf("Actor 1", "Actor 2")),
            // ... add more movies
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
        val db: AppDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "movies.db").build()

        try {
            val inputStream = resources.openRawResource(R.raw.movies)
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            inputStream.close()
            val json = String(buffer, Charsets.UTF_8)

            val jsonArray = JSONArray(json)
            val movies = mutableListOf<Movie>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val title = obj.getString("title")
                val director = obj.getString("director")
                val actorsArray = obj.getJSONArray("actors")

                // Convert the actors JSON array to a comma-separated string
                val actorsList = List(actorsArray.length()) { index ->
                    actorsArray.getString(index)
                }
                val actorsString = actorsList.joinToString(", ")

                // Now, construct the Movie object with this actors string
                movies.add(Movie(0, title, director, actorsString)) // We set 0 for the id since it's auto-generated
            }
            // Save to database
            Thread {
                db.movieDao().insertAll(*movies.toTypedArray())
            }.start()

        } catch (e: IOException) {
            Log.e("MainActivity", "Error reading movies.json", e)
        } catch (e: Exception) {
            Log.e("MainActivity", "Error inserting into database", e)
        }
    }


}
