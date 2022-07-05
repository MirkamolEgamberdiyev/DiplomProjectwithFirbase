package com.tuit.tuit.ui.student.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tuit.tuit.R
import com.tuit.tuit.databinding.FragmentHomeBinding
import com.tuit.tuit.databinding.FragmentSubjectsBinding
import com.tuit.tuit.repository.model.Data
import com.tuit.tuit.ui.student.adapter.FileAdapter
import com.tuit.tuit.ui.student.quiz.FragmentVariantsArgs
import com.tuit.tuit.ui.student.quiz.FragmentVariantsDirections

class SubjectsFragment : Fragment(), FileAdapter.OnClickListener {
    private var _binding: FragmentSubjectsBinding? = null
    private val binding get() = _binding!!
    lateinit var databaseReference: DatabaseReference
    private val args : SubjectsFragmentArgs by navArgs()
    private val list = ArrayList<Data>()
    var url: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSubjectsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseReference = Firebase.database.reference.child("subjects").child(args.title)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (snap in snapshot.children) {
                    val data = snap.getValue(Data::class.java)
                    list.add(data!!)
                    binding.subjectName.text = data.subjectName
                    url = data.url
                }
                binding.recycler.adapter = FileAdapter(list, this@SubjectsFragment)
            }
            override fun onCancelled(error: DatabaseError) {
            } })
        binding.home.setOnClickListener { findNavController().popBackStack() }
    }
    override fun onItemClicked() { // mana shu yerda o'tganku
        findNavController().navigate(R.id.action_subjectsFragment_to_openFileFragment, bundleOf("key" to url)) }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}