package com.example.diplom.games

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.models.game.Game
import com.example.diplom.models.game.GameLeague
import com.google.gson.Gson

class CountryLeagueGamesActivity : AppCompatActivity() {
    lateinit var games:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_league_games)
        games=findViewById(R.id.games)
        val intent= Gson().fromJson(intent.getStringExtra("games"),tempGame::class.java)
        var gameList=intent.list
        val secondListAttempt= mutableListOf<Game>()
        var currentLeague=""
        for(i in gameList!!.indices){
              if(!gameList[i].league!!.name.equals(currentLeague)){
                currentLeague= gameList[i].league!!.name.toString()
                var league= GameLeague(name = currentLeague,logo = gameList[i].league!!.logo)
                secondListAttempt.add(Game(league = league))
            }
            secondListAttempt.add(gameList[i])
            if(secondListAttempt.isEmpty()){
                var league= GameLeague(round = applicationContext.getString(R.string.no_results_league))
                secondListAttempt.add((Game(league = league)))
            }
        }
        gameList=secondListAttempt
        games.layoutManager=LinearLayoutManager(applicationContext)
        val adapter=CountryLeagueAdapter(applicationContext,gameList)
        games.adapter=adapter
    }
}