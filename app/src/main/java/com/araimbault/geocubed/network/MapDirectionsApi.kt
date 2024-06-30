package com.araimbault.geocubed.network

import com.araimbault.geocubed.model.DirectionResponse
import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

data class DirectionRequest(
    val coordinates: List<List<Double>>,
    val language: String = "en"
)


private const val BASE_URL = "https://api.openrouteservice.org"
private const val API_KEY = "5b3ce3597851110001cf62489176bfa4bd2e483893887b966900d1ae"

val directionInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor();
var directionsClient: OkHttpClient = OkHttpClient.Builder().addInterceptor(directionInterceptor).build()

/**
 * Retrofit object using a kotlinx.serialization converter
 */

private val retrofit = Retrofit.Builder()
    .client(directionsClient)
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

/**
 * Retrofit service object for creating api calls
 */
interface DirectionApiService {
    @POST
    suspend fun getDirection(
        @Url url: String,
        @Header("Authorization") apiKey: String,
        @Body requestBody: DirectionRequest
    ): DirectionResponse
}

suspend fun searchDirections (startPos: LatLng, endPos: LatLng?, profile: String, language: String): DirectionResponse {
    val startLatitude = startPos.latitude
    val startLongitude = startPos.longitude
    val endLatitude = endPos?.latitude ?: 0.0
    val endLongitude = endPos?.longitude ?: 0.0

    val coordinates = listOf(
        listOf(startLongitude, startLatitude),
        listOf(endLongitude, endLatitude)
    )

    val requestBody = DirectionRequest(coordinates, language)

    val apiUrl = "v2/directions/$profile/geojson"

    println("language = $language")
    val service = retrofit.create(DirectionApiService::class.java)
    return service.getDirection(apiUrl, API_KEY, requestBody)
}