package com.example.iftm.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iftm.CourseModel
import com.example.iftm.MainActivity
import com.example.iftm.R
import com.example.iftm.ShowAllCoursesAdapter
import com.example.iftm.ShowHomeHorizontalCoursesAdapter
import com.example.iftm.databinding.FragmentCoursesBinding
import com.example.iftm.databinding.FragmentHomeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CoursesFragment : Fragment() {

    private lateinit var binding: FragmentCoursesBinding
    lateinit var toolbar: Toolbar
    private var courseModel: CourseModel? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentCoursesBinding.inflate(layoutInflater, container, false)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.courses)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        val arrCourse= ArrayList<CourseModel>()

//        (activity as MainActivity).binding.navigation.visibility = View.GONE


        val firebaseDatabase = FirebaseDatabase.getInstance()

        firebaseDatabase.getReference("courses").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                arrCourse?.clear()
                for(postSnapshot in snapshot.children){
                    courseModel = postSnapshot.getValue(CourseModel::class.java)
                    Log.i(ContentValues.TAG, "onCreateView: Data > " + postSnapshot.value)
                    arrCourse?.add(courseModel!!)

                }
                binding.recyclerview.layoutManager = LinearLayoutManager(
                    context,
                    RecyclerView.VERTICAL,
                    false
                )

                binding.recyclerview.adapter = ShowAllCoursesAdapter(arrCourse!!,context)
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        })

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.homeFragment)
            }
        })

        return binding.root
    }



}