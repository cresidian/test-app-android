//package com.android.testapp.di
//
//import android.content.Context
//import com.android.testapp.core.network.ApiClient
//import com.android.testapp.core.network.serlializer.DateSerializer
//import com.android.testapp.data.sources.remote.OpenWeatherApiEndpoint
//import com.google.gson.Gson
//import com.google.gson.GsonBuilder
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import retrofit2.Retrofit
//import java.util.*
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object NetworkModule {
//
//    @Singleton
//    @Provides
//    fun provideApiClient(
//        @ApplicationContext appContext: Context,
//        gson: Gson,
//    ) = ApiClient(appContext, gson).getApiClient()
//
//
//    @Singleton
//    @Provides
//    fun provideGson(): Gson =
//        GsonBuilder()
//            .registerTypeAdapter(Date::class.java, DateSerializer())
//            .create()
//}