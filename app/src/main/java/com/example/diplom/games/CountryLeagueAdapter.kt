package com.example.diplom.games

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.models.game.Game

class CountryLeagueAdapter (val mContext: Context, val mutableList: MutableList<Game>):
    RecyclerView.Adapter<CountryLeagueHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryLeagueHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val view: View = layoutInflater.inflate(R.layout.list_view_country_league_games, parent, false)
        return CountryLeagueHolder(mContext, view)

    }

    override fun onBindViewHolder(holder: CountryLeagueHolder, position: Int) {
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