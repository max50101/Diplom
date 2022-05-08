package com.example.diplom.table.competionReview

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplom.R
import com.example.diplom.gameInfo.GameActivity
import com.example.diplom.models.game.Game
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson
import org.w3c.dom.Text

class PostHolder (val context: Context, val itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    lateinit var teamHomeLogo: ShapeableImageView
    lateinit var teamAwayLogo: ShapeableImageView
    lateinit var teamHomeName: TextView
    lateinit var teamAwayName: TextView
    lateinit var teamHomeGoalOne: TextView
    lateinit var teamAwayGoalOne: TextView
    lateinit var teamHomeGoalTwo: TextView
    lateinit var teamAwayGoalTwo: TextView
    lateinit var tour: TextView
    lateinit var results:LinearLayout
    lateinit var secondGame:LinearLayout
    lateinit var firstGame:LinearLayout
    lateinit var total:LinearLayout
    override fun onClick(p0: View?) {

    }
    init{
        teamHomeLogo=itemView.findViewById(R.id.first_team_image)
        teamAwayLogo=itemView.findViewById(R.id.second_team_image)
        teamHomeName=itemView.findViewById(R.id.first_team_name)
        teamAwayName=itemView.findViewById(R.id.second_team_name)
        teamHomeGoalOne=itemView.findViewById(R.id.first_team_goals_first)
        teamAwayGoalOne=itemView.findViewById(R.id.second_team_goals_first)
        teamHomeGoalTwo=itemView.findViewById(R.id.first_team_goals_second)
        teamAwayGoalTwo=itemView.findViewById(R.id.second_team_goals_second)
        tour=itemView.findViewById(R.id.tour)
        results=itemView.findViewById(R.id.result)
        firstGame=itemView.findViewById(R.id.first_game)
        secondGame=itemView.findViewById(R.id.second_game)
        total=itemView.findViewById(R.id.total)
    }
    fun bindTask(games:Array<Game>, position:Int){
        if(games[0].league!!.id==null){
            val params=total.layoutParams as RecyclerView.LayoutParams
            params.height=60
            total.layoutParams=params
            tour.visibility=View.VISIBLE
            results.visibility=View.GONE
            tour.text=games[0].league!!.round
        }else{
            teamHomeName.text= games[0]!!.teams?.get(0)!!.name
            teamAwayName.text=games[0]!!.teams?.get(1)!!.name
            teamHomeGoalOne.text=games[0].goalsGame!!.home
            teamAwayGoalOne.text=games[0].goalsGame!!.away
            if(games.size>1){
                val params=secondGame.layoutParams as LinearLayout.LayoutParams
                params.weight=0.2f
                secondGame.layoutParams=params
                val paramsOne=firstGame.layoutParams as LinearLayout.LayoutParams
                params.weight=0.2f
                firstGame.layoutParams=paramsOne
                secondGame.visibility=View.VISIBLE
                teamHomeGoalTwo.text=games[1].goalsGame!!.away
                teamAwayGoalTwo.text=games[1].goalsGame!!.home
                if(games[0].teams?.get(0)!!.winner!=null&& games[0].teams?.get(0)!!.winner == true&&games[1].teams?.get(1)!!.winner==true){
                    teamHomeName.setTypeface(Typeface.DEFAULT_BOLD)
                }else if(games[0].teams?.get(1)!!.winner!=null&&games[0].teams?.get(1)!!.winner==true&&games[1].teams?.get(0)!!.winner==true){
                    teamAwayName.setTypeface(Typeface.DEFAULT_BOLD)
                }else if(games[0].teams?.get(0)!!.winner!=null&& games[0].teams?.get(0)!!.winner == true&&games[1].teams?.get(0)!!.winner!=null&&games[1].teams?.get(0)!!.winner==true){
                    val totalGamesHome=games[0].goalsGame!!.home!!.toInt()+games[1].goalsGame!!.away!!.toInt()
                    val totalGamesAway=games[0].goalsGame!!.away!!.toInt()+games[1].goalsGame!!.home!!.toInt()
                    if(totalGamesHome>totalGamesAway){
                        teamHomeName.setTypeface(Typeface.DEFAULT_BOLD)
                    }else if (totalGamesHome<totalGamesAway){
                        teamAwayName.setTypeface(Typeface.DEFAULT_BOLD)
                    }else if(games[1].teams?.get(0)!!.winner!=null&&games[1].teams?.get(0)!!.winner==true){
                        teamAwayName.setTypeface(Typeface.DEFAULT_BOLD)
                    }else if(games[1].teams?.get(1)!!.winner!=null&&games[1].teams?.get(1)!!.winner==true){
                        teamHomeName.setTypeface(Typeface.DEFAULT_BOLD)
                    }
                }else if(games[0].teams?.get(1)!!.winner!=null&&games[0].teams?.get(1)!!.winner==true&&games[1].teams?.get(1)!!.winner!=null&&games[1].teams?.get(1)!!.winner==true){
                    val totalGamesHome=games[0].goalsGame!!.home!!.toInt()+games[1].goalsGame!!.away!!.toInt()
                    val totalGamesAway=games[0].goalsGame!!.away!!.toInt()+games[1].goalsGame!!.home!!.toInt()
                    if(totalGamesHome>totalGamesAway){
                        teamHomeName.setTypeface(Typeface.DEFAULT_BOLD)
                    }else if (totalGamesHome<totalGamesAway){
                        teamAwayName.setTypeface(Typeface.DEFAULT_BOLD)
                    }else if(games[1].teams?.get(0)!!.winner!=null&&games[1].teams?.get(0)!!.winner==true){
                        teamAwayName.setTypeface(Typeface.DEFAULT_BOLD)
                    }else if(games[1].teams?.get(1)!!.winner!=null&&games[1].teams?.get(1)!!.winner==true){
                        teamHomeName.setTypeface(Typeface.DEFAULT_BOLD)
                    }
                }else if(games[0].teams?.get(0)!!.winner!=null&&games[0].teams?.get(0)!!.winner==true&&games[1].teams?.get(1)!!.winner!=null&&games[1].teams?.get(1)!!.winner==true) {
                    val totalGamesHome=games[0].goalsGame!!.home!!.toInt()+games[1].goalsGame!!.away!!.toInt()
                    val totalGamesAway=games[0].goalsGame!!.away!!.toInt()+games[1].goalsGame!!.home!!.toInt()
                    if(totalGamesHome>totalGamesAway){
                        teamHomeName.setTypeface(Typeface.DEFAULT_BOLD)
                    }else if (totalGamesHome<totalGamesAway){
                        teamAwayName.setTypeface(Typeface.DEFAULT_BOLD)
                    }else if(games[1].teams?.get(0)!!.winner!=null&&games[1].teams?.get(0)!!.winner==true){
                        teamAwayName.setTypeface(Typeface.DEFAULT_BOLD)
                    }else if(games[1].teams?.get(1)!!.winner!=null&&games[1].teams?.get(1)!!.winner==true){
                        teamHomeName.setTypeface(Typeface.DEFAULT_BOLD)
                    }
                }else if(games[0].teams?.get(1)!!.winner!=null&&games[0].teams?.get(1)!!.winner==true&&games[1].teams?.get(0)!!.winner!=null&&games[1].teams?.get(0)!!.winner==true) {
                    val totalGamesHome=games[0].goalsGame!!.home!!.toInt()+games[1].goalsGame!!.away!!.toInt()
                    val totalGamesAway=games[0].goalsGame!!.away!!.toInt()+games[1].goalsGame!!.home!!.toInt()
                    if(totalGamesHome>totalGamesAway){
                        teamHomeName.setTypeface(Typeface.DEFAULT_BOLD)
                    }else if (totalGamesHome<totalGamesAway){
                        teamAwayName.setTypeface(Typeface.DEFAULT_BOLD)
                    }else if(games[1].teams?.get(0)!!.winner!=null&&games[1].teams?.get(0)!!.winner==true){
                        teamAwayName.setTypeface(Typeface.DEFAULT_BOLD)
                    }else if(games[1].teams?.get(1)!!.winner!=null&&games[1].teams?.get(1)!!.winner==true){
                        teamHomeName.setTypeface(Typeface.DEFAULT_BOLD)
                    }
                }else if(games[0].teams?.get(0)!!.winner==null&&games[1].teams?.get(1)!!.winner==true){
                    teamHomeName.setTypeface(Typeface.DEFAULT_BOLD)
                }else if(games[0].teams?.get(0)!!.winner==null&&games[1].teams?.get(0)!!.winner==true){
                    teamAwayName.setTypeface(Typeface.DEFAULT_BOLD)
                }else if(games[0].teams?.get(0)!!.winner==true&&games[1].teams?.get(0)!!.winner==null){
                    teamHomeName.setTypeface(Typeface.DEFAULT_BOLD)
                }else if(games[0].teams?.get(1)!!.winner==true&&games[1].teams?.get(0)!!.winner==null){
                    teamAwayName.setTypeface(Typeface.DEFAULT_BOLD)
                }
            }else{

                if(games[0].teams?.get(0)!!.winner!=null&& games[0].teams?.get(0)!!.winner == true){
                    teamHomeName.setTypeface(Typeface.DEFAULT_BOLD)
                }else if(games[0].teams?.get(1)!!.winner!=null&&games[0].teams?.get(1)!!.winner==true) {
                    teamAwayName.setTypeface(Typeface.DEFAULT_BOLD)
                }
            }
            Glide.with(context)
                .load(
                    games[0].teams?.get(0)!!.logo
                )
                .fitCenter()
                .into(teamHomeLogo)
            Glide.with(context)
                .load( games[0].teams?.get(1)!!.logo)
                .fitCenter()
                .into(teamAwayLogo)
        }

    }

}
