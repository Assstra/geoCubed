package com.araimbault.geocubed.network

import com.araimbault.geocubed.model.LocationResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


private const val BASE_URL = "https://api.openrouteservice.org"
private const val API_KEY = "5b3ce3597851110001cf62489176bfa4bd2e483893887b966900d1ae" // TODO : change that

val locationInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor();
var locationClient: OkHttpClient = OkHttpClient.Builder().addInterceptor(locationInterceptor).build()

/**
 * Retrofit object using a kotlinx.serialization converter
 */

private val retrofit = Retrofit.Builder()
    .client(locationClient)
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

/**
 * Retrofit service object for creating api calls
 */
interface LocationSearchApiService {
    @GET("geocode/search")
    suspend fun getLocations(
        @Query("api_key") apiKey: String,
        @Query("text") searchText: String,
        @Query("boundary.country") country: String
    ): LocationResponse
}

/**
 * A public Api function that exposes the Retrofit service
 */
suspend fun searchLocations(searchInput: String, country: String): LocationResponse {
    val encodedSearchInput = URLEncoder.encode(searchInput, StandardCharsets.UTF_8.toString())
    val service = retrofit.create(LocationSearchApiService::class.java)
    println(encodedSearchInput)
    return service.getLocations(API_KEY, encodedSearchInput, country)
}
