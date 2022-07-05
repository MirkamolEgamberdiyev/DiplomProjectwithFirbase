package com.tuit.tuit.ui.student.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.tuit.tuit.R
import com.tuit.tuit.databinding.FragmentVariantsBinding
import com.tuit.tuit.ui.student.adapter.MainPageAdapter

class FragmentVariants : Fragment(), MainPageAdapter.OnItemClicked{

    private var _binding: FragmentVariantsBinding?=null
    private val args: FragmentVariantsArgs by navArgs()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVariantsBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            title.text = args.name
            recyclerView.apply {
                adapter= MainPageAdapter(this@FragmentVariants)
                layoutManager= GridLayoutManager(context, 2)
            }
        }
    }

    override fun onItemCLicked(variantId: Int) {
        val action = FragmentVariantsDirections.actionFragmentVariantsToFragmentTest(variantId.toString(), args.subjectId)
        findNavController().navigate(action)
    }
}