package com.example.iftm.fragments

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.registerReceiver
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.iftm.BannerModel
import com.example.iftm.CourseModel
import com.example.iftm.MainActivity
import com.example.iftm.NetworkReceiver
import com.example.iftm.PopularStreamsCourseModel
import com.example.iftm.R
import com.example.iftm.ShowHomeHorizontalCoursesAdapter
import com.example.iftm.databinding.FragmentHomeBinding
import com.example.iftm.quiz.androidQuiz.AndroidQuizModel
import com.example.iftm.userAuthentication.UserModal
import com.example.iftm.userAuthentication.utlis.Constant
import com.example.iftm.userAuthentication.utlis.PrefManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import java.util.Timer
import java.util.TimerTask

class HomeFragment : Fragment() {


    private var integrationsArray = arrayListOf(
        R.drawable.integrations_1inch,
        R.drawable.integrations_aave,
        R.drawable.integrations_allianceblock,
        R.drawable.integrations_balancer,
        R.drawable.integrations_celo,
        R.drawable.integrations_compound,
        R.drawable.integrations_curve,
        R.drawable.integrations_dex,
        R.drawable.integrations_instadapp,
        R.drawable.integrations_kyb,
        R.drawable.integrations_luaswap,
        R.drawable.integrations_maker,
        R.drawable.integrations_matic,
        R.drawable.integrations_opensea,
        R.drawable.integrations_paraswap,
        R.drawable.integrations_rarible,
        R.drawable.integrations_sushi,
        R.drawable.integrations_synthetix,
        R.drawable.integrations_tokensets,
        R.drawable.integrations_unilend,
        R.drawable.integrations_uniswap,
        R.drawable.integrations_walletconnect,
        R.drawable.integrations_yearn,
        R.drawable.integrations_zeroswap,
    )

    private lateinit var binding: FragmentHomeBinding
    private var bannerModel: BannerModel? = null
    private var popularStreamsCourseModel: PopularStreamsCourseModel? = null
    private lateinit var prefManager: PrefManager
    private var networkReceiver: NetworkReceiver? = null
    private var userModal: UserModal? = null

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentHomeBinding.inflate(layoutInflater, container, false)
        prefManager = PrefManager(requireContext())
        setupNetworkListener()

        (activity as MainActivity).supportActionBar?.title = getString(R.string.home)

        (activity as MainActivity).binding.navigation.visibility = View.VISIBLE

        binding.autoRecyclerView.startScrolling(integrationsArray)

        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        val currentUser = FirebaseAuth.getInstance().currentUser
        val firebaseDatabase = FirebaseDatabase.getInstance()


        //  {it.providerId == "google.com"}

        if(currentUser != null && currentUser.providerData.any { it.providerId == GoogleAuthProvider.PROVIDER_ID }){
            binding.title.text = "Hi, "+Firebase.auth.currentUser?.displayName.toString().split("\\s".toRegex())[0]+" !"
            Log.i("googleAuthFirebaseName","Hi, "+Firebase.auth.currentUser?.displayName.toString()+"!" )
        }else {
            val userName = prefManager.getValue(Constant.PREF_IS_NAME)

            binding.title.text = "Hi, " + userName.toString().split("\\s".toRegex())[0]+ " !"
            Log.i("sharedPrefrenceLogin", "Hi, " + userName.toString() + " !")

        }


        val slideModels = ArrayList<SlideModel>()
        val arrCourse= ArrayList<PopularStreamsCourseModel>()


        val mDatabaseRef = firebaseDatabase.getReference("banners")

            mDatabaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                slideModels.clear()
                for(postSnapshot in snapshot.children){
                    bannerModel = postSnapshot.getValue(BannerModel::class.java)
                    Log.i(TAG, "onCreateView: Data > " + postSnapshot.value)
                    slideModels.add(SlideModel("" + bannerModel?.url,ScaleTypes.FIT))


                }
                binding.shimmerFrameLayout.stopShimmerAnimation()
                binding.shimmerFrameLayout.visibility = View.GONE
                binding.homeLayout.visibility = View.VISIBLE
                binding.imageSlider.setImageList(slideModels)
            }

            override fun onCancelled(error: DatabaseError) {
                binding.shimmerFrameLayout.visibility = View.GONE

                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        })


        firebaseDatabase.getReference("popularStreams").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                arrCourse?.clear()
                for(postSnapshot in snapshot.children){
                    popularStreamsCourseModel = postSnapshot.getValue(PopularStreamsCourseModel::class.java)
                    Log.i(TAG, "onCreateView: Data > " + postSnapshot.value)
                    arrCourse?.add(popularStreamsCourseModel!!)

                }


                binding.recyclerView.layoutManager = LinearLayoutManager(
                    context,
                    RecyclerView.HORIZONTAL,
                    false
                )

                binding.shimmerFrameLayout.stopShimmerAnimation()
                binding.shimmerFrameLayout.visibility = View.GONE
                binding.homeLayout.visibility = View.VISIBLE
                binding.recyclerView.adapter = ShowHomeHorizontalCoursesAdapter(arrCourse!!,context)
            }
            override fun onCancelled(error: DatabaseError) {
                binding.shimmerFrameLayout.visibility = View.GONE

                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        })




        return binding.root
    }
    private fun setupNetworkListener() {
        NetworkReceiver.networkReceiverListener =
            object : NetworkReceiver.NetworkReceiverListener {
                override fun onNetworkConnectionChanged(isConnected: Boolean) {
                    toggleNoInternetBar(!isConnected)
                }
            }
    }

    private fun toggleNoInternetBar(display: Boolean) {
        if (display) {
            val enterAnim = AnimationUtils.loadAnimation(requireActivity(), R.anim.enter_from_bottom)
            binding.noInternetBar.startAnimation(enterAnim)
        } else {
            val exitAnim = AnimationUtils.loadAnimation(requireActivity(), R.anim.exit_to_bottom)
            binding.noInternetBar.startAnimation(exitAnim)
        }
        binding.noInternetBar.visibility = if (display) View.VISIBLE else View.GONE
    }

    override fun onStart() {
        super.onStart()
        networkReceiver = NetworkReceiver()
        requireActivity().registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onStop() {
        super.onStop()
        networkReceiver?.let {
            requireActivity().unregisterReceiver(it) }
    }
    override fun onResume() {
        super.onResume()
        binding.shimmerFrameLayout.startShimmerAnimation()
    }

    override fun onPause() {
        binding.shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
    }


}