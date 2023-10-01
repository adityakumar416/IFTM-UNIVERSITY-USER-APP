package com.example.iftm

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.lang.String
import kotlin.Int
import kotlin.toString

class ShowHomeHorizontalCoursesAdapter(
    private val streamsCourseList: ArrayList<PopularStreamsCourseModel>,
    private val context: Context?=null
): RecyclerView.Adapter<ShowHomeHorizontalCoursesAdapter.ViewHolder>() {

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){

        val courseImage : ImageView = view.findViewById(R.id.course_image)
        val courseName : TextView = view.findViewById(R.id.course_name)
        val knowMore : TextView = view.findViewById(R.id.know_more)

        /* val courseDuration :TextView = view.findViewById(R.id.course_duration)
         val courseFees : TextView = view.findViewById(R.id.course_fees)
         val knowMore : TextView = view.findViewById(R.id.know_more)
         val card : MaterialCardView = view.findViewById(R.id.materialCardView)*/


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.popular_course_view,parent,false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val popularStreamsCourseModel:PopularStreamsCourseModel = streamsCourseList[position]

            holder.courseName.setText("  "+popularStreamsCourseModel.courseName)
        /*holder.courseDuration.setText("  "+courseModel.courseDuration)
        holder.courseFees.setText("  "+courseModel.courseFees)*/



        Picasso
            .get()
            .load(popularStreamsCourseModel.courseImage)
            .into(holder.courseImage)

/*
            Glide
                .with(Context)
            .load(imageModel.url)
            .into(holder.image)*/
        holder.knowMore.setOnClickListener {
            it.findNavController().navigate(R.id.knowMoreWebViewFragment)
        }


    }




    override fun getItemCount(): Int {

            return streamsCourseList.size
    }
}



