package com.tuit.tuit.ui.teacher.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuit.tuit.databinding.LayoutSubjectBinding

class SelectSubjectAdapter(private val list: List<String>, private val listener: OnSubjectClickListener) : RecyclerView.Adapter<SelectSubjectAdapter.MyViewHolder>() {


    inner class MyViewHolder(private val binding: LayoutSubjectBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener{

        fun bind(item: String){
            binding.textView.text = item
        }

        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            listener.onItemClicked(list[adapterPosition])
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding = LayoutSubjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = list[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int =  list.size


    interface OnSubjectClickListener{
        fun onItemClicked(name: String)
    }
}