package com.tuit.tuit.data.remote

import com.tuit.tuit.repository.model.MainModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {
    @GET("getAllOptics.php")
    suspend fun getAllOtics(
        @Query("variant") variant: Int,
        @Query("subjectId")subjectId:Int
    ): Response<MainModel>
}