package com.tuit.tuit.ui.teacher.fragment

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.tuit.tuit.R
import com.tuit.tuit.databinding.ActivityTeacherBinding
import com.tuit.tuit.databinding.FragmentTeacherHomeBinding
import com.tuit.tuit.repository.model.UploadFile
import com.tuit.tuit.ui.teacher.dialog.SelectSubjectDialog
import com.tuit.tuit.utils.PostValidator
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class FragmentTHome: Fragment(), SelectSubjectDialog.OnItemConfirmed {

    private var fileUri: Uri? = null

    var firebaseDatabase: DatabaseReference? = null

    private lateinit var progressDialog: ProgressDialog

    private var _binding: FragmentTeacherHomeBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeacherHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = ProgressDialog(requireContext())


        val storage = Firebase.storage("gs://daily-shopping-list-7bb71.appspot.com")
        val storageRef = storage.reference
        imagesRef =
            storageRef.child("files").child(System.currentTimeMillis().toString())

        binding.apply {

            ivUpload.setOnClickListener {
                checkLocationRequest()
            }

            selectSubject.setOnClickListener {
                SelectSubjectDialog(this@FragmentTHome).show(parentFragmentManager, "tag")
            }



            send.setOnClickListener {
                val title = binding.title.text.toString().trim()
                val description = binding.description.text.toString().trim()
                val subject = binding.selectSubject.text.toString().trim()
                val fileUri = fileUri

                if (PostValidator.validatePost(
                        title = title,
                        description = description,
                        subjectName = subject,
                        fileUri = fileUri,
                        context = requireContext()
                    )
                ) {
                    showProgressDialogWithTitle("Uploading file...")
                    uploadTask(imagesRef.putFile(fileUri!!), subject, description, title)



                }

            }
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            fileUri = data?.data
            binding.ivUpload.setImageResource(R.drawable.pdf)
        }

    }


    private lateinit var imagesRef: StorageReference
    private fun uploadTask(uploadTask: UploadTask, subject: String, description: String, title: String ) {
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it

                }
            }
            imagesRef.downloadUrl

        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                sendFileToRealTimeDB(subject, description, title, downloadUri.toString())

            }else{
                hideProgressDialogWithTitle()
            }

        }
    }


    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(intent, REQUEST_CODE)
    }
    @AfterPermissionGranted(LOCATION_REQUEST)
    private fun checkLocationRequest() {
        val perms = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (EasyPermissions.hasPermissions(requireContext(), *perms)) {
            openGalleryForImage()
        } else {
            EasyPermissions.requestPermissions(
                this, "Please grant permission",
                LOCATION_REQUEST, *perms
            )
        }
    }

    private fun sendFileToRealTimeDB(subject: String, description: String, title: String, url: String){

        showProgressDialogWithTitle("Sending Data...")
        firebaseDatabase =
            FirebaseDatabase.getInstance().reference.child("subjects").child(subject.replace(" ", "_"))
        firebaseDatabase!!.keepSynced(true)

        val key = firebaseDatabase?.push()?.key!!
        val databaseReference: DatabaseReference? = firebaseDatabase?.child(key)

        databaseReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val uploadFile= UploadFile(
                    subjectName = subject,
                    description = description,
                    fileTitle = title,
                    url = url
                )
                databaseReference.setValue(uploadFile)
                hideProgressDialogWithTitle()

            }

            override fun onCancelled(error: DatabaseError) {
                hideProgressDialogWithTitle()
            }

        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    companion object {
        const val REQUEST_CODE = 100
        const val LOCATION_REQUEST = 222
    }


    override fun onItemConfirmed(name: String) {
        binding.selectSubject.text = name
    }

    // Method to show Progress bar
    private fun showProgressDialogWithTitle(substring: String) {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
        progressDialog.setMessage(substring)
        progressDialog.show()
    }

    // Method to hide/ dismiss Progress bar
    private fun hideProgressDialogWithTitle() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.dismiss()
    }
}