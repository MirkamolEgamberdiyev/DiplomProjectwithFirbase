package com.tuit.tuit.repository

import com.tuit.tuit.data.remote.ApiInterface
import javax.inject.Inject

class Repository @Inject constructor(private val apiInterface: ApiInterface) {

    suspend fun getQuestionTest(variant:Int, subjectId:Int) =
        apiInterface.getAllOtics(variant, subjectId)

}