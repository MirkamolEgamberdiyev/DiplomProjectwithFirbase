package com.tuit.tuit.di

import android.content.Context
import com.tuit.tuit.data.remote.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideRetrofitInstance(
        @ApplicationContext context: Context
    ): ApiInterface {
        return Retrofit.Builder()
            .baseUrl("https://testone.uz/jaloliddin1199/tuitDiplomaWork/")
            //.client(builder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

            .create(ApiInterface::class.java)
    }





}