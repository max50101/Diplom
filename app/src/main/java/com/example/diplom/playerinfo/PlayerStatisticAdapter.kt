package com.example.diplom.playerinfo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.models.Player
import com.example.diplom.models.statistic.PlayerStatistic
import com.example.diplom.models.statistic.Statistics
import com.example.diplom.profile.PlayerHolder

class PlayerStatisticAdapter (val mContext: Context, val mutableList: MutableList<Statistics>):
    RecyclerView.Adapter<PlayerStatisticHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerStatisticHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val view: View = layoutInflater.inflate(R.layout.custom_list_view_player_statistic, parent, false)
        return PlayerStatisticHolder(mContext, view)

    }

    override fun onBindViewHolder(holder: PlayerStatisticHolder, position: Int) {
        val task=mutableList.get(position)
        holder.bindTask(task,position)
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }
}