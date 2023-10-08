package ntnu.idatt2506.sockets

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

        thread {
            socket = Socket("YOUR_SERVER_IP", 12345) // replace YOUR_SERVER_IP with your server's IP
            input = DataInputStream(socket.getInputStream())
            output = DataOutputStream(socket.getOutputStream())

            while (true) {
                val message = input.readUTF()
                runOnUiThread {
                    chatTextView.append("\nReceived: $message")
                }
            }
        }

        sendButton.setOnClickListener {
            val message = messageEditText.text.toString()
            chatTextView.append("\nSent: $message")
            thread {
                output.writeUTF(message)
            }
        }
    }
}
