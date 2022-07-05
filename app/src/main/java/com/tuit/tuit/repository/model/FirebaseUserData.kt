package com.tuit.tuit.repository.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class FirebaseUserData(
    val name: String,
    val email: String,
    var position: String
)
