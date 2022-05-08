package com.example.diplom.table.competionReview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.models.standings.Standings
import com.example.diplom.models.statistic.PlayerStatistic

class ScorersAdapter (val mContext: Context, val mutableList: MutableList<PlayerStatistic>):
    RecyclerView.Adapter<ScorersHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScorersHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val view: View = layoutInflater.inflate(R.layout.list_view_scorers, parent, false)
        return ScorersHolder(mContext, view)

    }

    override fun onBindViewHolder(holder: ScorersHolder, position: Int) {
        val task=mutableList.get(position)
        holder.bindTask(task,position)
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }
}