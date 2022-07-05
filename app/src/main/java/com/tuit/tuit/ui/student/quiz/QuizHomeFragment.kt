package com.tuit.tuit.ui.student.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tuit.tuit.databinding.FragmentHomeQuizBinding

class QuizHomeFragment : Fragment() {
    private var _binding: FragmentHomeQuizBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeQuizBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            optics.setOnClickListener{
                goToVariants("Optika", 0)
            }
            multimedia.setOnClickListener {
                goToVariants("Multimedia", 1)

            }
            computerNetworks.setOnClickListener {
                goToVariants("Kompyuter Tarmoqlari", 2)
            }
        }
    }
    private fun goToVariants(name: String, subjectId:Int){
        val action = QuizHomeFragmentDirections.actionNavigationDashboardToFragmentVariants(name, subjectId)
        findNavController().navigate(action)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}