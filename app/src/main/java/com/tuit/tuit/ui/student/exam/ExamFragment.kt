package com.tuit.tuit.ui.student.exam

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tuit.tuit.databinding.FragmentExamBinding
import com.tuit.tuit.utils.Constant

class ExamFragment : Fragment() {
    private var _binding: FragmentExamBinding? = null
    private val binding get() = _binding!!
    private var correctAnswers : Int=0
    private var correctness1:Float=0F
    private var incorrectAnswer1:Int=0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        for (i in Constant.chosenAnswers.indices){
            if (Constant.chosenAnswers[i] == Constant.correctAnswers[i]){
                correctAnswers++
            }else{
                incorrectAnswer1++
            }
        }
        binding.numberOfQuestions.text=Constant.chosenAnswers.size.toString()


        correctness1= if (correctAnswers==0) 0f else (correctAnswers/Constant.chosenAnswers.size).toFloat()


        binding.apply {
            correctness.text= "$correctness1%"
            correctAns.text= correctAnswers.toString()
            correctAnswer.text=correctAnswers.toString()
            incorrectAnswer.text=incorrectAnswer1.toString()

            share.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, "Hey Check out this Great app:")
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "Share To:"))
            }

            check.setOnClickListener {
                correctAnswers=0
                incorrectAnswer1=0
                correctness1=0F
            }

            home.setOnClickListener {
                handlePopback()
            }

        }

        handlePopback()

    }

    private fun handlePopback(){
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Constant.mainModel.clear()
                    Constant.currentIndex =0
                    Constant.chosenAnswers.clear()
                    Constant.correctAnswers.clear()
                    Constant.currentQuestions.clear()
                    val action = ExamFragmentDirections.actionExamFragmentToNavigationDashboard()
                    findNavController().navigate(action)
                }
            })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}