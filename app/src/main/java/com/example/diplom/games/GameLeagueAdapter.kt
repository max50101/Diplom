package com.example.diplom.games

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.models.Player
import com.example.diplom.profile.PlayerHolder

class GameLeagueAdapter (val mContext: Context, val mutableList: MutableList<tempLeagueGame>):
    RecyclerView.Adapter<GameLeagueHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameLeagueHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val view: View = layoutInflater.inflate(R.layout.list_view_leagues_games, parent, false)
        return GameLeagueHolder(mContext, view)

    }

    override fun onBindViewHolder(holder: GameLeagueHolder, position: Int) {
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