package com.example.newsapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.models.Articles
import com.example.newsapp.viewmodels.NewsViewModel
import coil.compose.AsyncImage
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.example.newsapp.ui.theme.NewsAppTheme

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
                    val image = intent.getStringExtra("image")
                    val title = intent.getStringExtra("title")
                    val descriptn = intent.getStringExtra("desc")
                    val url = intent.getStringExtra("url")
                    if(image!=null || title!=null || descriptn!=null || url!=null){
                        LandingPage(newsVM.news, applicationContext,{ getToken() }) { newsVM.sortList(it) }
                        startActivity(Intent(applicationContext,NewsDetail::class.java)
                            .putExtra("image",image)
                            .putExtra("title",title)
                            .putExtra("desc",descriptn)
                            .putExtra("url",url))
                    }
                    else
                        LandingPage(newsVM.news, applicationContext,{ getToken() }) { newsVM.sortList(it) }

                }
            }
        }
    }

    private fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d("Himanshi", "Fetching FCM registration token failed ${task.exception}")
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

        })
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingPage(newsList: MutableList<Articles>, context : Context, getToken : () -> Unit, sortList : (Int) -> Unit) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "NewsApp",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.ExtraBold,
//                            modifier = Modifier.width(200.dp)
                        )
                        DropDownMenu(){
                            sortList(it)
                        }
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.DarkGray,
                    titleContentColor = Color.White
                ),
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(0.dp, 0.dp, 15.dp, 15.dp)),
            )
        },
        content = { ListView(newsList,context) },
        bottomBar = {}
    )
//    getToken();
}

@Composable
fun ListView(newsList: MutableList<Articles>,context: Context, modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current
    val localContext = LocalContext.current
    LazyColumn(modifier = Modifier.padding(top = 70.dp)) {
        items(items = newsList) { item ->
            Card(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp)
                    .padding(top = 10.dp)
                    .shadow(5.dp, shape = RoundedCornerShape(15.dp))
                    .clickable {
                        localContext.startActivity(
                            Intent(context, NewsDetail::class.java)
                                .putExtra("image", item.urlToImage)
                                .putExtra("title", item.title)
                                .putExtra("desc", item.description)
                                .putExtra("url", item.url)
                        )
                    }
            ) {
                Column(
                    modifier = Modifier
                ) {
                    AsyncImage(
                        model = "${item.urlToImage}", contentDescription = "", modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 7.dp, end = 7.dp)
                            .padding(top = 7.dp)
                            .clip(shape = RoundedCornerShape(15.dp)),
                        contentScale = ContentScale.FillBounds
                    )
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(text = item.title ?: "Title",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.clickable(true) {
                                uriHandler.openUri(
                                    item.url ?: ""
                                )
                            })
                        Text(
                            text = item.description ?: "Desciption",
                            fontSize = 12.sp,
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .wrapContentWidth(),
                            lineHeight = 15.sp,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = item.publishedAt ?: "Author",
                            fontSize = 9.sp,
                            modifier = Modifier
                                .padding(top = 15.dp),
                            lineHeight = 12.sp,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(sortList: (Int) -> Unit) {

    val menu = listOf("Old to New", "New to Old")
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var selectedChoice by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier
        .then(
            if (selectedChoice == "") {
                Modifier.width(40.dp)
            } else {
                Modifier.width(160.dp)
            } as Modifier
        )
        .padding(end = 20.dp)
        .height(50.dp)
        ) {
        ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = {
            isExpanded = !isExpanded
        }) {
            TextField(
                modifier = Modifier.menuAnchor(),
                value = selectedChoice,
                onValueChange = {},
                readOnly = true,
                textStyle = TextStyle(fontSize = 15.sp, lineHeight = 15.sp),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)}
            )
            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false },
                modifier = Modifier
                    .width(130.dp)
                    .requiredHeightIn(max = 100.dp)) {
                menu.forEachIndexed { index, data ->
                    DropdownMenuItem(text = { Text(text = data)},
                        onClick = { selectedChoice = menu[index]
                        isExpanded = false
                        sortList(index) },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                }
            }
        }
    }
}
