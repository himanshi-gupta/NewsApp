package com.example.newsapp.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.RemoteApi
import com.example.newsapp.models.Articles
import com.example.newsapp.models.News
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime

@SuppressLint("MutableCollectionMutableState")
class NewsViewModel : ViewModel() {
    var news : MutableList<Articles> by mutableStateOf(mutableListOf())

    @SuppressLint("SimpleDateFormat")
    fun sortList(index :Int){
        val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
        if(index==0) {
            news.sortedBy { it -> it.publishedAt?.let { it1 -> parser.parse(it1)?.let { formatter.format(it) } } }
        }
        else {
            news.sortedByDescending { it -> it.publishedAt?.let { it1 -> parser.parse(it1)?.let { formatter.format(it) } } }
        }
    }
    @OptIn(ExperimentalSerializationApi::class)
    fun getNews(){
        viewModelScope.launch {

            var data : String
            try {
                val apiService = RemoteApi()
                apiService.getNews()
                news = Json.decodeFromString<News>(apiService.data?:"").articles.toMutableList()
                if(news.isNotEmpty())
                    saveData()
            }
            catch (ex: Exception){
                throw Exception(ex.message)
            }
        }
    }

    private fun saveData(){
        val firestore : FirebaseFirestore = FirebaseFirestore.getInstance()
        news.forEach {
            firestore.collection("NewsArticles").document().set(mapOf("article" to it))
        }
    }
}