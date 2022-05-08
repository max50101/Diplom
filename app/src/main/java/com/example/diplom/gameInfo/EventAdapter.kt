package com.example.diplom.gameInfo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.models.game.Game
import com.example.diplom.models.game.event.Event
import com.example.diplom.profile.GameHolder

class EventAdapter (val mContext: Context, val mutableList: MutableList<Event>, val game:Game):
    RecyclerView.Adapter<EventHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val view: View = layoutInflater.inflate(R.layout.list_view_event, parent, false)
        return EventHolder(mContext, view, game)

    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        val task=mutableList.get(position)
        holder.bindTask(task,position)
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }
}