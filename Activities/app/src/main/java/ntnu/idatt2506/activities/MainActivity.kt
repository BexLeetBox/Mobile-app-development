package ntnu.idatt2506.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import ntnu.idatt2506.activities.ui.theme.ActivitiesTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the upper limit from the incoming Intent or use a default value of 100.
        val upperLimit = intent.getIntExtra("UPPER_LIMIT", 100)

        setContent {
            ActivitiesTheme {
                // A Surface is a fundamental building block in Compose.
                // It's a container that has a background color and elevation.
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // This is the button to generate a random number.
                    RandomNumberView(upperLimit) { value ->
                        // When the button is clicked, set the result and finish the activity.
                        setRandomResultAndFinish(value)
                    }
                }
            }
        }
    }

    // This function sets the generated random number as a result for the activity.
    private fun setRandomResultAndFinish(value: Int) {
        // Create a new Intent to hold the result.
        val resultIntent = Intent()
        // Put the random number value inside the intent with the key "RANDOM_NUMBER".
        resultIntent.putExtra("RANDOM_NUMBER", value)
        // Set the result as OK and provide the intent containing our random number.
        setResult(Activity.RESULT_OK, resultIntent)
        // Finish the activity, effectively closing it and returning to the caller.
        finish()
    }
}

@Composable
fun RandomNumberView(upperLimit: Int, onNumberGenerated: (Int) -> Unit) {
    val context = LocalContext.current
    val value = (0..upperLimit).random()
    Button(onClick = {
        Toast.makeText(context, "Random Value: $value", Toast.LENGTH_SHORT).show()
        onNumberGenerated(value)
    }) {
        Text("Generate Random Number")
    }
}





