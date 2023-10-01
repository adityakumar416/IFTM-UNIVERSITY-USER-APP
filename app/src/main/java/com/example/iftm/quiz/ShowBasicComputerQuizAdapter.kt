package com.example.iftm.quiz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.iftm.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import kotlin.Int

class ShowBasicComputerQuizAdapter(
    private val courseList: ArrayList<BasicComputerQuizModel>,
    private val context: Context?=null
): RecyclerView.Adapter<ShowBasicComputerQuizAdapter.ViewHolder>() {

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){

        val question : TextView = view.findViewById(R.id.quiz_question)
        val optionA : TextView = view.findViewById(R.id.bacicComOption1)
        val optionB :TextView = view.findViewById(R.id.bacicComOption2)
        val optionC : TextView = view.findViewById(R.id.bacicComOption3)
        val optionD : TextView = view.findViewById(R.id.bacicComOption4)
        val answer : TextView = view.findViewById(R.id.answer)

        val showAnswerText : MaterialTextView = view.findViewById(R.id.show_answer_text)
        val hideAnswerText : MaterialTextView = view.findViewById(R.id.hide_answer_text)

        val card : MaterialCardView = view.findViewById(R.id.materialCardView)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.quiz_card,parent,false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val basicComputerQuizModel: BasicComputerQuizModel = courseList[position]

            holder.question.setText("Qus.- "+basicComputerQuizModel.question)
        holder.optionA.setText(basicComputerQuizModel.optionA)
        holder.optionB.setText(basicComputerQuizModel.optionB)
        holder.optionC.setText(basicComputerQuizModel.optionC)
        holder.optionD.setText(basicComputerQuizModel.optionD)
        holder.answer.setText("- "+basicComputerQuizModel.answer)

        holder.answer.visibility = View.GONE
        holder.hideAnswerText.visibility = View.GONE

        holder.showAnswerText.setOnClickListener {
            holder.answer.visibility = View.VISIBLE
            holder.hideAnswerText.visibility = View.VISIBLE
            holder.showAnswerText.visibility = View.GONE

        }
        holder.hideAnswerText.setOnClickListener {
            holder.answer.visibility = View.GONE
            holder.hideAnswerText.visibility = View.GONE
            holder.showAnswerText.visibility = View.VISIBLE

        }


    }




    override fun getItemCount(): Int {

            return courseList.size
    }
}



