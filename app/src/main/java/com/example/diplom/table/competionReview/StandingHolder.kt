package com.example.diplom.table.competionReview

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplom.R
import com.example.diplom.models.game.GameLeague
import com.example.diplom.models.standings.Standings
import com.google.android.material.imageview.ShapeableImageView

class StandingHolder  (val context: Context, val itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    lateinit var rank:TextView
    lateinit var photo:ShapeableImageView
    lateinit var name:TextView
    lateinit var games_played:TextView
    lateinit var wins:TextView
    lateinit var draw:TextView
    lateinit var looses:TextView
    lateinit var goalsDiff:TextView
    lateinit var points:TextView
    lateinit var infoTextView:TextView
    override fun onClick(p0: View?) {

    }
     init{
        rank=itemView.findViewById(R.id.rank)
        photo=itemView.findViewById(R.id.team_photo)
        name=itemView.findViewById(R.id.team_name)
        games_played=itemView.findViewById(R.id.games_playes)
        wins=itemView.findViewById(R.id.wins)
        draw=itemView.findViewById(R.id.draws)
        looses=itemView.findViewById(R.id.looses)
        goalsDiff=itemView.findViewById(R.id.goals_diff)
        points=itemView.findViewById(R.id.points)
        infoTextView=itemView.findViewById(R.id.info)
    }
    fun bindTask(standing: Standings, position:Int,isHome:Int){

        if(standing.rank==null) {
            rank.text = "#"
            name.text = itemView.context.getString(R.string.team_name)
            games_played.text = itemView.context.getString(R.string.GP)
            wins.text = itemView.context.getString(R.string.W)
            draw.text = itemView.context.getString(R.string.D)
            looses.text = itemView.context.getString(R.string.L)
            goalsDiff.text = itemView.context.getString(R.string.GD)
            points.text = itemView.context.getString(R.string.P)
            Glide.with(context)
                .load(
                    "data:image/jpeg;base64,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"
                )
                .fitCenter()
                .into(photo)
        }else if(standing.rank.equals("0")){
            infoTextView.visibility=View.VISIBLE
            infoTextView.text=itemView.context.getString(R.string.no_table)
        }else if(isHome==0){
            rank.text=standing.rank
            name.text=standing.team!!.name
            games_played.text=standing.all!!.played
            wins.text=standing.all!!.win
            draw.text=standing.all!!.draw
            looses.text=standing.all!!.loose
            goalsDiff.text=standing.all!!.goals!!.home+" : "+standing.all!!.goals!!.away
            points.text=standing.points
            Glide.with(context)
                .load(standing.team!!.logo)
                .fitCenter()
                .into(photo)
        }else if(isHome==1){
            rank.text=standing.rank
            name.text=standing.team!!.name
            games_played.text=standing.home!!.played
            wins.text=standing.home!!.win
            draw.text=standing.home!!.draw
            looses.text=standing.home!!.loose
            goalsDiff.text=standing.home!!.goals!!.home+" : "+standing.home!!.goals!!.away
            points.text=(standing!!.home!!.win!!.toInt()*3+standing!!.home!!.draw!!.toInt()*1).toString()
            Glide.with(context)
                .load(standing.team!!.logo)
                .fitCenter()
                .into(photo)
        }else if(isHome==2){
            rank.text=standing.rank
            name.text=standing.team!!.name
            games_played.text=standing.away!!.played
            wins.text=standing.away!!.win
            draw.text=standing.away!!.draw
            looses.text=standing.away!!.loose
            goalsDiff.text=standing.away!!.goals!!.home+" : "+standing.away!!.goals!!.away
            points.text=(standing!!.away!!.win!!.toInt()*3+standing!!.away!!.draw!!.toInt()*1).toString()
            Glide.with(context)
                .load(standing.team!!.logo)
                .fitCenter()
                .into(photo)
        }

    }
}
