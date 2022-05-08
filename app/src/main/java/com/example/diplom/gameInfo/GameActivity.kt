package com.example.diplom.gameInfo

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.diplom.R
import com.example.diplom.models.game.Game
import com.example.diplom.models.getGameInfo
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class GameActivity : AppCompatActivity() {
    lateinit var homeLogo:ShapeableImageView
    lateinit var awayLogo:ShapeableImageView
    lateinit var homeName:TextView
    lateinit var awayName:TextView
    lateinit var homeGoal:TextView
    lateinit var awayGoal:TextView
    lateinit var time:TextView
    lateinit var leagueName:TextView
    lateinit var elapsed:TextView
    lateinit var game:Game
    lateinit var overviewBtn:AppCompatButton
    lateinit var squadsBtn:AppCompatButton
    lateinit var statisticwBtn:AppCompatButton
    lateinit var gameResultObj:Game
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        init()
        overviewBtn=findViewById(R.id.overview_btn)
        squadsBtn=findViewById(R.id.squad_btn)
        statisticwBtn=findViewById(R.id.statistic_btn)
        var gameJson=intent.getStringExtra("game")
        var gameFromJson= Gson().fromJson(gameJson, Game::class.java)
        game=gameFromJson
        var gameResult=""
        GlobalScope.launch(Dispatchers.IO) {
            gameResultObj=getGameInfo(game)
            launch(Dispatchers.Main){
                homeName.text=gameResultObj.teams!!.get(0).name
                awayName.text=gameResultObj.teams!!.get(1).name
                if(gameResultObj.teams!!.get(0).winner == true){
                    homeName.setTypeface(Typeface.DEFAULT_BOLD)
                }else if(gameResultObj.teams!!.get(1).winner==true){
                    awayName.setTypeface(Typeface.DEFAULT_BOLD)
                }
                elapsed.text=gameResultObj.fixture!!.status!!.elapsed
                val simpleDateFormat= SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ", Locale.getDefault())
                simpleDateFormat.timeZone= TimeZone.getTimeZone("GMT")
                val resultDate= SimpleDateFormat("dd/MM HH:mm", Locale.getDefault()).format(simpleDateFormat.parse(gameResultObj.fixture!!.date))
                time.text=resultDate
                homeGoal.text=gameResultObj.goalsGame!!.home
                awayGoal.text=gameResultObj.goalsGame!!.away
                leagueName.text=gameResultObj.league!!.name


                Glide.with(applicationContext)
                    .load(gameResultObj.teams!!.get(0).logo)
                    .fitCenter()
                    .into(homeLogo)
                Glide.with(applicationContext)
                    .load(gameResultObj.teams!!.get(1).logo)
                    .fitCenter()
                    .into(awayLogo)
                 gameResult=Gson().toJson(gameResultObj)
                setCurrentFragment(OverviewFragment.newInstance(Gson().toJson(gameResultObj)))
            }
        }

        overviewBtn.setOnClickListener{
            setCurrentFragment(OverviewFragment.newInstance(gameResult))
        }
        statisticwBtn.setOnClickListener{
            setCurrentFragment(StatisticFragment.newInstance(gameResult))
        }
        squadsBtn.setOnClickListener{
            setCurrentFragment(SquadFragment.newInstance(gameResult))
        }

    }
    fun init(){
        homeLogo=findViewById(R.id.homeLogo)
        awayLogo=findViewById(R.id.awayLogo)
        homeName=findViewById(R.id.home_name)
        awayName=findViewById(R.id.away_name)
        leagueName=findViewById(R.id.league_name)
        time=findViewById(R.id.time)
        elapsed=findViewById(R.id.elapsed)
        homeGoal=findViewById(R.id.home_goal)
        awayGoal=findViewById(R.id.away_goal)
    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentlayout,fragment)
            commit()
        }
}