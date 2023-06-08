package com.idn.rumayshomobile.utils.network

import com.idn.rumayshomobile.BuildConfig
import com.idn.rumayshomobile.BuildConfig.BASE_URL
import com.idn.rumayshomobile.models.collection.Collection
import com.idn.rumayshomobile.models.post.Posts
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface WordpressApi {

    @GET("posts")
    suspend fun getPosts(
        @Query("page")
        page: Int,
        @Query("per_page")
        perPage: Int,
        @Query("_embed")
        embed: Boolean,
    ): List<Posts>

    @GET("posts")
    suspend fun getPosts(
        @Query("categories")
        category: Int,
        @Query("page")
        page: Int,
        @Query("per_page")
        perPage: Int,
        @Query("_embed")
        embed: Boolean,
    ): List<Posts>

    @GET("posts")
    suspend fun getPosts(
        @Query("page")
        page: Int,
        @Query("per_page")
        perPage: Int,
        @Query("_embed")
        embed: Boolean,
        @Query("tags")
        tag: Int
    ): List<Posts>

    @GET("categories")
    suspend fun getCategories(
        @Query("page")
        page: Int,
        @Query("per_page")
        perPage: Int,
        @Query("_fields")
        fields: String
    ): List<Collection>

    @GET("tags")
    suspend fun getTags(
        @Query("page")
        page: Int,
        @Query("per_page")
        perPage: Int,
        @Query("_fields")
        fields: String
    ): List<Collection>

    @GET("posts")
    suspend fun searchPost(
        @Query("page")
        page: Int,
        @Query("per_page")
        perPage: Int,
        @Query("_embed")
        embed: Boolean,
        @Query("search")
        keyword: String
    ): List<Posts>

    companion object {
        fun create(): WordpressApi {
            val httpLoggingInterceptor: HttpLoggingInterceptor = if (BuildConfig.DEBUG){
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }

            val client = OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor {
                    val request = it.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build()
                    return@addInterceptor it.proceed(request)
                }
                .pingInterval(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build()


            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WordpressApi::class.java)
        }
    }

}