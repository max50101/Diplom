package com.example.diplom.table

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.models.CountryLeague
import com.example.diplom.models.game.GameLeague

class CompetinionAdapter(val mContext: Context, val mutableList: MutableList<GameLeague>):
    RecyclerView.Adapter<CompetitionHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetitionHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val view: View = layoutInflater.inflate(R.layout.list_view_leagues_sub, parent, false)
        return CompetitionHolder(mContext, view)

    }

    override fun onBindViewHolder(holder: CompetitionHolder, position: Int) {
        val task=mutableList.get(position)
        holder.bindTask(task,position)
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }
}