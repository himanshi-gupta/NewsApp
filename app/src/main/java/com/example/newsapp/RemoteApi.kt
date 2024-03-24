package com.example.newsapp

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class RemoteApi {

    val BASE_URL = "https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json"

    fun getNews(){
        Thread(Runnable {
            val conn = URL(BASE_URL).openConnection() as HttpURLConnection

            try{
                val reader = InputStreamReader(conn.inputStream)
                reader.use {input ->
                    val response = StringBuilder()
                    val bufferReader = BufferedReader(input)
                    bufferReader.forEachLine {
                        response.append(it.trim())
                    }
                    Log.d("Himanshi","Sucess - $response")
                }
            }
            catch (e : Exception){
                Log.d("Error","Error - ${e.localizedMessage}")
            }
        }).start()
    }
}