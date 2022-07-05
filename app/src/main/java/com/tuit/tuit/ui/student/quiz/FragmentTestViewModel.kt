package com.tuit.tuit.ui.student.quiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuit.tuit.repository.Repository
import com.tuit.tuit.repository.model.FirebaseUserData
import com.tuit.tuit.repository.model.MainModelItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FragmentTestViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    val questionList = MutableLiveData<List<MainModelItem>>()
    val freeQuestionErrorObserver = MutableLiveData<String>()

    fun requestQuestions(variant: Int, subjectId:Int) = viewModelScope.launch {
        try {
            repository.getQuestionTest(variant, subjectId).let {
                if (it.isSuccessful) {
                    questionList.postValue(it.body())
                } else {
                    freeQuestionErrorObserver.postValue(it.message())
                }
            }

        } catch (e: Exception) {
            freeQuestionErrorObserver.postValue(e.message)
        }
    }

}