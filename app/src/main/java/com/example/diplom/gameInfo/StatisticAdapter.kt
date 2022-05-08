package com.example.diplom.gameInfo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.models.game.Game
import com.example.diplom.models.game.GameStatisticForHolder
import com.example.diplom.models.game.event.Event

class StatisticAdapter (val mContext: Context, val mutableList: MutableList<GameStatisticForHolder>, val game: Game):
    RecyclerView.Adapter<StatisticHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val view: View = layoutInflater.inflate(R.layout.custom_list_layout_statistic, parent, false)
        return StatisticHolder(mContext, view, game)

    }

    override fun onBindViewHolder(holder: StatisticHolder, position: Int) {
        val task=mutableList.get(position)
        holder.bindTask(task,position)
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }
}