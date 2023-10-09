package ntnu.idatt2506.sockets

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var chatTextView: TextView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: Button
    private lateinit var socket: Socket
    private lateinit var input: DataInputStream
    private lateinit var output: DataOutputStream



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
                socket = Socket("localhost", 1234)
                input = DataInputStream(socket.getInputStream())
                output = DataOutputStream(socket.getOutputStream())

                Log.d("Client", "Connected to server at 10.0.0.82:1234")

                runOnUiThread {
                    sendButton.isEnabled = true
                }

                while (true) {
                    val message = input.readUTF()
                    runOnUiThread {
                        chatTextView.append("\nReceived: $message")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    // You can provide a more detailed error message based on the exception type.
                    val errorMsg = "Failed to connect to server: ${e.message}"
                    Toast.makeText(this@MainActivity, errorMsg, Toast.LENGTH_LONG).show()
                    Log.e("Socket error", e.toString())
                }
            }
        }

        //-----------------------------------------------------//

        sendButton.setOnClickListener {
            val message = messageEditText.text.toString()
            chatTextView.append("\nSent: $message")
            thread {
                output.writeUTF(message)
                Log.d("Client", "Sent message: $message")
                output.flush()

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
