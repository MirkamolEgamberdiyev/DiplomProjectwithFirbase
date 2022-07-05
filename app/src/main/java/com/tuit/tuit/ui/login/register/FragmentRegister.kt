package com.tuit.tuit.ui.login.register

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.tuit.tuit.databinding.FragmentSignUpBinding
import com.tuit.tuit.repository.model.FirebaseUserData
import com.tuit.tuit.ui.student.MainActivity
import com.tuit.tuit.ui.teacher.TeacherActivity
import com.tuit.tuit.utils.PostValidator
import com.tuit.tuit.utils.SharedPreferences

class FragmentRegister : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private var progressDialog: ProgressDialog? = null
    var firebaseAuth: FirebaseAuth? = null
    var firebaseDatabase: DatabaseReference? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(requireContext())
        binding.apply {
            register.setOnClickListener {
                val textName = name.text.toString()
                val textEmail = email.text.toString()
                val textPassword = password.text.toString()
                val textReEnteredPassword = reEnteredPassword.text.toString()
                if (PostValidator.isRegistrationValid(
                        requireContext(),
                        textName,
                        textEmail,
                        textPassword,
                        textReEnteredPassword,
                    )
                ) {
                    progressDialog!!.setMessage("Processing...")
                    progressDialog!!.show()
                    registerUser(textEmail, textPassword, textName)
                }
            }
            back.setOnClickListener { requireActivity().onBackPressed() }
            login.setOnClickListener {
                val action = FragmentRegisterDirections.actionFragmentSignUpToFragmentSignIn()
                findNavController().navigate(action)
            }
        }
    }

    private fun registerUser(emailM: String, passwordM: String, textName: String) {
        firebaseAuth!!.createUserWithEmailAndPassword(emailM, passwordM)
            .addOnCompleteListener { task: Task<AuthResult?> ->
                progressDialog!!.dismiss()
                if (task.isSuccessful) {
                    val mUser = firebaseAuth!!.currentUser
                    val userId = mUser!!.uid
                    firebaseDatabase =
                        FirebaseDatabase.getInstance().reference.child("UserList").child(userId)
                    firebaseDatabase!!.keepSynced(true)
                    if (binding.isTeacher.isChecked) {
                        createUserDb(emailM = emailM, name = textName, position = "Teacher")
                    } else {
                        createUserDb(emailM = emailM, name = textName, position = "Student")
                    }
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun createUserDb(emailM: String, name: String, position: String) {
        firebaseDatabase?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userDate = FirebaseUserData(name = name, email = emailM, position = position)
                firebaseDatabase?.setValue(userDate) { _, _ ->
                    SharedPreferences.setLoggedIn(requireContext(), true)
                    if (position == "Teacher") {
                        SharedPreferences.saveIsTeacher(requireContext(), true)
                        val intent = Intent(requireContext(), TeacherActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    } else {
                        SharedPreferences.saveIsTeacher(requireContext(), false)
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

