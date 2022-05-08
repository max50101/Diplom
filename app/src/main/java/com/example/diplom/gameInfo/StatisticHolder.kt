package com.example.diplom.gameInfo

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.models.game.Game
import com.example.diplom.models.game.GameStatisticForHolder
import com.example.diplom.models.game.GameTeam
import com.example.diplom.models.game.event.Event
import com.google.gson.Gson

class StatisticHolder  (val context: Context, val itemView: View, game: Game) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    lateinit var mGame: Game
    lateinit var mHomnStatistic: TextView
    lateinit var mAwayStatistic: TextView
    lateinit var mStatisticNane: TextView
    lateinit var mHomeTeam:GameTeam
    lateinit var mAwayTeam:GameTeam
    override fun onClick(p0: View?) {

    }

    init {
        mHomnStatistic=itemView.findViewById(R.id.home_statistic)
        mAwayStatistic=itemView.findViewById(R.id.away_statistic)
        mStatisticNane=itemView.findViewById(R.id.statistic_name)
        mHomeTeam= game.teams!!.get(0)
        mAwayTeam=game.teams!!.get(1)
    }

    fun bindTask(statistic: GameStatisticForHolder, position: Int) {
        mStatisticNane.text=statistic.name
        if(statistic.homeValue!=null) {
            mHomnStatistic.text = statistic.homeValue
        }else{
            mHomnStatistic.text="0"
        }
        if(statistic.awayValue!=null) {
            mAwayStatistic.text = statistic.awayValue
        }else{
            mAwayStatistic.text="0"
        }
    }
}
