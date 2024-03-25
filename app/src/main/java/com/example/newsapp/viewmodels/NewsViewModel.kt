package com.example.newsapp.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.newsapp.RemoteApi
import com.example.newsapp.models.Articles
import com.example.newsapp.models.News
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    var news : List<Articles> by mutableStateOf(listOf())

    @OptIn(ExperimentalSerializationApi::class)
    fun getNews(){
        viewModelScope.launch {

            var data : String
            try {
                val apiService = RemoteApi()
                apiService.getNews()
                news = Json.decodeFromString<News>(apiService.data?:"").articles
            }
            catch (ex: Exception){
                throw Exception(ex.message)
            }
        }
    }
}