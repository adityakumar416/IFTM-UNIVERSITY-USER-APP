package com.example.iftm.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.example.iftm.MainActivity
import com.example.iftm.R
import com.example.iftm.databinding.FragmentQuizBinding


class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding
    lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentQuizBinding.inflate(layoutInflater, container, false)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.quiz)
        (activity as MainActivity).binding.navigation.visibility = View.VISIBLE
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        binding.basicComputerCard.setOnClickListener {
            findNavController().navigate(R.id.basicComputerQuizFragment)
        }
        binding.androidQuizCard.setOnClickListener {
            findNavController().navigate(R.id.androidQuizFragment)
        }

        return binding.root
    }
}