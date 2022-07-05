package com.tuit.tuit.ui.student.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.tuit.tuit.databinding.LayoutVariantItemBinding

class MainPageAdapter(private val listener: OnItemClicked): RecyclerView.Adapter<MainPageAdapter.MyAdapter>() {

    inner class MyAdapter(private val itemBinding: LayoutVariantItemBinding)
        :RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener{
        fun bind(){
            itemBinding.title.text= "${ adapterPosition + 1 } variant"
        }

        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            listener.onItemCLicked( adapterPosition+1)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter {
        val view=LayoutVariantItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyAdapter(view)
    }

    override fun onBindViewHolder(holder: MyAdapter, position: Int) {
        holder.bind()
    }

    interface OnItemClicked{
        fun onItemCLicked( variantId:Int)
    }

    override fun getItemCount()=7
}