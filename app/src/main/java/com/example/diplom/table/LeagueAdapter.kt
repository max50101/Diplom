package com.example.diplom.table

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.models.CountryLeague
import com.example.diplom.models.game.Game
import com.example.diplom.profile.GameHolder

class LeagueAdapter  (val mContext: Context, val mutableList: MutableList<CountryLeague>):
    RecyclerView.Adapter<LeagueHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val view: View = layoutInflater.inflate(R.layout.list_view_leagues, parent, false)
        return LeagueHolder(mContext, view)

    }

    override fun onBindViewHolder(holder: LeagueHolder, position: Int) {
        val task=mutableList.get(position)
        holder.bindTask(task,position)
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }
}