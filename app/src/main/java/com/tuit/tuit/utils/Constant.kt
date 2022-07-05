package com.tuit.tuit.utils

import android.os.CountDownTimer
import com.tuit.tuit.repository.model.CurrentQuestion
import com.tuit.tuit.repository.model.MainModelItem
import com.tuit.tuit.ui.student.quiz.FragmentQuestion

object Constant {
    val list = listOf<String>(
        "variant",
        "variant",
        "variant",
        "variant",
        "variant",
        "variant",
        "variant",
        "variant",
        "variant",
        "variant",
        "variant",
        "variant",
        "variant",
        "variant",
        "variant",
        "variant",
        "variant",
        "variant",
        )
    val list2 = listOf<String>(
        "variant",
        "variant",
        "variant",
        "variant",
        "variant",
        "variant",
        "variant",
        "variant",
        "variant",
        "variant"
    )



    var lastPosition=0

    var mainModel=ArrayList<MainModelItem>()
    var currentIndex=0
    var chosenAnswers=ArrayList<String>()
    var correctAnswers=ArrayList<String>()
    var countDownTimer: CountDownTimer? = null
    var currentQuestions: ArrayList<CurrentQuestion> = ArrayList()
    var fragmentQuestions: ArrayList<FragmentQuestion> = ArrayList()
}