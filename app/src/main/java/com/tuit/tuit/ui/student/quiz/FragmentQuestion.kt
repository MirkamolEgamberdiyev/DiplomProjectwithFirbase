package com.tuit.tuit.ui.student.quiz

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tuit.tuit.databinding.FragmentTestQuestionPresenterBinding
import com.tuit.tuit.repository.model.MainModelItem
import com.tuit.tuit.utils.Constant

class FragmentQuestion : Fragment() {
    private lateinit var questionNumber: MainModelItem
    var questionIndex = -1
    private var _binding: FragmentTestQuestionPresenterBinding?=null
    private val binding get() = _binding!!
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        _binding = FragmentTestQuestionPresenterBinding.inflate(inflater, container, false)
        return binding.root }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionIndex = requireArguments().getInt("index", -1)
        questionNumber = Constant.mainModel[questionIndex]
        binding.questionNumber.text = "Savol ${questionIndex + 1}/${Constant.mainModel.size}"
        binding.savol.text = (questionNumber.question_txt)
        binding.savol.visibility = View.VISIBLE
        binding.variantA.text = questionNumber.optionA
        binding.variantA.setOnClickListener {
            Constant.chosenAnswers[Constant.currentIndex] = "A" }
        binding.variantB.text = questionNumber.optionB
        binding.variantB.setOnClickListener {
            Constant.chosenAnswers[Constant.currentIndex] = "B" }
        binding.variantC.text = questionNumber.optionC
        binding.variantC.setOnClickListener {
            Constant.chosenAnswers[Constant.currentIndex] = "C" }
        binding.variantD.text = questionNumber.optionD
        binding.variantD.setOnClickListener {
            Constant.chosenAnswers[Constant.currentIndex] = "D"
        }
    }

}

