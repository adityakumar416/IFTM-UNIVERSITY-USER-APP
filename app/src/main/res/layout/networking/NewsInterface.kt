package com.example.lates.networking

import com.example.iftm.news.networking.NewsApi
import com.example.iftm.news.networking.NewsInterface
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "5b6e4aa952d542e9b23db7e1e63578df"

interface NewsInterface {
    @GET("v2/top-headlines?apiKey=${com.example.iftm.news.networking.API_KEY}")
    fun getHeadlines(@Query("country") country: String, @Query("page") page: Int) : Call<NewsApi>
}

object NewsService{
    val newsInstance: NewsInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(com.example.iftm.news.networking.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance = retrofit.create(NewsInterface::class.java)
    }
}
