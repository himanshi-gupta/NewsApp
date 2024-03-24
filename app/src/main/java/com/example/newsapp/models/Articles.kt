package com.example.newsapp.models

import kotlinx.serialization.Serializable

@Serializable
data class Articles (

    var source      : Source? = Source(),
    var author      : String? = null,
    var title       : String? = null,
    var description : String? = null,
    var url         : String? = null,
    var urlToImage  : String? = null,
    var publishedAt : String? = null,
    var content     : String? = null

)