package ntnu.idatt2506.chatserver

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private lateinit var server: Server
    private lateinit var statusTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)  // Assuming you have a layout named activity_main

        // Assuming you have a TextView in your layout with the id "statusTextView"
        statusTextView = findViewById(R.id.statusTextView)
        server = Server(statusTextView)

        // Start the server
        server.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        // Stop the server when the activity is destroyed
        server.stop()
    }
}
