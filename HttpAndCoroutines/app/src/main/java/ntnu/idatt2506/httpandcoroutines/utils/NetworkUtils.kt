package ntnu.idatt2506.httpandcoroutines.utils

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.CookieHandler
import java.net.CookieManager
import java.net.HttpURLConnection
import java.net.URL


object NetworkUtils {
    private val cookieManager = CookieManager()

    init {
        CookieHandler.setDefault(cookieManager)
    }

    suspend fun sendGetRequestWithParams(apiUrl: String, params: String): String {
        var result = ""
        var connection: HttpURLConnection? = null

        try {
            val url = URL("$apiUrl?$params")
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            // Setting up cookies
            val cookie = cookieManager.cookieStore.get(url.toURI()).joinToString(separator = "; ") {
                "${it.name}=${it.value}"
            }
            if (cookie.isNotEmpty()) {
                connection.setRequestProperty("Cookie", cookie)
            }

            // Get the server response
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = StringBuilder()
                var line: String?
                do {
                    line = reader.readLine()
                    if (line != null) response.append(line)
                } while (line != null)
                result = response.toString()
                Log.i("result from server: ", result)
            } else {
                result = "Error: $responseCode"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("NetworkError", "Error sending GET request", e)
        } finally {
            connection?.disconnect()
        }

        return result
    }

}
