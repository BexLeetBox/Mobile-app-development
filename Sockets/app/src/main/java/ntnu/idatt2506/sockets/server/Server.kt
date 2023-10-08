package ntnu.idatt2506.sockets.server

import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

class Server(private val textView: TextView, private val port: Int = 12345) {


    private var clients = mutableListOf<Socket>()

    private var ui: String? = ""
        set(str) {
            MainScope().launch { textView.text = str }
            field = str
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
                ui = e.message
            }
        }
    }

    private fun handleClient(clientSocket: Socket) {
        val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
        val message = reader.readLine()
        ui = "Client says: $message"
        broadcast(message)
    }

    private fun broadcast(message: String) {
        for (client in clients) {
            if (!client.isClosed) {
                val writer = PrintWriter(client.getOutputStream(), true)
                writer.println(message)
            }
        }
        ui = "Broadcasted: $message"
    }
}
