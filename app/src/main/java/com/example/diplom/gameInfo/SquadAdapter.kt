package com.example.diplom.gameInfo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.models.game.Game
import com.example.diplom.models.game.GamePlayer
import com.example.diplom.models.game.GameStatisticForHolder

class SquadAdapter  (val mContext: Context, val mutableList: MutableList<GamePlayer>, val game: Game,val isHome:Boolean):
    RecyclerView.Adapter<SquadHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SquadHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val view: View = layoutInflater.inflate(R.layout.list_view_squad, parent, false)
        return SquadHolder(mContext, view, game,isHome)

    }

    override fun onBindViewHolder(holder: SquadHolder, position: Int) {
        val task=mutableList.get(position)
        holder.bindTask(task,position)
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }
}