package com.example.diplom.table.competionReview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.models.game.Game
import com.example.diplom.models.statistic.PlayerStatistic

class ResultsAdapter (val mContext: Context, val mutableList: MutableList<Game>):
    RecyclerView.Adapter<ResultsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val view: View = layoutInflater.inflate(R.layout.list_view_games_tours, parent, false)
        return ResultsHolder(mContext, view)

    }

    override fun onBindViewHolder(holder: ResultsHolder, position: Int) {
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