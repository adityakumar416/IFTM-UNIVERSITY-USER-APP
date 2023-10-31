package com.example.iftm.fragments

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.iftm.MainActivity
import com.example.iftm.databinding.FragmentResetPasswordBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordFragment : Fragment() {
 private lateinit var binding: FragmentResetPasswordBinding
    private val auth = FirebaseAuth.getInstance()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResetPasswordBinding.inflate(layoutInflater, container, false)

        (activity as MainActivity).supportActionBar?.title = "Update Password "

        (activity as MainActivity).binding.navigation.visibility = View.GONE



        binding.resetButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()

            if (email.isNotEmpty()) {
                showProgressDialog(email)
            } else {
                binding.emailEditText.requestFocus()
                Snackbar.make(
                    binding.emailEditText,
                    "Password is Mandatory.",
                    Snackbar.LENGTH_SHORT
                ).show();
            }
        }



        return binding.root
    }

    private fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "We have sent you a Email.", Toast.LENGTH_SHORT).show()


                } else {
                    Toast.makeText(context, "Something went wrong !", Toast.LENGTH_SHORT).show()

                         }
            }
    }

    private fun showProgressDialog(email: String) {

        if (context != null) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Update Password")
                .setMessage("Do you want to Update Password ?")
                .setNegativeButton("No", object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog?.dismiss()
                    }
                })
                .setPositiveButton("Yes",object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                       resetPassword(email)
                        dialog?.dismiss()
                    }

                }).show()
        }
    }

}
