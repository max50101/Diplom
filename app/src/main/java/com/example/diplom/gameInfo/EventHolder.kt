package com.example.diplom.gameInfo

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplom.R
import com.example.diplom.models.game.Game
import com.example.diplom.models.game.GameTeam
import com.example.diplom.models.game.event.Event
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import android.R.attr

import android.widget.RelativeLayout

import android.R.attr.button




class EventHolder (val context: Context, val itemView: View, game:Game) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    lateinit var mGame: Game
    lateinit var mTextView:TextView
    lateinit var homeTeam:GameTeam
    lateinit var awayTeam:GameTeam
    override fun onClick(p0: View?) {

    }

    init {
        mTextView=itemView.findViewById(R.id.event_textview)
        homeTeam= game.teams!!.get(0)
        awayTeam=game.teams!!.get(1)
    }

    fun bindTask(event: Event, position: Int) {
        if(event.gameTeam!!.name.equals(homeTeam.name)){
            val params = mTextView.layoutParams as RelativeLayout.LayoutParams
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT)

            mTextView.layoutParams=params
        }else {
            val params = mTextView.layoutParams as RelativeLayout.LayoutParams
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)

            mTextView.layoutParams = params
        }
        when(event.type){
            "Goal"->{
                mTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_goal, 0, 0, 0);
                if(event.assistEvent!!.name!=null){
                    mTextView.text=event.eventPlayer!!.name+" ("+event.assistEvent.name+")"+" "+event.time.elapsed
                }else{
                    mTextView.text=event.eventPlayer!!.name+" "+event.time.elapsed
                }
            }
            "Card"->{
                when(event.detail){
                    "Yellow Card"->{
                        mTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_yellow_card, 0, 0, 0);
                        mTextView.text=event.eventPlayer!!.name+" "+event.time.elapsed
                    }
                    "Red Card"->{
                        mTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_red_card, 0, 0, 0);
                        mTextView.text=event.eventPlayer!!.name+" "+event.time.elapsed
                    }
                }
            }
            "subst"->{
                mTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_sub, 0, 0, 0);
                if(event.assistEvent!!.name!=null){
                    mTextView.text=event.eventPlayer!!.name+" ("+event.assistEvent.name+")"+" "+event.time.elapsed
                }else{
                    mTextView.text=event.eventPlayer!!.name+" "+event.time.elapsed
                }
            }
            "Var"->{
                mTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_var, 0, 0, 0);
                mTextView.text=context.getString(R.string.varcheck)+"- "+event.eventPlayer!!.name+" ("+event.detail+") "+event.time.elapsed
            }
        }
    }
}
