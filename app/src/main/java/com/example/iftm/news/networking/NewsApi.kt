package com.example.iftm.news.networking

import com.example.iftm.news.networking.Article


data class NewsApi(val totalResults: Int, val articles: List<Article>)