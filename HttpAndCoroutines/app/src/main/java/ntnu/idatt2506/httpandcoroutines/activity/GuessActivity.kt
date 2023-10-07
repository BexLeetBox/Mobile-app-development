package ntnu.idatt2506.httpandcoroutines.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ntnu.idatt2506.httpandcoroutines.R
import ntnu.idatt2506.httpandcoroutines.utils.NetworkUtils.sendPostRequest

class GuessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guess)

        val guessEditText: EditText = findViewById(R.id.guessEditText)
        val submitGuessButton: Button = findViewById(R.id.submitGuessButton)
        val responseTextView: TextView = findViewById(R.id.responseTextView)

        //... Rest of the code that deals with the guessing game.

        submitGuessButton.setOnClickListener {
            val guessedNumber = guessEditText.text.toString()

            // Use lifecycleScope for sending the guessed number to the server
            lifecycleScope.launch(Dispatchers.IO) {
                val response = sendPostRequest(
                    "https://bigdata.idi.ntnu.no/mobil/tallspill.jsp",
                    "{\"tall\":\"$guessedNumber\"}"
                )
                withContext(Dispatchers.Main) {
                    responseTextView.text = response
                }
            }
        }
    }


}
