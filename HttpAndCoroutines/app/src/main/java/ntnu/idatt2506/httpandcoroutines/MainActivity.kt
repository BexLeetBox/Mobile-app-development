package ntnu.idatt2506.httpandcoroutines


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ntnu.idatt2506.httpandcoroutines.activity.GuessActivity
import ntnu.idatt2506.httpandcoroutines.utils.NetworkUtils
import ntnu.idatt2506.httpandcoroutines.utils.NetworkUtils.sendGetRequestWithParams
import org.json.JSONObject
import java.net.URLEncoder


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameEditText: EditText = findViewById(R.id.nameEditText)
        val cardEditText: EditText = findViewById(R.id.cardEditText)
        val submitInfoButton: Button = findViewById(R.id.submitInfoButton)
        val responseTextView: TextView = findViewById(R.id.responseTextViewMain)

        submitInfoButton.setOnClickListener {
            val playerName = nameEditText.text.toString()
            val cardNumber = cardEditText.text.toString()

            // Convert data into URL-encoded format
            val playerInfo = StringBuilder()
            playerInfo.append("navn=").append(URLEncoder.encode(playerName, "UTF-8"))
            playerInfo.append("&kortnummer=").append(URLEncoder.encode(cardNumber, "UTF-8"))

            // Use lifecycleScope for sending player information to the server
            lifecycleScope.launch(Dispatchers.IO) {
                val response = NetworkUtils.sendGetRequestWithParams(
                    "https://bigdata.idi.ntnu.no/mobil/tallspill.jsp",
                    playerInfo.toString()
                )
                withContext(Dispatchers.Main) {
                    responseTextView.text = response
                    if (response.contains("Oppgi et tall mellom")) {
                        val intent = Intent(this@MainActivity, GuessActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }




        /** newGameButton.setOnClickListener {
            // Handle starting a new game here
        }*/

        //val intent = Intent(this, GuessActivity::class.java)
        //startActivity(intent)
    }


}