package com.example.diplom.table.competionReview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.models.game.Game
import com.example.diplom.table.tempCup

class CupStandingAdapter (val mContext: Context, val mutableList: MutableList<tempCup>):
    RecyclerView.Adapter<CupStandingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CupStandingHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val view: View = layoutInflater.inflate(R.layout.list_view_standing_cup, parent, false)
        return CupStandingHolder(mContext, view)

    }

    override fun onBindViewHolder(holder: CupStandingHolder, position: Int) {
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