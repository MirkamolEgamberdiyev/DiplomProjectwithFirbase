package com.tuit.tuit.repository.model

data class MainModelItem(
    val correctAnswer: String,
    val id: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val question_txt: String,
    val variantNum: String
)