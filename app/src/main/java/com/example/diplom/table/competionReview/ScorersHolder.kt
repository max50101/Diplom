package com.example.diplom.table.competionReview

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplom.R
import com.example.diplom.models.Player
import com.example.diplom.models.standings.Standings
import com.example.diplom.models.statistic.PlayerStatistic
import com.example.diplom.playerinfo.PlayerActivity
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson

class ScorersHolder (val context: Context, val itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    lateinit var rank: TextView
    lateinit var photo: ShapeableImageView
    lateinit var name: TextView
    lateinit var goals:TextView
    lateinit var assists:TextView
    lateinit var mPlayer:PlayerStatistic
    override fun onClick(p0: View?) {

    }
    init{
        rank=itemView.findViewById(R.id.rank)
        photo=itemView.findViewById(R.id.team_photo)
        name=itemView.findViewById(R.id.name)
        goals=itemView.findViewById(R.id.goals)
        assists=itemView.findViewById(R.id.assist)
    }
    fun bindTask(player: PlayerStatistic, position:Int){
        if(player.player!=null){
            mPlayer=player
            rank.text=player.rank
            name.text=player.player!!.name!!
            goals.text=player.statistic!!.get(0)!!.goal!!.total.toString()
            assists.text=player.statistic!!.get(0)!!.goal!!.assists.toString().replace("null","0")
            Glide.with(context)
                .load(player.player!!.photo)
                .fitCenter()
                .into(photo)
            name.setOnClickListener{
                val intent= Intent(context, PlayerActivity::class.java)
                val player= Player(id=mPlayer.player!!.id,name=mPlayer.player!!.name)
                intent.putExtra("player", Gson().toJson(player))
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        }else{
            rank.text="#"
            name.text=itemView.context.getString(R.string.player_name)
            goals.text=itemView.context.getString(R.string.goals)
            assists.text=itemView.context.getString(R.string.assists)
            GlideToVectorYou
                .init()
                .with(itemView.context)
                .load(Uri.parse("https://upload.wikimedia.org/wikipedia/commons/f/fc/Player_%28TVN%29.svg"), photo)
        }
    }
}
