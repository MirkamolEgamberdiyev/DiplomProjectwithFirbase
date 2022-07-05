package com.tuit.tuit.ui.teacher.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tuit.tuit.R
import com.tuit.tuit.databinding.DialogSelectSubjectBinding
import com.tuit.tuit.ui.teacher.adapter.SelectSubjectAdapter


class SelectSubjectDialog(private val listener: OnItemConfirmed): BottomSheetDialogFragment(), SelectSubjectAdapter.OnSubjectClickListener {

    private var _binding: DialogSelectSubjectBinding?=null
    private val binding get() = _binding!!
    override fun getTheme() = R.style.CustomBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogSelectSubjectBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            val stringArray = resources.getStringArray(R.array.my_string_array)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = SelectSubjectAdapter(stringArray.asList(), this@SelectSubjectDialog)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnItemConfirmed{
        fun onItemConfirmed(name: String)
    }

    override fun onItemClicked(name: String) {
        listener.onItemConfirmed(name)
        dismiss()
    }
}