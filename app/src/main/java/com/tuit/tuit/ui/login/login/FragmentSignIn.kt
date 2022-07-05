package com.tuit.tuit.ui.login.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.tuit.tuit.R
import com.tuit.tuit.databinding.FragmentSignInBinding
import com.tuit.tuit.ui.login.AuthManager
import com.tuit.tuit.ui.login.handler.AuthHandler
import com.tuit.tuit.ui.student.MainActivity
import com.tuit.tuit.utils.SharedPreferences
import com.tuit.tuit.utils.toast
import java.lang.Exception

class FragmentSignIn : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val et_email = email
            val et_password = password
            back.setOnClickListener { findNavController().popBackStack() }
            signIn.setOnClickListener {
                val email = et_email.text.toString().trim()
                val password = et_password.text.toString().trim()
                if (email.isNotEmpty() && password.isNotEmpty())
                    firebaseSignIn(email, password)
            }
            register.setOnClickListener{
                findNavController().navigate(R.id.action_fragmentSignIn_to_fragmentSignUp)
                findNavController().popBackStack()
            }
        }
    }

    private fun firebaseSignIn(email: String, password: String) {
        AuthManager.signIn(email, password, object : AuthHandler {
            override fun onSuccess(uid: String) {
                toast(requireContext(), getString(R.string.str_signin_success))
                SharedPreferences.setLoggedIn(requireContext(), true)
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            }

            override fun onError(exception: Exception?) {
                toast(requireContext(), getString(R.string.str_signin_failed))
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}