package com.example.diplom.table.competionReview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.models.game.GameLeague
import com.example.diplom.models.standings.Standings
import com.example.diplom.table.CompetitionHolder

class StandingAdapter (val mContext: Context, val mutableList: MutableList<Standings>,val isHome:Int):
    RecyclerView.Adapter<StandingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandingHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val view: View = layoutInflater.inflate(R.layout.list_view_standing, parent, false)
        return StandingHolder(mContext, view)

    }

    override fun onBindViewHolder(holder: StandingHolder, position: Int) {
        holder.setIsRecyclable(false)
        val task=mutableList.get(position)
        holder.bindTask(task,position,isHome)

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