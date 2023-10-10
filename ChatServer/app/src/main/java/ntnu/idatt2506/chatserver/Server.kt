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
import java.io.EOFException
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

                ui = "Server started on port: $port"
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
                Log.e(TAG, "Server initialization error", e) // Use exception overload for detailed stack trace
                ui = "Error initializing server: ${e.localizedMessage}"
            }
        }
    }

    private fun handleClient(clientSocket: Socket) {
        val clientAddress = clientSocket.remoteSocketAddress.toString()
        val dataInputStream = DataInputStream(clientSocket.getInputStream())
        try {
            while (true) {  // Continuously read messages
                val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
                val message = reader.readLine()
                if (message == null) {
                    ui = "Client disconnected"
                    clients.remove(clientSocket)
                    clientSocket.close()
                    break
                }
                ui = "Received from $clientAddress: $message"
                Log.d(TAG, "Received from $clientAddress: $message")
                broadcast(message, clientSocket)
            }
        } catch (e: EOFException) {
            // Client disconnected or some other read error occurred.
            ui = "Exception when reading"
            Log.w(TAG, "Connection with client $clientAddress ended: ${e.localizedMessage}") // Using warning level
        } catch (e: IOException) {
            // Handle other IO exceptions
            ui = "Exception when reading IO"
            ui = e.message
            Log.e(TAG, "Error reading from client $clientAddress", e) // Detailed stack trace for other IO errors
        }
    }


    private fun broadcast(message: String, sender: Socket? = null) {
        for (client in clients) {
            val clientAddress = client.remoteSocketAddress.toString()
            //don't need to send the sender the message it sent back to itself
            if (client == sender) continue

            ui = if (!client.isClosed) {
                try {
                    val writer = PrintWriter(client.getOutputStream(), true)
                    writer.println(message)
                    Log.d(TAG, "Message sent to $clientAddress: $message")
                    "Sent to $clientAddress: $message"
                }catch (e: IOException) {
                    ui = "Exception when writing IO"
                    Log.e(TAG, "Error broadcasting to client $clientAddress", e)
                    "Error broadcasting to client "
                }
            } else {
                ui = "skipped closed client"
                Log.w(TAG, "Skipped closed client $clientAddress during broadcast")
                "Skipped closed client"
            }

        }
        Log.i(TAG, "Broadcast completed for message: $message")
    }

    fun stop() {
        Log.i(TAG, "Stopping server and closing connections...")
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
