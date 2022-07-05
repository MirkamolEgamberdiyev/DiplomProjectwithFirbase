package com.tuit.tuit.ui.student.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.tuit.tuit.R
import com.tuit.tuit.databinding.LayoutFileItemBinding
import com.tuit.tuit.databinding.LayoutVariantItemBinding
import com.tuit.tuit.repository.model.Data
import com.tuit.tuit.ui.student.home.HomeFragment

class FileAdapter(private val list: ArrayList<Data>,  val listener: OnClickListener) :
    RecyclerView.Adapter<FileAdapter.MyAdapter>() {

    inner class MyAdapter(private val itemBinding: LayoutFileItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {
        fun bind(data: Data) = with(itemBinding) {
            ivImage.setImageResource(R.drawable.ic_tuit)
            tvTitle.text = data.fileTitle
            tvDescription.text = data.description


        }
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
         listener.onItemClicked()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter {
        val view = LayoutFileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyAdapter(view)
    }

    override fun onBindViewHolder(holder: MyAdapter, position: Int) {
        holder.bind(list[position])
    }


    override fun getItemCount() = list.size

    interface OnClickListener {
        fun onItemClicked()
    }
}