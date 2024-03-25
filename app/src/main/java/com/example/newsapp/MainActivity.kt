package com.example.newsapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.models.Articles
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.viewmodels.NewsViewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
                    LandingPage(newsVM.news)
                }
            }
        }
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingPage(newsList: List<Articles>){
    val expandedState = remember { mutableStateOf(false) }
    val dropdownItems = listOf("Old to New", "New to Old")

    Scaffold(
        topBar = { TopAppBar(
            title = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "NewsApp",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.width(330.dp)
                    )
                    Button(onClick = { /*TODO*/ },) {
                        Text("Sort")
                    }
//                    Icon(painter = painterResource(R.drawable.baseline_bookmarks_24),
//                        contentDescription = "",
//                        modifier = Modifier
//                            .width(20.dp)
//                            .clickable { navController.navigate("Watch List") })
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color.DarkGray,
                titleContentColor = Color.White
            ),
            modifier = Modifier
                .clip(shape = RoundedCornerShape(0.dp, 0.dp, 15.dp, 15.dp)),
        )},
        content = { ListView(newsList)},
        bottomBar = {}
    )
}
@Composable
fun ListView(newsList : List<Articles>, modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current

    LazyColumn (modifier = Modifier.padding(top = 70.dp)){
        items(items = newsList){item->
            Card(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .padding(top = 10.dp)
//                .height(400.dp)
                .shadow(5.dp, shape = RoundedCornerShape(15.dp))
            ) {
                Column(modifier = Modifier
//                    .fillMaxSize()
                ) {
                    AsyncImage(model = "${item.urlToImage}", contentDescription = "", modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 7.dp, end = 7.dp)
                        .padding(top = 7.dp)
                        .clip(shape = RoundedCornerShape(15.dp)),
                        contentScale = ContentScale.FillBounds)
                    Column(modifier = Modifier.padding(10.dp)){
                        Text(text = item.title?:"Title",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.clickable(true){ uriHandler.openUri(item.url?:"")})
//
                        Text(text = item.description?:"Desciption",
                            fontSize = 12.sp,
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .wrapContentWidth(),
                            lineHeight = 15.sp,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis)
                        Text(text = item.author?:"Author",
                            fontSize = 9.sp,
                            modifier = Modifier
                                .padding(top = 15.dp),
                            lineHeight = 12.sp,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewsAppTheme {
//        HomeScreen(listOf())
    }
}