package com.example.diplom.playerinfo

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplom.R
import com.example.diplom.models.Player
import com.example.diplom.models.statistic.Statistics
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson

class PlayerStatisticHolder (val context: Context, val itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    lateinit var mPlayer: Statistics
    lateinit var mLogo: ShapeableImageView
    lateinit var mLeagueName:TextView
    lateinit var mAppearance: TextView
    lateinit var mMinutesPlayed:TextView
    lateinit var mPosition:TextView
    lateinit var mPasses: TextView
    lateinit var mCards:TextView
    lateinit var mShots:TextView
    lateinit var mRating:TextView
    lateinit var mGoals:TextView
    lateinit var mAsissts:TextView
    lateinit var mDuels:TextView
    lateinit var mDribbles:TextView
    lateinit var mPenatlies:TextView

    override fun onClick(p0: View?) {

    }
    init{
        itemView.setOnClickListener(this)
        mLogo=itemView.findViewById<ShapeableImageView>(R.id.league_logo)
        mLeagueName=itemView.findViewById(R.id.leaugue_name)
        mAppearance=itemView.findViewById(R.id.appearance)
        mMinutesPlayed=itemView.findViewById(R.id.minutes_plaied)
        mPosition=itemView.findViewById(R.id.position)
        mPasses=itemView.findViewById(R.id.passes)
        mCards=itemView.findViewById(R.id.cards)
        mShots=itemView.findViewById(R.id.shots)
        mRating=itemView.findViewById(R.id.rating)
        mGoals=itemView.findViewById(R.id.goals)
        mAsissts=itemView.findViewById(R.id.assist)
        mDuels=itemView.findViewById(R.id.duels)
        mDribbles=itemView.findViewById(R.id.dribbles)
        mPenatlies=itemView.findViewById(R.id.penalties)


    }
    fun bindTask(playerStatistic: Statistics, position:Int){
        mPlayer=playerStatistic
        Glide.with(context)
            .load(playerStatistic.league!!.logo)
            .fitCenter()
            .into(mLogo)
        mLeagueName.text=mLeagueName.text.toString()+playerStatistic.league!!.name
        mAppearance.text=mAppearance.text.toString()+playerStatistic.games!!.appearences.toString()
        mMinutesPlayed.text=mMinutesPlayed.text.toString()+playerStatistic.games!!.minutes.toString()
        mPosition.text=mPosition.text.toString()+playerStatistic.games!!.position
        mPasses.text=mPasses.text.toString()+playerStatistic.passes!!.total.toString().replace("null","0")+"/"+playerStatistic.passes!!.accuracy.toString().replace("null","0")+"%"
        mCards.text=mCards.text.toString()+playerStatistic.cardsGameStatistic!!.yellow.toString().replace("null","0")+"/"+playerStatistic.cardsGameStatistic!!.red.toString().replace("null","0")
        mShots.text=mShots.text.toString()+playerStatistic.shots!!.total.toString().replace("null","0")+"/"+playerStatistic.shots!!.on
        mRating.text=mRating.text.toString()+playerStatistic.games!!.rating.toString().replace("null","0")
        if(playerStatistic.games!!.position.equals("Goalkeeper")){
            mGoals.text=context.getString(R.string.saves)+playerStatistic.goal!!.conceded.toString().replace("null","0")+playerStatistic.goal!!.saves.toString().replace("null","0")
            mPenatlies.text=context.getString(R.string.penalties_saved)+playerStatistic.penaltyGameStatistic!!.saved.toString().replace("null","0")
        }else{
            mGoals.text=mGoals.text.toString()+playerStatistic.goal!!.total.toString().replace("null","0")
            mPenatlies.text=mPenatlies.text.toString()+playerStatistic.penaltyGameStatistic!!.scored.toString().replace("null","0")
        }
        mAsissts.text=mAsissts.text.toString()+playerStatistic.goal!!.assists.toString().replace("null","0")
        mDuels.text=mDuels.text.toString()+playerStatistic.duealsGameStatistic!!.total.toString().replace("null","0")+"/"+playerStatistic.duealsGameStatistic!!.won.toString().replace("null","0")
        mDribbles.text=mDribbles.text.toString()+playerStatistic.dribblesGameStatistic!!.attempts.toString().replace("null","0")+"/"+playerStatistic.dribblesGameStatistic!!.success.toString().replace("null","0")

    }
}
