package com.example.newsapp.models

import kotlinx.serialization.Serializable

@Serializable
data class News (
    var status   : String?             = null,
    var articles : List<Articles> = listOf()
)