package com.example.newsapp

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class RemoteApi {

    val BASE_URL = "https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json"
    var data : String? = null

    // Define a function to perform the HTTP request
    suspend fun getNews(){
        withContext(Dispatchers.IO) {
            val conn = URL(BASE_URL).openConnection() as HttpURLConnection
            val response = StringBuilder()

            try {
                val reader = InputStreamReader(conn.inputStream)
                reader.use { input ->
                    val bufferReader = BufferedReader(input)
                    bufferReader.forEachLine {
                        response.append(it.trim())
                    }
                }
            } catch (e: Exception) {
                Log.d("Error", "Error - ${e.localizedMessage}")
            }
            data = response.toString()
        }
    }
}