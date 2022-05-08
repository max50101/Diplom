package com.example.diplom.teamChoose

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.models.Team

class TeamAdapter (val mContext: Context, val mutableList: MutableList<Team>, val acitvity:AppCompatActivity):
    RecyclerView.Adapter<TeamHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val view: View = layoutInflater.inflate(R.layout.list_view, parent, false)
        return TeamHolder(mContext, view)

    }

    override fun onBindViewHolder(holder: TeamHolder, position: Int) {
        val task=mutableList.get(position)
        holder.bindTask(task,position,acitvity)
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }
}