package com.example.iftm

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

import com.example.iftm.databinding.ActivityRegistrationBinding
import com.example.iftm.userAuthentication.UserModal
import com.example.iftm.userAuthentication.utlis.Constant
import com.example.iftm.userAuthentication.utlis.PrefManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var prefManager: PrefManager
    private lateinit var auth: FirebaseAuth
    private var userModal: UserModal? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        auth = Firebase.auth

        binding.signIn.setOnClickListener {

            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()

        }

        prefManager = PrefManager(this)



        binding.signUpBtn.setOnClickListener {

            val name = binding.nameEditText.text.toString().trim()
            val number = binding.numberEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (binding.nameEditText.text!!.isEmpty()) {
                binding.nameEditText.requestFocus()
                Snackbar.make(binding.nameEditText, "Name is Mandatory.", Snackbar.LENGTH_SHORT).show();

            } else if (binding.numberEditText.text!!.isEmpty()) {
                binding.numberEditText.requestFocus()
                Snackbar.make(binding.numberEditText, "Phone Number is Mandatory.", Snackbar.LENGTH_SHORT).show();
            } else if (binding.emailEditText.text!!.isEmpty()) {
                binding.emailEditText.requestFocus()
                Snackbar.make(binding.emailEditText, "Email is Mandatory.", Snackbar.LENGTH_SHORT).show();
            }
            else if (binding.passwordEditText.text!!.isEmpty()) {
                binding.passwordEditText.requestFocus()
                Snackbar.make(binding.passwordEditText,"Password is Mandatory.", Snackbar.LENGTH_SHORT).show();
            }else if (binding.confirmPasswordEditText.text!!.isEmpty()) {
                binding.confirmPasswordEditText.requestFocus()
                Snackbar.make(binding.confirmPasswordEditText, "Confirm Password is Mandatory.", Snackbar.LENGTH_SHORT).show();
            }
            /*    else if (binding.confirmPasswordEditText.text != binding.passwordEditText.text) {
                    binding.confirmPasswordEditText.requestFocus()
                    Toast.makeText(context,"Confirm Password are not same as the Password.", Toast.LENGTH_SHORT).show();

                }*/
            else{


                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (!it.isSuccessful) return@addOnCompleteListener

                        // else if successful
                        Log.d(TAG, "Successfully created user with uid: ${it.result!!.user?.uid}")
                        addDataToFirebase(name, number, email,password)
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "Failed to create user: ${it.message}")
                        //  binding.loadingView.visibility = View.GONE
                        Toast.makeText(this, "${it.message}", Toast.LENGTH_LONG).show()
                    }

                val database = FirebaseDatabase.getInstance()
                val userNameFatch: DatabaseReference = database.getReference("users") // Replace with your actual path

                userNameFatch.addValueEventListener(object : ValueEventListener {

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for(postSnapshot in dataSnapshot.children){

                            userModal = postSnapshot.getValue(UserModal::class.java)
                            Log.i("homeUserData", "onCreateView: UserData > " + postSnapshot.value)
/*
                            prefManager.name(Constant.PREF_IS_NAME, userModal?.name.toString())
                            prefManager.number(Constant.PREF_IS_NUMBER,userModal?.number.toString())
                            prefManager.email(Constant.PREF_IS_EMAIL,userModal?.email.toString())
                            prefManager.password(Constant.PREF_IS_PASSWORD,userModal?.password.toString())*/


                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

             /*   prefManager.name(Constant.PREF_IS_NAME,binding.nameEditText.text.toString())
                prefManager.number(Constant.PREF_IS_NUMBER,binding.numberEditText.text.toString())
                prefManager.email(Constant.PREF_IS_EMAIL,binding.emailEditText.text.toString())
                prefManager.password(Constant.PREF_IS_PASSWORD,binding.passwordEditText.text.toString())
*/


            }


        }

    }


    private fun addDataToFirebase(name: String, number: String, email: String, password: String) {
        val uid = FirebaseAuth.getInstance().uid ?: return

        val firebaseDatabase =  FirebaseDatabase.getInstance().getReference("users").child(number)


        firebaseDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                Log.i(ContentValues.TAG, "onSuccess Main: $snapshot")



                val userModal = UserModal(
                    name, number, email, password
                )

                firebaseDatabase.setValue(userModal)
                if (snapshot.exists()) {
                    Snackbar.make(
                        binding.signUpBtn,
                        "Registration Successfully",
                        Snackbar.LENGTH_SHORT
                    )
                        .show()

                } else {
                    Snackbar.make(binding.signUpBtn, "Something went Wrong", Snackbar.LENGTH_SHORT).show()
                }

                /* binding.nameEditText.text = null
                 binding.numberEditText.text= null
                 binding.emailEditText.text= null
                 binding.passwordEditText.text= null*/

            }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@RegistrationActivity,"Registration Failed.", Toast.LENGTH_SHORT).show();
            }

        })

    }

}