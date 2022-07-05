package com.tuit.tuit.repository.model

import java.io.Serializable

data class Data(
    val fileTitle: String="",
    val description: String="",
    val subjectName: String="",
    val url:String=""
):Serializable