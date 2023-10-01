package com.example.iftm.fragments

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.iftm.MainActivity
import com.example.iftm.R
import com.example.iftm.ReportComplaintModel
import com.example.iftm.databinding.FragmentReportComplaintBinding
import com.example.iftm.userAuthentication.utlis.Constant
import com.example.iftm.userAuthentication.utlis.PrefManager
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.IOException

class ReportComplaintFragment : Fragment() {
    private lateinit var binding:FragmentReportComplaintBinding
    private lateinit var uri: Uri
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReportComplaintBinding.inflate(layoutInflater, container, false)

        uri = Uri.parse(toString())
        (activity as MainActivity).supportActionBar?.title = getString(R.string.reportComplaint)

        (activity as MainActivity).binding.navigation.visibility = View.GONE

        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser


        if(user != null && user.providerData.any()){
            binding.userName.text = "Hi, "+Firebase.auth.currentUser?.displayName.toString()
        }else{
            val userName = prefManager.getValue(Constant.PREF_IS_NAME)
            binding.userName.text = "Hi, "+userName.toString()
        }


        binding.attachFile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 71)
        }


        binding.sendComplaint.setOnClickListener {

            val courseName =binding.complaintBox.text.toString()
            val attachFile =binding.attachFile.text.toString()
          //   val courseFees =binding.courseFees.text.toString()*/

            if(courseName.isEmpty()){
                Toast.makeText(
                    context,
                    "Please enter Something in the Complaint Box.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if(attachFile.isEmpty()){
                Toast.makeText(
                    context,
                    "Please Attach the Screenshot.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {
                addDataToFirebase(courseName)
            }

        }


        return binding.root
    }

    private fun addDataToFirebase(courseName: String) {
        val processDialog = ProgressDialog(context)
        processDialog.setMessage("Complaint Sending..")
        processDialog.setCancelable(false)
        processDialog.show()

        val firebaseStorage = FirebaseStorage.getInstance().getReference("reportComplaint")
        val firebaseDatabase = FirebaseDatabase.getInstance().getReference("reportComplaint")

        val storageRef = firebaseStorage.child(System.currentTimeMillis().toString()+"."+ getFileExtension(uri))
        storageRef.putFile(uri)
            .addOnSuccessListener { takeSnapshot ->
                Log.i(ContentValues.TAG, "onSuccess Main: $takeSnapshot")

                Toast.makeText(context, "Complaint was successfully send.", Toast.LENGTH_SHORT).show()
                processDialog.dismiss()

                val urlTask: Task<Uri> = takeSnapshot!!.storage.downloadUrl
                while (!urlTask.isSuccessful);
                val downloadUrl: Uri = urlTask.result
                Log.i(ContentValues.TAG, "onSuccess: $downloadUrl")

                val reportComplaintModel = ReportComplaintModel(
                    firebaseDatabase!!.push().key,
                    downloadUrl.toString(),
                    courseName
                    /*courseDuration,
                    courseFees*/
                )
                val uploadId = reportComplaintModel.complaintId

                firebaseDatabase!!.child(uploadId.toString()).setValue(reportComplaintModel)

                binding.complaintBox.text = null
                /* binding.courseDuration.text = null
                 binding.courseFees.text = null*/
                binding.attachFile.text= null
                refreshCurrentFragment()
            }

            .addOnFailureListener {
                Toast.makeText(context, "Failed to send Complaint.", Toast.LENGTH_SHORT).show()
              //  Toast.makeText(context, "Please Attach the Screenshot.", Toast.LENGTH_SHORT).show()
                processDialog.dismiss()

            }
            .addOnProgressListener { taskSnapshot -> //displaying the upload progress
                val progress =
                    100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                processDialog.setMessage("Sending " + progress.toInt() + "%...")
            }
    }

    private fun getFileExtension(uri: Uri): String? {
        val cR: ContentResolver = requireContext().contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 71 && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }

            uri = data.data!!

        }
    }


    private fun refreshCurrentFragment(){
        val id = findNavController().currentDestination?.id
        findNavController().popBackStack(id!!,true)
        findNavController().navigate(id)
    }
}