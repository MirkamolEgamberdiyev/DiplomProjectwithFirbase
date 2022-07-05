package com.tuit.tuit.ui.student.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import com.tuit.tuit.databinding.FragmentProfileBinding
import com.tuit.tuit.ui.login.LoginActivity
import com.tuit.tuit.utils.SharedPreferences

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var imagesRef: StorageReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val notificationsViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val storage = Firebase.storage("gs://daily-shopping-list-7bb71.appspot.com")
        val storageRef = storage.reference
        val currentuser = FirebaseAuth.getInstance().currentUser!!.uid
        imagesRef =
            storageRef.child("images").child(currentuser)

        if (SharedPreferences.getImageUrl(requireContext())!=""){
            Picasso.get().load(SharedPreferences.getImageUrl(requireContext())).into(binding.ivImage)
        }

        notificationsViewModel.text.observe(viewLifecycleOwner) {

        }

        binding.ivProfile.setOnClickListener {
            openGalleryForImage()
        }


        binding.logoutTv.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            SharedPreferences.setLoggedIn(requireContext(), false)
            requireActivity().finish()
        }
        return root
    }






    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 111) {
            val uploadTask = imagesRef.putFile(data?.data!!)
            uploadTask(uploadTask)
        }
    }


    private fun uploadTask(uploadTask: UploadTask) {
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
                    throw it
                }
            }
            imagesRef.downloadUrl

        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                Picasso.get().load(downloadUri).into(binding.ivImage)
                SharedPreferences.saveImageUrl(requireContext(), task.result.toString())
            } else {
                Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
            }

        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}