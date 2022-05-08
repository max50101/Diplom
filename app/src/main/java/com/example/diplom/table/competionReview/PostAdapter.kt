package com.example.diplom.table.competionReview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.models.game.Game

class PostAdapter(val mContext: Context, val mutableList: MutableList<Array<Game>>):
    RecyclerView.Adapter<PostHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val view: View = layoutInflater.inflate(R.layout.list_view_cup, parent, false)
        return PostHolder(mContext, view)

    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val task=mutableList.get(position)
        holder.bindTask(task,position)
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}