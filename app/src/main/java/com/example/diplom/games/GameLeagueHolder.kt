package com.example.diplom.games

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplom.R
import com.example.diplom.models.Player
import com.example.diplom.playerinfo.PlayerActivity
import com.example.diplom.profile.PlayerHolder
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson

class GameLeagueHolder  (val context: Context, val itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    lateinit var mPlayer: Player
    lateinit var mPhoto: ShapeableImageView
    lateinit var mCountry: TextView
    lateinit var mNumber: TextView


    var mposition=0
    override fun onClick(p0: View?) {

    }
    init{
        itemView.setOnClickListener(this)
        mPhoto=itemView.findViewById<ShapeableImageView>(R.id.league_photo)
        mCountry=itemView.findViewById(R.id.country_name)
        mNumber=itemView.findViewById(R.id.games_count)

    }
    fun bindTask(gameLeague: tempLeagueGame, position:Int){
        mCountry.text=gameLeague.country
        mNumber.text= gameLeague.mutableList?.size.toString()
        if(gameLeague.mutableList!!.get(0).league!!.flag!=null){
            GlideToVectorYou
                .init()
                .with(itemView.context)
                .load(Uri.parse(gameLeague.mutableList?.get(0)!!.league!!.flag), mPhoto)
        }
        mCountry.setOnClickListener{
            val intent=Intent(itemView.context,CountryLeagueGamesActivity::class.java)
            val temp=tempGame(gameLeague.mutableList)
            intent.putExtra("games",Gson().toJson(temp))
            itemView.context.startActivity(intent)
        }

    }
}
