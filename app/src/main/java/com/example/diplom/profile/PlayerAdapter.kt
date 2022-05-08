package com.example.diplom.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.models.Player
import com.example.diplom.models.Team
import com.example.diplom.teamChoose.TeamHolder

class PlayerAdapter  (val mContext: Context, val mutableList: MutableList<Player>):
    RecyclerView.Adapter<PlayerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val view: View = layoutInflater.inflate(R.layout.list_view_playes, parent, false)
        return PlayerHolder(mContext, view)

    }

    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        val task=mutableList.get(position)
        holder.bindTask(task,position)
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }
}