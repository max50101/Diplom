package com.example.diplom.games

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplom.R
import com.example.diplom.gameInfo.GameActivity
import com.example.diplom.models.game.Game
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class CountryLeagueHolder (val context: Context, val itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    lateinit var cardView: CardView
    lateinit var league:LinearLayout
    lateinit var homeLogo:ShapeableImageView
    lateinit var awayLogo:ShapeableImageView
    lateinit var homeGoals:TextView
    lateinit var awayGoals:TextView
    lateinit var date:TextView
    lateinit var homeName:TextView
    lateinit var awayName:TextView
    lateinit var leagueName:TextView
    lateinit var leaguePhoto:ShapeableImageView
    lateinit var total:LinearLayout
    var mposition=0
    override fun onClick(p0: View?) {

    }
    init{
        itemView.setOnClickListener(this)
        cardView=itemView.findViewById(R.id.card_view)
        league=itemView.findViewById(R.id.league)
        homeLogo=itemView.findViewById(R.id.home_logo)
        awayLogo=itemView.findViewById(R.id.away_logo)
        homeName=itemView.findViewById(R.id.home_team_name)
        awayName=itemView.findViewById(R.id.away_team_name)
        homeGoals=itemView.findViewById(R.id.home_team_goals)
        date=itemView.findViewById(R.id.date)
        awayGoals=itemView.findViewById(R.id.away_team_goals)
        total=itemView.findViewById(R.id.total)
        leagueName=itemView.findViewById(R.id.league_name)
        leaguePhoto=itemView.findViewById(R.id.league_photo)
    }
    fun bindTask(game: Game, position:Int){
        if(game.fixture==null){
            val params=total.layoutParams as RecyclerView.LayoutParams
            params.height=60
            total.layoutParams=params
            league.visibility=View.VISIBLE
            cardView.visibility=View.GONE
            leagueName.text=game.league!!.name
            Glide.with(context)
                .load(
                    game.league!!.logo
                )
                .fitCenter()
                .into(leaguePhoto)
        }
        else{
            homeName.text= game.teams?.get(0)!!.name
            awayName.text=game.teams?.get(1)!!.name
            homeGoals.text=game.goalsGame!!.home!!.replace("null","0")
            awayGoals.text=game.goalsGame!!.away!!.replace("null","0")
            val simpleDateFormat= SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ", Locale.getDefault())
            simpleDateFormat.timeZone= TimeZone.getTimeZone("GMT")
            val resultDate= SimpleDateFormat("HH:mm", Locale.getDefault()).format(simpleDateFormat.parse(game.fixture!!.date))
            date.text=resultDate
            Glide.with(context)
                .load(game.teams!!.get(0).logo)
                .fitCenter()
                .into(homeLogo)
            Glide.with(context)
                .load(game.teams!!.get(1).logo)
                .fitCenter()
                .into(awayLogo)
            if(!game!!.fixture!!.status!!.short.equals("NS")){
                cardView.setOnClickListener {
                    val intent = Intent(context, GameActivity::class.java)
                    intent.putExtra("game", Gson().toJson(game))
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            }
        }

    }
}
