package com.computerwizards.moodnotes.online

import com.computerwizards.moodnotes.online.model.RedditResponse
import com.computerwizards.moodnotes.online.model.Response
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


interface AdviceApiService {

    @GET("advice")
    fun getAdvice(): Call<Response>

}

object AdviceApi {

    val BASE_URL = "https://api.adviceslip.com/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val retrofittedBuilder: Retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val adviceApiService = retrofittedBuilder.create(AdviceApiService::class.java)


}

interface RedditApiService {

    @GET("best.json")
    fun getListing(): Deferred<RedditResponse>

}

object RedditApi {
    val BASE_URL = "https://www.reddit.com/"

    val retroffitedBuilder: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    val redditApiService = retroffitedBuilder.create(RedditApiService::class.java)

}