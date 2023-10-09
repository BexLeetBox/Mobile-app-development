package ntnu.idatt2506.chatserver

import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

private const val TAG = "ChatServerLogs"
class Server(private val textView: TextView, private val port: Int = 1234) {



    private var clients = mutableListOf<Socket>()
    private var serverSocket: ServerSocket? = null


    private var ui: String? = ""
        set(str) {
            MainScope().launch {
                textView.append("$str\n")
            }
            Log.d(TAG, str ?: "null") // Log every ui update
            field = "$field$str\n"
        }


    fun start() {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                ui = "Starting Server ..."
                withContext(Dispatchers.IO) {
                    ServerSocket(port).use { serverSocket ->
                        ui = "ServerSocket created, waiting for clients..."
                        while (true) {
                            val clientSocket = serverSocket.accept()
                            clients.add(clientSocket)
                            ui = "Client connected: $clientSocket"

                            // Handle client communication in a separate coroutine
                            CoroutineScope(Dispatchers.IO).launch {
                                // Listen to messages from this client and broadcast
                                handleClient(clientSocket)
                            }
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e(TAG, "Server error: ${e.message}") // Log errors
                ui = e.message
            }
        }
    }

    private fun handleClient(clientSocket: Socket) {
        val dataInputStream = DataInputStream(clientSocket.getInputStream())
        val message = dataInputStream.readUTF()
        ui = "Client says: $message"
        Log.d(TAG, "Client says: $message") // Log errors
        broadcast(message)
    }

    private fun broadcast(message: String) {
        for (client in clients) {
            if (!client.isClosed) {
                val writer = PrintWriter(client.getOutputStream(), true)
                writer.println(message)
                ui = "Sent to ${client.remoteSocketAddress}: $message"
            }
        }
        Log.d(TAG, "Broadcasting: $message")
        ui = "Broadcasted: $message"
    }
    fun stop() {
        try {
            // Close all client sockets
            for (client in clients) {

                try {
                    client.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            clients.clear()

            // Close the ServerSocket
            serverSocket?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
