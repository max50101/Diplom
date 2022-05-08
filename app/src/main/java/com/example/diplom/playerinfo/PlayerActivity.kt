package com.example.diplom.playerinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplom.R
import com.example.diplom.models.Player
import com.example.diplom.models.getPlayerStatistic
import com.example.diplom.models.statistic.Statistics
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayerActivity : AppCompatActivity() {
    lateinit var playerPhoto:ShapeableImageView
    lateinit var playerName:TextView
    lateinit var age:TextView
    lateinit var nationality:TextView
    lateinit var height:TextView
    lateinit var weight:TextView
    lateinit var games:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        init()
        val playerSt= Gson().fromJson(intent.getStringExtra("player"), Player::class.java)
       GlobalScope.launch(Dispatchers.IO) {
            val playerStatistic=playerSt.id?.let { getPlayerStatistic(it) }
            val mutableList= mutableListOf<Statistics>()
           if (playerStatistic != null) {
               for(static in playerStatistic.statistic!!){
                if(!(static.games?.appearences ==0)&&!(static.games?.appearences==null)&&!(static.league!!.name.equals("Friendlies"))&&!(static.league!!.name.equals("Club Friendlies"))){
                    mutableList.add(static)
                }
               }
           }
            launch(Dispatchers.Main) {
                val adapter=PlayerStatisticAdapter(applicationContext,mutableList)

                games.adapter=adapter
                if (playerStatistic != null) {
                    playerName.text=playerStatistic.player!!.name
                }
                if (playerStatistic != null) {
                    age.text=playerStatistic.player!!.age.toString()
                }
                if (playerStatistic != null) {
                    nationality.text=playerStatistic.player!!.nationality
                }
                if (playerStatistic != null) {
                    height.text=playerStatistic.player!!.height
                }
                if (playerStatistic != null) {
                    weight.text=playerStatistic.player!!.weight
                }
                if (playerStatistic != null) {
                    Glide.with(applicationContext)
                        .load(playerStatistic.player!!.photo)
                        .fitCenter()
                        .into(playerPhoto)
                }
            }
        }
    }
    fun init(){
        playerPhoto=findViewById(R.id.shapeableImageView)
        playerName=findViewById(R.id.player_name)
        age=findViewById(R.id.age)
        nationality=findViewById(R.id.player_nationality)
        height=findViewById(R.id.player_height)
        weight=findViewById(R.id.player_weight)
        games=findViewById(R.id.list_attandance)
        games.layoutManager= LinearLayoutManager(applicationContext)
    }
}