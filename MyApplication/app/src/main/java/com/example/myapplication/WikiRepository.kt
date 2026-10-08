package com.example.myapplication

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException


data class WikiResponse(val query: WikiQuery)
data class WikiQuery(val searchinfo: SearchInfo)
data class SearchInfo(val totalhits: Int)
class WikiRepository() {
    private val call = Api.service
    suspend fun getUser(title: String) = call.getTitle(title)

    suspend fun hitCountCheck(title: String): Int =
        try {
            getUser(title).query.searchinfo.totalhits
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e("Wiki", "request failed", e)
            0
        }
}


object Api {
    const val URL =
        "https://en.wikipedia.org/"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .header("User-Agent", "MyApplication/1.0 (your.email@example.com)")
                    .build()
            )
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    interface Service {
        @GET("w/api.php?action=query&format=json&list=search")
        suspend fun getTitle(@Query("srsearch") action: String): WikiResponse
    }

    val service: Service by lazy {
        retrofit.create(Service::class.java)
    }

}




