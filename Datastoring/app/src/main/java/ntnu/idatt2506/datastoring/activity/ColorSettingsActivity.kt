package ntnu.idatt2506.datastoring.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import ntnu.idatt2506.datastoring.MainActivity
import ntnu.idatt2506.datastoring.R

class ColorSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_settings)


        val backButton: Button = findViewById(R.id.button)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()  // This will close the current activity
        }



        val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        // Get current saved background color
        val mainLayout: View =
            findViewById(R.id.colorLayout) // Assuming you have a root layout with this ID
        val bgColor = sharedPref.getInt("backgroundColor", resources.getColor(R.color.black, null))
        mainLayout.setBackgroundColor(bgColor)



        val spinnerColor: Spinner = findViewById(R.id.colorSpinner)

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
    }
}
