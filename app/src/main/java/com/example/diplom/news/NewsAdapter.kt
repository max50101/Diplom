package com.example.diplom.news

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.games.GameLeagueHolder
import com.example.diplom.games.tempLeagueGame

class NewsAdapter(val mContext: Context, val mutableList: MutableList<RssItem>):
    RecyclerView.Adapter<NewsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val view: View = layoutInflater.inflate(R.layout.list_view_news, parent, false)
        return NewsHolder(mContext, view)

    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val task = mutableList.get(position)
        holder.bindTask(task, position)
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