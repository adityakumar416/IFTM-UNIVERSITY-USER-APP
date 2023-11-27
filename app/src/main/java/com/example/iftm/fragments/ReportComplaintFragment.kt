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


}