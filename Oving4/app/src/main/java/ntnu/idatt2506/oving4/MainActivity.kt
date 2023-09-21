package ntnu.idatt2506.oving4

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val greetingTextView: TextView = findViewById(R.id.greeting_text)
        greetingTextView.text = "Hello Android!"
    }
}
