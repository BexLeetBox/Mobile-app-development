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
import ntnu.idatt2506.httpandcoroutines.utils.NetworkUtils
import ntnu.idatt2506.httpandcoroutines.utils.NetworkUtils.sendGetRequestWithParams
import java.net.URLEncoder

class GuessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guess)  // Assuming you have a layout for this activity

        val guessEditText: EditText = findViewById(R.id.guessEditText)
        val submitGuessButton: Button = findViewById(R.id.submitGuessButton)
        val responseTextView: TextView = findViewById(R.id.responseTextView)

        submitGuessButton.setOnClickListener {
            val guess = guessEditText.text.toString()

            // Convert data into URL-encoded format
            val guessInfo = StringBuilder()
            guessInfo.append("tall=").append(URLEncoder.encode(guess, "UTF-8"))

            // Use lifecycleScope for sending the guess to the server
            lifecycleScope.launch(Dispatchers.IO) {
                val response = NetworkUtils.sendGetRequestWithParams(
                    "https://bigdata.idi.ntnu.no/mobil/tallspill.jsp",
                    guessInfo.toString()
                )
                withContext(Dispatchers.Main) {
                    responseTextView.text = response
                    // Handle the server's response for the guess here
                }
            }
        }
    }
}
