package com.shanwu.ubiquiti_assignment.network

import com.shanwu.ubiquiti_assignment.network.model.AirQualityInfoBean
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class Retrofit {
    companion object {
        inline fun <reified T> create(baseUrl: String): T {
            return retrofit2.Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(OkHttpClient().newBuilder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(T::class.java)
        }
    }
}

interface ApiService {
    companion object {
        private const val AIR_QUALITY_URL = "https://data.epa.gov.tw/api/v2/"

        fun create() = Retrofit.create<ApiService>(AIR_QUALITY_URL)
    }

    @GET("aqx_p_432")
    fun getAirQuality(
        @Query("limit") limit:Int = 1000,
        @Query("api_key") apiKey: String = "cebebe84-e17d-4022-a28f-81097fda5896",
        @Query("sort") sort: String = "ImportDate desc",
        @Query("format") format: String = "json"
    ): Call<AirQualityInfoBean>
}