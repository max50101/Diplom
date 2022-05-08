package com.example.diplom.table

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplom.R
import com.example.diplom.models.CountryLeague
import com.example.diplom.models.game.GameLeague
import com.example.diplom.table.competionReview.StandingActivity
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson

class CompetitionHolder (val context: Context, val itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    lateinit var leaguePhoto:ShapeableImageView
    lateinit var leagueName:TextView
    lateinit var mCountryLeague: GameLeague
    lateinit var cardView:CardView
    override fun onClick(p0: View?) {

    }
    init{
        leaguePhoto=itemView.findViewById(R.id.league_photo)
        leagueName=itemView.findViewById(R.id.league_name)
        cardView=itemView.findViewById(R.id.card_view)
        cardView.setOnClickListener{
            val intent=Intent(itemView.context,StandingActivity::class.java)
            intent.putExtra("league", Gson().toJson(mCountryLeague))
            itemView.context.startActivity(intent)
        }
    }
    fun bindTask(countryLeague: GameLeague, position:Int){
        mCountryLeague=countryLeague
        leagueName.text=countryLeague.name!!
        Glide.with(context)
            .load(countryLeague.logo)
            .fitCenter()
            .into(leaguePhoto)


    }
}
