package com.example.diplom.table.competionReview

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplom.R
import com.example.diplom.gameInfo.GameActivity
import com.example.diplom.models.game.Game
import com.example.diplom.models.standings.Standings
import com.example.diplom.table.temp
import com.example.diplom.table.tempCup
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson

class CupStandingHolder (val context: Context, val itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private lateinit var tour:TextView
    private lateinit var total:LinearLayout
    lateinit var results:RecyclerView

    override fun onClick(p0: View?) {

    }
    init{
        tour=itemView.findViewById(R.id.group_name)
        total=itemView.findViewById(R.id.total)
        results=itemView.findViewById(R.id.group_results)
        results.layoutManager=LinearLayoutManager(itemView.context)

    }
    fun bindTask(game: tempCup, position:Int) {
        if (game.mutableList == null || game.mutableList!!.isEmpty()) {
            val params = total.layoutParams as RecyclerView.LayoutParams
            params.height = 60
            total.layoutParams = params
            tour.visibility = View.VISIBLE
            results.visibility = View.GONE
            tour.text = game.groupName
        } else {
            val tempList= mutableListOf<Standings>()
            tempList.addAll(game.mutableList!!)
            for(i in tempList.indices){
                if(tempList.isEmpty()){
                    tempList.add(Standings(rank = "0"))
                }else {
                    tempList.add(0,Standings())
                    break
                }
            }
            val adapter= tempList.let { StandingAdapter(itemView.context, it,0) }
            adapter!!.setHasStableIds(true)
            results.adapter=adapter
        }
    }
}
