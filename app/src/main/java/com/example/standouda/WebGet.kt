package com.example.standouda
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

class WebGet(
    url : String

) {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(url)
        .build()


    interface WebGetterService {
        @GET("infos.txt")
        suspend fun getInfos() : String
    }

    val retrofitService : WebGetterService by lazy {
        retrofit.create(WebGetterService::class.java)
    }

}