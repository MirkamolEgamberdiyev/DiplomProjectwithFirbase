package com.tuit.tuit.ui.student.home

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tuit.tuit.R
import com.tuit.tuit.databinding.FragmentHomeBinding
import com.tuit.tuit.repository.model.Data
import com.tuit.tuit.ui.student.adapter.FileAdapter
import com.tuit.tuit.ui.student.adapter.SubjectAdapter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    val list = ArrayList<String>()
    lateinit var databaseReference: DatabaseReference
    lateinit var adapter: SubjectAdapter
    private var progressDialog: ProgressDialog? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = ProgressDialog(requireContext())
        adapter = SubjectAdapter(subjectsList())
        binding.rvSubjects.adapter = adapter

        adapter.onClick = {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragment2ToSubjectsFragment(it))
        }
    }

    private fun subjectsList(): ArrayList<String> {
        databaseReference = Firebase.database.reference.child("subjects")
        progressDialog!!.setMessage("Loading...")
        progressDialog!!.show()
        databaseReference.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                progressDialog!!.hide()
                list.clear()
                for (snap in snapshot.children) {
                    val data = snap.key
                    list.add(data!!)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        return list
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        progressDialog!!.hide()
    }
}


