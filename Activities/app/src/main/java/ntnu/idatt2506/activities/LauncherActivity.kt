package ntnu.idatt2506.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class LauncherActivity : AppCompatActivity() {
    private lateinit var randomNumberText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        randomNumberText = findViewById(R.id.randomNumberText)

        // Using registerForActivityResult to listen for results from MainActivity
        val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val randomValue = result.data?.getIntExtra("RANDOM_NUMBER", -1)
                // Update the TextView with the random number
                randomNumberText.text = "Random Number: $randomValue"
            }
        }

        // For simplicity, we'll start MainActivity immediately upon launching LauncherActivity.
        // In a real scenario, this might be triggered by a button click or some other action.
        val upperLimit = 100  // This can be any value you choose
        val intent = Intent("ntnu.idatt2506.RANDOM_NUMBER_GENERATOR")
        intent.putExtra("UPPER_LIMIT", upperLimit)
        requestLauncher.launch(intent)
    }
}
