package com.example.diplom.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.models.Player
import com.example.diplom.models.game.Game

class GameAdapter (val mContext: Context, val mutableList: MutableList<Game>):
    RecyclerView.Adapter<GameHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val view: View = layoutInflater.inflate(R.layout.list_view_games, parent, false)
        return GameHolder(mContext, view)

    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        val task=mutableList.get(position)
        holder.bindTask(task,position)
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }
}