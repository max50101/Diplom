package com.example.diplom.table

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.diplom.models.CountryLeague
import com.example.diplom.models.Player
import com.example.diplom.playerinfo.PlayerActivity
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson
import android.net.Uri



import com.bumptech.glide.load.model.StreamEncoder

import android.graphics.drawable.PictureDrawable
import com.example.diplom.R
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

import java.io.InputStream




class LeagueHolder (val context: Context, val itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    lateinit var countryName:TextView
    lateinit var countryPhoto: ShapeableImageView
    lateinit var countryLeagues:RecyclerView
    override fun onClick(p0: View?) {

    }
    init{
        countryName=itemView.findViewById(R.id.countryName)
        countryPhoto=itemView.findViewById(R.id.countryImage)
        countryLeagues=itemView.findViewById(R.id.country_leagues)
        countryLeagues.layoutManager=LinearLayoutManager(itemView.context)
    }
    fun bindTask(countryLeague: CountryLeague, position:Int){

        countryName.text=countryLeague.countryName
//        Glide.with(context)
//            .load(countryLeague.leagueList.get(0).countryPhoto)
//            .fitCenter()
//            .into(countryPhoto)
        GlideToVectorYou
            .init()
            .with(itemView.context)
            .load(Uri.parse(countryLeague.leagueList.get(0).countryPhoto), countryPhoto)
        countryLeagues.adapter=CompetinionAdapter(itemView.context,countryLeague.leagueList)

    }
}
