package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.models.Articles
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.viewmodels.NewsViewModel

class MainActivity : ComponentActivity() {
    val newsVM by viewModels<NewsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    newsVM.getNews()
                    HomeScreen(newsVM.news)
                }
            }
        }
    }
}

@Composable
fun HomeScreen(newsList : List<Articles>, modifier: Modifier = Modifier) {
    LazyColumn {
        items(items = newsList){item->
            Card {
                Column {
                    Text(text = item.title?:"Title")
                    Text(text = item.description?:"Desciption")
                    Text(text = item.author?:"Author")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewsAppTheme {
        HomeScreen(listOf())
    }
}