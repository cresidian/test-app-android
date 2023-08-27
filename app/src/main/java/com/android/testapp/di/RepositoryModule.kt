//package com.android.testapp.di
//
//import com.android.testapp.data.repository.*
//import com.android.testapp.domain.repository.*
//import dagger.Module
//import dagger.hilt.InstallIn
//import dagger.hilt.android.components.ViewModelComponent
//
//@Module
//@InstallIn(ViewModelComponent::class)
//object RepositoryModule {
//
//    @Provides
//    @ViewModelScoped
//    fun provideRepository(
//        endpoint: OpenWeatherApiEndpoint,
//        testAppDatabase: TestAppDatabase
//    ): WeatherRepository {
//        return TestAppRepositoryImpl(endpoint, testAppDatabase)
//    }
//
//}