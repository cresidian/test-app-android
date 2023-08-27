//package com.android.testapp.data.sources.local.database.converters
//
//import androidx.room.TypeConverter
//import com.android.testapp.domain.model.TemperatureDetails
//import com.google.gson.Gson
//
//class Converters {
//
//    private val gson = Gson()
//
//    @TypeConverter
//    fun fromResponse(weatherDetailsResponse: TestResponse): String {
//        return gson.toJson(weatherDetailsResponse)
//    }
//
//    @TypeConverter
//    fun toResponse(json: String): TestResponse {
//        return gson.fromJson(json, TestResponse::class.java)
//    }
//}