package com.example.diplom.profile

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.icu.util.LocaleData
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplom.R
import com.example.diplom.gameInfo.GameActivity
import com.example.diplom.models.Player
import com.example.diplom.models.game.Game
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class GameHolder(val context: Context, val itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    lateinit var mGame:Game
    lateinit var leaguePhoto:ShapeableImageView
    lateinit var leagueName:TextView
    lateinit var timeStamp:TextView
    lateinit var teamHomeLogo:ShapeableImageView
    lateinit var teamAwayLogo:ShapeableImageView
    lateinit var teamHomeName:TextView
    lateinit var teamAwayName:TextView
    lateinit var teamHomeGoal:TextView
    lateinit var teamAwayGoal:TextView
    lateinit var textView6:TextView
    override fun onClick(p0: View?) {
        val intent= Intent(context,GameActivity::class.java)
        intent.putExtra("game", Gson().toJson(mGame))
        context.startActivity(intent)

    }
    init{
        itemView.setOnClickListener(this)
        leaguePhoto=itemView.findViewById(R.id.league_photo)
        leagueName=itemView.findViewById(R.id.league_name)
        timeStamp=itemView.findViewById(R.id.time_stamp)
        teamHomeLogo=itemView.findViewById(R.id.team_home_logo)
        teamAwayLogo=itemView.findViewById(R.id.team_away_logo)
        teamHomeName=itemView.findViewById(R.id.home_team_name)
        teamAwayName=itemView.findViewById(R.id.away_team_name)
        teamHomeGoal=itemView.findViewById(R.id.team_hone_goals)
        teamAwayGoal=itemView.findViewById(R.id.team_away_goals)
        textView6=itemView.findViewById(R.id.textView6)
    }
    fun bindTask(game: Game, position:Int){
        mGame=game
        leagueName.setText(mGame.league!!.name)
        val simpleDateFormat=SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ", Locale.getDefault())
        simpleDateFormat.timeZone= TimeZone.getTimeZone("GMT")
        val resultDate=SimpleDateFormat("dd/MM HH:mm",Locale.getDefault()).format(simpleDateFormat.parse(game.fixture!!.date))
        timeStamp.text=resultDate
        teamHomeName.text= mGame.teams?.get(0)!!.name
        teamAwayName.text= mGame.teams?.get(1)!!.name
        if(mGame.teams?.get(0)!!.winner!=null&& mGame.teams?.get(0)!!.winner == true){
            teamHomeName.setTypeface(Typeface.DEFAULT_BOLD)
        }else if(mGame.teams?.get(1)!!.winner!=null&&mGame.teams?.get(1)!!.winner==true){
            teamAwayName.setTypeface(Typeface.DEFAULT_BOLD)
        }
        teamHomeGoal.text=mGame.goalsGame!!.home
        teamAwayGoal.text=mGame.goalsGame!!.away

        Glide.with(context)
            .load(mGame.league!!.logo)
            .fitCenter()
            .into(leaguePhoto)
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
