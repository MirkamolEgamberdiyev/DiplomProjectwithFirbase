package com.tuit.tuit.ui.student.adapter
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.tuit.tuit.R
import com.tuit.tuit.repository.model.MainModelItem
import com.tuit.tuit.utils.Constant.chosenAnswers
import com.tuit.tuit.utils.Constant.correctAnswers

class ResultsAdapter(var context: Context, private val questionNumberList: List<MainModelItem>) :
    RecyclerView.Adapter<ResultsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View =
            LayoutInflater.from(context).inflate(R.layout.layout_togri_javoblar, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.savolNomer.text = (position + 1).toString() + "-savol"

        holder.text_togri_javob.visibility = View.VISIBLE
        holder.onlineImageResult.visibility = View.GONE
        holder.text_togri_javob.text = questionNumberList[position].correctAnswer


        holder.text_togri_javob.text = questionNumberList[position].correctAnswer
        holder.answerA.text = questionNumberList[position].optionA
        holder.answerB.text = questionNumberList[position].optionB
        holder.answerC.text = questionNumberList[position].optionC
        holder.answerD.text = questionNumberList[position].optionD
        holder.answerA.isChecked = false
        holder.answerB.isChecked = false
        holder.answerC.isChecked = false
        holder.answerD.isChecked = false
        if (correctAnswers[position] == chosenAnswers[position]
        ) {
            holder.isCorrectImage.setImageResource(R.drawable.markasdone)
            holder.togri_javobni_korish.text = "Sizning javobingiz to'g'ri"
            holder.togri_javobni_korish.setTextColor(Color.parseColor(R.color.base_blue.toString()))
            holder.togri_javobni_korish2.text = "To'g'ri javob " + correctAnswers[position]
            holder.answerA.setTextColor(Color.BLACK)
            holder.answerB.setTextColor(Color.BLACK)
            holder.answerC.setTextColor(Color.BLACK)
            holder.answerD.setTextColor(Color.BLACK)
            when (correctAnswers[position]) {
                "A" -> {
                    holder.answerA.isChecked = true
                    holder.answerA.setTextColor(Color.parseColor(R.color.base_blue.toString()))
                }
                "B" -> {
                    holder.answerB.isChecked = true
                    holder.answerB.setTextColor(Color.parseColor(R.color.base_blue.toString()))
                }
                "C" -> {
                    holder.answerC.isChecked = true
                    holder.answerC.setTextColor(Color.parseColor(R.color.base_blue.toString()))
                }
                "D" -> {
                    holder.answerD.isChecked = true
                    holder.answerD.setTextColor(Color.parseColor(R.color.base_blue.toString()))
                }
            }
        } else {
            holder.isCorrectImage.setImageResource(R.drawable.markasincorrect)
            if (chosenAnswers[position] == "E") {
                holder.togri_javobni_korish.setTextColor(Color.BLACK)
                holder.togri_javobni_korish.text = "Sizning hech qandey javob belgilamagansiz"
            } else {
                holder.togri_javobni_korish.setTextColor(Color.RED)
                holder.togri_javobni_korish.text = "Sizning javobingiz xato"
            }
            holder.togri_javobni_korish2.text = "To'g'ri javob " + correctAnswers[position]
            holder.answerA.setTextColor(Color.BLACK)
            holder.answerB.setTextColor(Color.BLACK)
            holder.answerC.setTextColor(Color.BLACK)
            holder.answerD.setTextColor(Color.BLACK)
            when (chosenAnswers[position]) {
                "A" -> {
                    holder.answerA.isChecked = true
                    holder.answerA.setTextColor(Color.RED)
                }
                "B" -> {
                    holder.answerB.isChecked = true
                    holder.answerB.setTextColor(Color.RED)
                }
                "C" -> {
                    holder.answerC.isChecked = true
                    holder.answerC.setTextColor(Color.RED)
                }
                "D" -> {
                    holder.answerD.isChecked = true
                    holder.answerD.setTextColor(Color.RED)
                }
            }
        }
        holder.answerA.isEnabled = false
        holder.answerB.isEnabled = false
        holder.answerC.isEnabled = false
        holder.answerD.isEnabled = false
        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.layout_animation)
        holder.itemView.startAnimation(animation)
    }

    override fun getItemCount(): Int {
        return questionNumberList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var card_togri_javob: MaterialCardView
        var text_togri_javob: TextView
        var togri_javobni_korish: TextView
        var togri_javobni_korish2: TextView
        var savolNomer: TextView
        var answerA: RadioButton
        var answerB: RadioButton
        var answerC: RadioButton
        var answerD: RadioButton
        var isCorrectImage: ImageView
        var onlineImageResult: ImageView

        init {
            card_togri_javob = itemView.findViewById(R.id.card_togri_javoblar)
            savolNomer = itemView.findViewById(R.id.savolNomeri)
            text_togri_javob = itemView.findViewById(R.id.togriJavobText)
            answerA = itemView.findViewById(R.id.buttonA)
            answerB = itemView.findViewById(R.id.buttonB)
            answerC = itemView.findViewById(R.id.buttonC)
            answerD = itemView.findViewById(R.id.buttonD)
            togri_javobni_korish = itemView.findViewById(R.id.togriJavobKorsatish)
            togri_javobni_korish2 = itemView.findViewById(R.id.togriJavobKorsatish2)
            isCorrectImage = itemView.findViewById(R.id.callIcon)
            onlineImageResult = itemView.findViewById(R.id.onlineImageResult)
        }
    }

}