package com.example.diplom.gameInfo

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.models.Player
import com.example.diplom.models.game.Game
import com.example.diplom.models.game.GamePlayer
import com.example.diplom.models.game.GameStatisticForHolder
import com.example.diplom.models.game.GameTeam
import com.example.diplom.playerinfo.PlayerActivity
import com.google.gson.Gson

class SquadHolder (val context: Context, val itemView: View, val game: Game,val isHome:Boolean) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    lateinit var mGame: Game
    lateinit var mName:TextView
    lateinit var mPlayer:GamePlayer
    override fun onClick(p0: View?) {
        val intent= Intent(context,PlayerActivity::class.java)
        val player= Player(id=mPlayer.id,name=mPlayer.name)
        intent.putExtra("player",Gson().toJson(player))
        context.startActivity(intent)
    }

    init {
        mName=itemView.findViewById(R.id.player_name)
        mName.setOnClickListener{
            val intent= Intent(context,PlayerActivity::class.java)
            val player= Player(id=mPlayer.id,name=mPlayer.name)
            intent.putExtra("player",Gson().toJson(player))
            context.startActivity(intent)
        }
    }

    fun bindTask(player: GamePlayer, position: Int) {
        mPlayer=player
        if(isHome) {
            val params = mName.layoutParams as RelativeLayout.LayoutParams
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
            mName.layoutParams = params
        }
        else {
            val params = mName.layoutParams as RelativeLayout.LayoutParams
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
            mName.layoutParams = params
        }
        mName.text=player.name

    }
}
