package ntnu.idatt2506.httpandcoroutines.utils

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

object NetworkUtils {

    suspend fun sendPostRequest(apiUrl: String, data: String): String {
        var result = ""
        var connection: HttpURLConnection? = null

        try {
            val url = URL(apiUrl)
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json; utf-8")
            connection.doOutput = true

            // Write data to the server
            val os: OutputStream = connection.outputStream
            val input: ByteArray = data.toByteArray(StandardCharsets.UTF_8)
            os.write(input, 0, input.size)

            // Get the server response
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = StringBuilder()
                var line: String? = reader.readLine()
                while (line != null) {
                    response.append(line)
                    line = reader.readLine()
                }
                result = response.toString()

                Log.i("result from server: ",result)
            } else {
                result = "Error: $responseCode"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("NetworkError", "Error sending POST request", e)
        } finally {
            connection?.disconnect()
        }

        return result
    }

}
