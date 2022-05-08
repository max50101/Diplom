package com.example.diplom.table.competionReview

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplom.R
import com.example.diplom.gameInfo.GameActivity
import com.example.diplom.models.game.Game
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson

class ResultsHolder (val context: Context, val itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    lateinit var teamHomeLogo:ShapeableImageView
    lateinit var teamAwayLogo:ShapeableImageView
    lateinit var teamHomeName:TextView
    lateinit var teamAwayName:TextView
    lateinit var teamHomeGoal:TextView
    lateinit var teamAwayGoal:TextView
    lateinit var tour:TextView
    lateinit var cardView:CardView
    lateinit var mGame:Game
    override fun onClick(p0: View?) {

    }
    init{
        teamHomeLogo=itemView.findViewById(R.id.home_logo)
        teamAwayLogo=itemView.findViewById(R.id.away_logo)
        teamHomeName=itemView.findViewById(R.id.home_name)
        teamAwayName=itemView.findViewById(R.id.away_name)
        teamHomeGoal=itemView.findViewById(R.id.goals_home)
        teamAwayGoal=itemView.findViewById(R.id.goals_away)
        tour=itemView.findViewById(R.id.round)
        cardView=itemView.findViewById(R.id.card_view)
        cardView.setOnClickListener{
            val intent= Intent(context, GameActivity::class.java)
            intent.putExtra("game", Gson().toJson(mGame))
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
    fun bindTask(game: Game, position:Int){
        mGame=game
        if(game.league!!.id==null){
            tour.visibility=View.VISIBLE
            cardView.visibility=View.GONE
            tour.text=game.league!!.round
        }else{
            if(mGame.fixture!!.status!!.short.equals("NS")){
                cardView.setOnClickListener{

                }
            }
            teamHomeName.text= mGame.teams?.get(0)!!.name
            teamAwayName.text= mGame.teams?.get(1)!!.name
            if(mGame.teams?.get(0)!!.winner!=null&& mGame.teams?.get(0)!!.winner == true){
                teamHomeName.setTypeface(Typeface.DEFAULT_BOLD)
            }else if(mGame.teams?.get(1)!!.winner!=null&&mGame.teams?.get(1)!!.winner==true){
                teamAwayName.setTypeface(Typeface.DEFAULT_BOLD)
            }
            teamHomeGoal.text=mGame.goalsGame!!.away!!.replace("null","0")
            teamAwayGoal.text=mGame.goalsGame!!.home!!.replace("null","0")
            Glide.with(context)
                .load(mGame.teams?.get(0)!!.logo)
                .fitCenter()
                .into(teamHomeLogo)
            Glide.with(context)
                .load(mGame.teams?.get(1)!!.logo)
                .fitCenter()
                .into(teamAwayLogo)
        }
    }
}
