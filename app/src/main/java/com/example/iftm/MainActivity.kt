package com.example.iftm

import android.content.Intent

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.iftm.databinding.ActivityMainBinding
import com.example.iftm.userAuthentication.utlis.Constant
import com.example.iftm.userAuthentication.utlis.PrefManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var prefManager: PrefManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        prefManager = PrefManager(this)




        val currentUser = FirebaseAuth.getInstance().currentUser

        val navigationView:NavigationView =  findViewById(R.id.nav_view);
        val header = navigationView.getHeaderView(0)
        val email: TextView =  header.findViewById(R.id.user_drawer_email);
        val name:TextView =  header.findViewById(R.id.user_drawer_name);
        var userImage:CircleImageView =  header.findViewById(R.id.profile_image);

        //  {it.providerId == "google.com"}

        if(currentUser != null && currentUser.providerData.any { it.providerId == GoogleAuthProvider.PROVIDER_ID }){
            name.text =  Firebase.auth.currentUser?.displayName.toString()
            email.text = Firebase.auth.currentUser?.email.toString()
            val iconURL = Firebase.auth.currentUser?.photoUrl.toString()
            Picasso
                .get()
                .load(iconURL)
                .into(userImage)
        }else{
            val userName = prefManager.getValue(Constant.PREF_IS_NAME)
            name.text = userName
            val userEmail = prefManager.getValue(Constant.PREF_IS_EMAIL)
            email.text = userEmail

            userImage.setOnClickListener {


            }


        }




        val navView : BottomNavigationView = binding.navigation

        binding.navigation.visibility = View.VISIBLE


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.findNavController()

        navView.setupWithNavController(navController)



        setSupportActionBar(binding.toolbar)
       // supportActionBar?.title = "Home"

        binding.toolbar.setBackgroundColor(resources.getColor(R.color.white));
        binding.toolbar.setTitleTextColor(Color.BLACK)

        //toolbar.navigationIcon?.setTint(Color.BLACK)

        binding.navView.setNavigationItemSelectedListener(this)



        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar, 0, 0
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()



    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.nav_courses -> {
                Navigation.findNavController(this,R.id.fragmentContainerView).navigate(R.id.coursesFragment)
            }
            R.id.nav_quiz -> {
                Navigation.findNavController(this,R.id.fragmentContainerView).navigate(R.id.quizFragment)
            // replaceFragment(QuizFragment())
            // Toast.makeText(this, "Quiz clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.result -> {
                Navigation.findNavController(this,R.id.fragmentContainerView).navigate(R.id.webViewFragment)
            }
            R.id.nav_change_password -> {
                Navigation.findNavController(this,R.id.fragmentContainerView).navigate(R.id.resetPasswordFragment)
            }
            R.id.nav_report_complaint -> {
                Toast.makeText(this, "Report Complaint clicked", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(this,R.id.fragmentContainerView).navigate(R.id.reportComplaintFragment)

            }
            R.id.nav_logout -> {

                prefManager.clear()

                Firebase.auth.signOut()
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("824256831838-ksi98eeunueqquvq7fct14o3nmeh0auf.apps.googleusercontent.com")
                    .requestEmail()
                    .build()

                googleSignInClient = GoogleSignIn.getClient(this, gso)
                googleSignInClient.signOut()
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this, "Sign out clicked", Toast.LENGTH_SHORT).show()



            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragment)
            .commit()
    }
   /* override fun onBackPressed() {
        if (backPressedTime + 3000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finish()
        } else {
            Toast.makeText(this, "Press back again to leave the app.", Toast.LENGTH_LONG).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
*/

}
