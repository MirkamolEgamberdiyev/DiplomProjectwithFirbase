package com.tuit.tuit.ui.student.quiz

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tuit.tuit.databinding.FragmentTestBinding
import com.tuit.tuit.repository.model.CurrentQuestion
import com.tuit.tuit.ui.student.adapter.FragmentQuestionPagerAdapter
import com.tuit.tuit.ui.student.dialog.DialogWarning
import com.tuit.tuit.utils.Constant.chosenAnswers
import com.tuit.tuit.utils.Constant.correctAnswers
import com.tuit.tuit.utils.Constant.currentIndex
import com.tuit.tuit.utils.Constant.currentQuestions
import com.tuit.tuit.utils.Constant.fragmentQuestions
import com.tuit.tuit.utils.Constant.mainModel
import dagger.hilt.android.AndroidEntryPoint
import vn.beautylife.pagertransformerlibrary.ZoomOutSlideTransformer

@AndroidEntryPoint
class FragmentTest : Fragment(), DialogWarning.DialogListener {
    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!
    private val args: FragmentTestArgs by navArgs()
    private lateinit var viewModel: FragmentTestViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[FragmentTestViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializePopback()
        Toast.makeText(context, args.variantNumber, Toast.LENGTH_SHORT).show()
        viewModel.requestQuestions(args.variantNumber.toInt(),args.subjectId )

        viewModel.questionList.observe(viewLifecycleOwner) {
            binding.progress.visibility = View.GONE
            if (it.isEmpty()) {
                showDialog()
                return@observe
            }
            mainModel.addAll(it)
            for (i in it.indices) {
                correctAnswers.add(it[i].correctAnswer)
                chosenAnswers.add("E")
            }
            initializeQuestions()
        }
        viewModel.freeQuestionErrorObserver.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        binding.finish.setOnClickListener {
            if (chosenAnswers.contains("E")){
                val menuFragment = DialogWarning(this@FragmentTest)
                menuFragment.isCancelable = false
                menuFragment.show(parentFragmentManager, menuFragment.tag)
            }else{
                finishGame2()
            }
        }

    }


    private fun initializeQuestions() {
        currentQuestions.clear()
        fragmentQuestions.clear()
        for (i in 0 until mainModel.size) {
            currentQuestions.add(CurrentQuestion(i))
        }
        setupQuestion()
    }

    private fun setupQuestion() {
        binding.apply {
            viewPager.addOnPageChangeListener(viewPagerF)
            genFragmentList()
            val questionsFragmentAdapter = FragmentQuestionPagerAdapter(
                childFragmentManager,
                requireContext(), fragmentQuestions
            )
            viewPager.adapter = questionsFragmentAdapter
            viewPager.setPageTransformer(true, ZoomOutSlideTransformer())
            pageIndicator.attachTo(viewPager)
            pageIndicator.swipePrevious()
            pageIndicator.swipeNext()
        }
    }

    private fun genFragmentList() {
        for (i in 0 until mainModel.size) {
            val bundle = Bundle()
            bundle.putInt("index", i)
            val fragment = FragmentQuestion()
            fragment.arguments = bundle
            fragmentQuestions.add(fragment)
        }
    }



    private fun finishGame2() {
        val action=FragmentTestDirections.actionFragmentTestToExamFragment()
        findNavController().navigate(action)

    }

    private fun initializePopback(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                mainModel.clear()
                currentIndex=0
                chosenAnswers.clear()
                correctAnswers.clear()
                currentQuestions.clear()
                findNavController().popBackStack()

            }
        })


    }

    private val viewPagerF=object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }
        override fun onPageSelected(position: Int) {
            currentIndex= position
        }
        override fun onPageScrollStateChanged(state: Int) {}
    }
    private fun showDialog(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Kechirasiz")
            .setCancelable(false)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setMessage("Bizda hozircha  savollar yo'q.")
            .setPositiveButton("ok") { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
                findNavController().popBackStack()
            }
            .show()
    }

    override fun finishTest() {
        finishGame2()
    }


}