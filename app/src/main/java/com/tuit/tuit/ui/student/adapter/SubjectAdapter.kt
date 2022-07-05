package com.tuit.tuit.ui.student.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuit.tuit.databinding.ItemSubjectsBinding
class SubjectAdapter(private val list: ArrayList<String>) :
    RecyclerView.Adapter<SubjectAdapter.MyAdapter>() {

    var onClick: ((String) -> Unit)? = null
    inner class MyAdapter(private val itemBinding: ItemSubjectsBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(data: String) = with(itemBinding) {
            subjectName.text = data.replace("_", " ")

            root.setOnClickListener {
                onClick?.invoke(data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter {
        val view = ItemSubjectsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyAdapter(view)
    }

    override fun onBindViewHolder(holder: MyAdapter, position: Int) {
        holder.bind(list[position])
    }


    override fun getItemCount() = list.size
}