package com.example.diplom.news

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplom.R
import com.example.diplom.models.Team
import com.example.diplom.teamChoose.TeamHolder
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class NewsHolder (val context: Context, val itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
   lateinit var mImage:ImageView
   lateinit var mDate:TextView
   lateinit var mTitle:TextView
   lateinit var mNews:RssItem
    var mposition=0
    override fun onClick(p0: View?) {
        val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse(mNews!!.link)
        itemView.context.startActivity(openURL)

    }
    init{
        itemView.setOnClickListener(this)
        mImage=itemView.findViewById(R.id.news_image)
        mDate=itemView.findViewById(R.id.date_news)
        mTitle=itemView.findViewById(R.id.title_news)
    }
    fun bindTask(news: RssItem, position:Int){
        mNews=news
        mposition=position
        mTitle.text=news.title
        val formatter =  SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH)
        val date=formatter.parse(news.pubDate)
        val formatterEndDate=SimpleDateFormat("HH:mm, dd/MM")
        mDate.text=formatterEndDate.format(date)
        if(news.image!=null){
            Glide.with(context)
                .load(news.image)
                .fitCenter()
                .into(mImage)
        }else if(news.link!!.contains("www.sport-express")){
            Glide.with(context)
                .load("https://www.druzhininracing.com/img/photo/photologue/photos/SE_big.png")
                .fitCenter()
                .into(mImage)
        }else if(news.link!!.contains("euro-football")){
            Glide.with(context)
                .load("https://www.euro-football.ru/images/og_image.png")
                .fitCenter()
                .into(mImage)
        }

    }

}