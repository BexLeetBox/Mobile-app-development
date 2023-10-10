package ntnu.idatt2506.sockets

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var chatTextView: TextView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: Button
    private lateinit var socket: Socket
    private lateinit var input: DataInputStream
    private lateinit var output: PrintWriter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chatTextView = findViewById(R.id.tv_chat)
        messageEditText = findViewById(R.id.et_message)
        sendButton = findViewById(R.id.btn_send)
        sendButton.isEnabled = true  //to circumvent race condition bugs

    //-------------------------- thread stuff ------------------------------//

        thread {
            try {
                socket = Socket("10.0.0.83", 1234)




                Log.d("Client", "Connected to server")

                if (socket.isConnected) {
                    runOnUiThread {
                        chatTextView.append("\n Successfully connected to server")
                        sendButton.isEnabled = true
                    }
                } else {
                    chatTextView.append("\n IS NOT Connected to server")
                }




                while (true) {
                    val input = BufferedReader(InputStreamReader(socket.getInputStream())) // This will give an error because types are different
                    val message = input.readLine()
                    Log.d("Reading input stream", message)
                    runOnUiThread {
                        chatTextView.append("\nReceived: $message")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()

                runOnUiThread {
                    chatTextView.append("\nFailed to connect to server")
                    // You can provide a more detailed error message based on the exception type.
                    val errorMsg = "Failed to connect to server: ${e.message}"
                    Toast.makeText(this@MainActivity, errorMsg, Toast.LENGTH_LONG).show()
                    Log.e("Socket error", errorMsg)
                }
            }
        }

        //-----------------------------------------------------//

        sendButton.setOnClickListener {
            val message = messageEditText.text.toString()

            thread {
                output = PrintWriter(socket.getOutputStream(), true)
                output.println(message)
                Log.d("Client", "Sent message: $message")
                runOnUiThread{
                    chatTextView.append("\nSent: $message")
                }
            }
        }
    }

    //close the socket and streams to free up resources
    override fun onDestroy() {
        super.onDestroy()
        try {
            input.close()
            output.close()
            socket.close()
        } catch (e: Exception) {
            // Handle exception if needed
        }
    }

}
