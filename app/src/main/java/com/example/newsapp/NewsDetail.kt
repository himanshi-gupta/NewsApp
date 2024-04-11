package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newsapp.ui.theme.NewsAppTheme

class NewsDetail : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val image = intent.getStringExtra("image")
                    val title = intent.getStringExtra("title")
                    val descriptn = intent.getStringExtra("desc")
                    val url = intent.getStringExtra("url")
                    DetailedView(image, title, descriptn,url)
                }
            }
        }
    }
}

@Composable
fun DetailedView( image : String?, title : String?, descriptn : String?, url : String?){
    val uriHandler = LocalUriHandler.current
    Column(modifier = Modifier
        .padding(10.dp)
    ){

        Box(modifier = Modifier.height(200.dp)){
            AsyncImage(
                model = image, contentDescription = "Title", modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 7.dp, end = 7.dp)
                    .padding(top = 7.dp)
                    .clip(shape = RoundedCornerShape(15.dp)),
                contentScale = ContentScale.FillBounds
            )
        }

        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = title ?: "Title",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Color.Blue,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable(true) {
                    uriHandler.openUri(
                        url ?: ""
                    )
                })
            Text(
                text = descriptn ?: "Desciption",
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .wrapContentWidth(),
                lineHeight = 15.sp,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

