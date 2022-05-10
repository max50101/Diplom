package com.example.diplom.table.competionReview

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplom.R
import com.example.diplom.models.game.Game
import com.example.diplom.models.game.GameLeague
import com.example.diplom.models.standings.Standings
import com.example.diplom.models.statistic.PlayerStatistic
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatSpinner
import com.example.diplom.models.*
import com.example.diplom.table.tempCup


class StandingActivity : AppCompatActivity() {
    private lateinit var countryPhoto:ShapeableImageView
    private lateinit var leaguePhoto:ShapeableImageView
    private lateinit var leagueName:TextView
    private lateinit var tableList:RecyclerView
    private lateinit var table:AppCompatButton
    private lateinit var scorrers:AppCompatButton
    private lateinit var home:AppCompatButton
    private lateinit var away:AppCompatButton
    private lateinit var calendar:AppCompatButton
    private lateinit var results:AppCompatButton
    private lateinit var listGl:MutableList<Standings>
    private lateinit var mGameLeague:GameLeague
    private lateinit var scorrersL:MutableList<PlayerStatistic>
    private lateinit var resultList:MutableList<Game>
    private lateinit var calendarList:MutableList<Game>
    private lateinit var spinnerSeasons:AppCompatSpinner
    private lateinit var cupList:MutableList<Game>
    private lateinit var cupFinalList:MutableList<Array<Game>>
    private lateinit var cupStandingList:MutableList<tempCup>
    private lateinit var spinner_play_off:AppCompatSpinner
    var playOff="League"
    var currentSeason=2021
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standing)
        val gameLeague= Gson().fromJson(intent.getStringExtra("league"),GameLeague::class.java)
        mGameLeague=gameLeague
        init()
        listGl= mutableListOf<Standings>()
        scorrersL= mutableListOf()
        resultList= mutableListOf()
        calendarList= mutableListOf()
        cupList= mutableListOf()
        cupStandingList= mutableListOf()
        if(mGameLeague.type.equals("League")){
            home.visibility=View.VISIBLE
            away.visibility=View.VISIBLE
        }
        if(mGameLeague.type.equals("League-Cup")){
            spinner_play_off.visibility=View.VISIBLE
            spinner_play_off.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    spinner_play_off.setSelection(p2)
                    playOff=p0!!.getItemAtPosition(p2).toString()
                    if(this@StandingActivity::cupFinalList.isInitialized){
                        cupFinalList.clear()
                    }
                    if(this@StandingActivity::cupList.isInitialized){
                        cupList.clear()
                    }
                    if(this@StandingActivity::cupStandingList.isInitialized){
                        cupStandingList.clear()
                    }
                    initStanding()

                }
            }
        }
        if(gameLeague.countryPhoto!=null) {
            GlideToVectorYou
                .init()
                .with(applicationContext)
                .load(Uri.parse(gameLeague.countryPhoto), countryPhoto)
        }
        Glide.with(applicationContext)
            .load(gameLeague.logo)
            .fitCenter()
            .into(leaguePhoto)
        leagueName.text=gameLeague.name
        spinnerSeasons.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
               spinnerSeasons.setSelection(p2)
               currentSeason=p0!!.getItemAtPosition(p2).toString().toInt()
                if(this@StandingActivity::cupFinalList.isInitialized){
                    cupFinalList.clear()
                }
                if(this@StandingActivity::cupList.isInitialized){
                    cupList.clear()
                }
                if(this@StandingActivity::cupStandingList.isInitialized){
                    cupStandingList.clear()
                }
                initStanding()
                if(this@StandingActivity::resultList.isInitialized) {
                    resultList.clear()
                }
                if(this@StandingActivity::calendarList.isInitialized) {
                    calendarList.clear()
                }
                if(this@StandingActivity::scorrersL.isInitialized){
                    scorrersL.clear()
                }
            }
        }
        table.setOnClickListener{
            if(mGameLeague.type.equals("League")) {
                tableList.adapter = StandingAdapter(applicationContext, listGl, 0)
            }else if(mGameLeague.type.equals("Cup")){
                tableList.adapter=PostAdapter(applicationContext,cupFinalList)
            }else if(mGameLeague.type.equals("League-Cup")){
                if(playOff.equals("cup")) {
                    tableList.adapter = CupStandingAdapter(applicationContext, cupStandingList)
                }else{
                    tableList.adapter=PostAdapter(applicationContext,cupFinalList)
                }
            }
        }
        home.setOnClickListener{
            tableList.adapter=StandingAdapter(applicationContext,listGl,1)
        }
        away.setOnClickListener{
            tableList.adapter=StandingAdapter(applicationContext,listGl,2)
        }
        scorrers.setOnClickListener{it->setScorrers(it) }
        results.setOnClickListener{it->setResults(it)}
        calendar.setOnClickListener{it->setCalendar(it)}
    }
    fun initStanding(){
        if(mGameLeague.type.equals("League")) {
            GlobalScope.launch(Dispatchers.IO) {
                val list = getStanding(mGameLeague.id!!.toInt(), currentSeason)
                listGl = list
                launch(Dispatchers.Main) {
                    if (list.isEmpty()) {
                        list.add(0, Standings(rank = "0"))
                    } else {
                        list.add(0, Standings())
                    }
                    tableList.adapter = StandingAdapter(applicationContext, list, 0)
                }
            }
        }else if(mGameLeague.type.equals("Cup")){
            getCupPlayOff()
        }else if(mGameLeague.type.equals("League-Cup")){
            if(playOff.equals("League")){
                getStandingsCup()
            }else{
                getCupPlayOff()
            }

        }
    }

    fun init(){
        countryPhoto=findViewById(R.id.county_photo)
        leaguePhoto=findViewById(R.id.league_photo)
        leagueName=findViewById(R.id.league_name)
        tableList=findViewById(R.id.table_list)
        tableList.layoutManager=LinearLayoutManager(applicationContext)
        table=findViewById(R.id.table)
        scorrers=findViewById(R.id.scorers)
        home=findViewById(R.id.home)
        away=findViewById(R.id.away)
        calendar=findViewById(R.id.calendar)
        results=findViewById(R.id.results)
        spinnerSeasons=findViewById(R.id.spinner_season)
        if(mGameLeague.name.equals("Africa Cup of Nations")){
            val adapter = ArrayAdapter.createFromResource(
                this,
                com.example.diplom.R.array.africa,
                android.R.layout.simple_spinner_dropdown_item
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSeasons.adapter=adapter
        }else if(mGameLeague.name.equals("Copa America")){
            val adapter = ArrayAdapter.createFromResource(
                this,
                com.example.diplom.R.array.america,
                android.R.layout.simple_spinner_dropdown_item
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSeasons.adapter=adapter
        }else if(mGameLeague.name.equals("World Cup")){
            val adapter = ArrayAdapter.createFromResource(
                this,
                com.example.diplom.R.array.word_cup,
                android.R.layout.simple_spinner_dropdown_item
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSeasons.adapter=adapter
        }else if(mGameLeague.name.equals("Euro Championship")){
            val adapter = ArrayAdapter.createFromResource(
                this,
                com.example.diplom.R.array.euro,
                android.R.layout.simple_spinner_dropdown_item
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSeasons.adapter=adapter
        }else{
            val adapter = ArrayAdapter.createFromResource(
                this,
                com.example.diplom.R.array.season,
                android.R.layout.simple_spinner_dropdown_item
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSeasons.adapter=adapter
        }


        spinner_play_off=findViewById(R.id.league_cup)
        val adapterPlayOff=ArrayAdapter.createFromResource(
            this,
            R.array.league_cup,
            android.R.layout.simple_spinner_dropdown_item
        )
        adapterPlayOff.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_play_off.adapter=adapterPlayOff

    }
    fun setResults(it: View){
        if(resultList.isEmpty()){
            GlobalScope.launch(Dispatchers.IO) {
                resultList= getGameByTours(mGameLeague.id!!.toInt(),currentSeason)
                val secondListAttempt= mutableListOf<Game>()
                var currentRound=""
                for(i in resultList.indices.reversed()){
                    if(resultList.get(i).fixture!!.status!!.short.equals("NS")){
                        continue
                    }
                    if(!resultList.get(i).league!!.round.equals(currentRound)){
                        currentRound= resultList.get(i).league!!.round.toString()
                        var league=GameLeague(round = currentRound)
                        secondListAttempt.add(Game(league = league))
                    }
                    secondListAttempt.add(resultList.get(i))
                    if(secondListAttempt.isEmpty()){
                        var league=GameLeague(round = applicationContext.getString(R.string.no_results_league))
                        secondListAttempt.add(Game(league = league))
                    }
                }
                resultList=secondListAttempt
                launch(Dispatchers.Main) {
                    tableList.adapter = ResultsAdapter(applicationContext, resultList)
                }
            }
        }else{
            tableList.adapter = ResultsAdapter(applicationContext, resultList)
        }
    }
    fun setCalendar(view:View){
        if(calendarList.isEmpty()){
            GlobalScope.launch(Dispatchers.IO) {
                calendarList= getGameByToursNotStarted(mGameLeague.id!!.toInt(),currentSeason)
                val secondListAttempt= mutableListOf<Game>()
                var currentRound=""
                for(i in calendarList.indices){
                    if(calendarList.get(i).fixture!!.status!!.short.equals("FT")){
                        continue
                    }
                    if(!calendarList.get(i).league!!.round.equals(currentRound)){
                        currentRound= calendarList.get(i).league!!.round.toString()
                        var league=GameLeague(round = currentRound)
                        secondListAttempt.add(Game(league = league))
                    }
                    secondListAttempt.add(calendarList.get(i))

                }
                if(secondListAttempt.isEmpty()){
                    var league=GameLeague(round = applicationContext.getString(R.string.no_next_matches))
                    secondListAttempt.add(Game(league = league))
                }
                calendarList=secondListAttempt
                launch(Dispatchers.Main) {
                    tableList.adapter = ResultsAdapter(applicationContext, calendarList)
                }
            }
        }else{
            tableList.adapter = ResultsAdapter(applicationContext, calendarList)
        }
    }

    fun setScorrers(it: View){
        if(scorrersL.isEmpty()){
            GlobalScope.launch(Dispatchers.IO) {
                scorrersL= getTopScorrect(mGameLeague.id!!.toInt(),currentSeason)
                for(i in 0 until scorrersL.size){
                    scorrersL.get(i).rank= (i+1).toString()
                }
                scorrersL.add(0,PlayerStatistic())
                launch(Dispatchers.Main) {
                    tableList.adapter = ScorersAdapter(applicationContext, scorrersL)
                }
            }
        }else{
            tableList.adapter=ScorersAdapter(applicationContext,scorrersL)
        }
    }
    fun getCupPlayOff(){
        if(cupList.isEmpty()){
            GlobalScope.launch(Dispatchers.IO) {
              cupList= getGameByTours(mGameLeague.id!!.toInt(),currentSeason)
             //   cupList= getGameByTours(2,currentSeason)
                var definedList= mutableListOf<Array<Game>>()
                val tempList= mutableListOf<Game>()
                for(i in cupList.indices){
                    val elem=cupList.get(i)
                    if(!elem.league!!.round!!.contains("Group Stage")){
                        tempList.add(elem)
                    }
                }
                cupList=tempList
                val tempListIt= mutableListOf<Game>()
                for(i in cupList.indices) {
                    var tempArray = mutableListOf<Game>()
                    val elem = cupList.get(i)
                    if (!tempListIt.contains(elem)) {
                        tempArray.add(elem)

                        for (t in i until cupList.size) {
                            val elemEnemy = cupList.get(t)
                            if (elem.teams!!.get(0).name!!.equals(elemEnemy.teams!!.get(1).name) && elem.teams!!.get(1).name.equals(elemEnemy.teams!!.get(0).name)
                            ) {
                                tempArray.add(elemEnemy)
                                tempListIt.add(elemEnemy)
                                break
                            }
                            if (t > i + 50) {
                                break
                            }
                        }
                        definedList.add(tempArray.toTypedArray())
                    }
                }
                val secondListAttempt= mutableListOf<Array<Game>>()
                    var currentRound=""
                    for(i in definedList.indices.reversed()){
                        if(definedList[i].get(0).fixture!!.status!!.short.equals("NS")){
                            continue
                        }
                        if(!definedList[i].get(0).league!!.round.equals(currentRound)){
                            currentRound= definedList[i].get(0).league!!.round.toString()
                            var league=GameLeague(round = currentRound)
                            secondListAttempt.add(arrayOf(Game(league = league)))
                        }
                        secondListAttempt.add(definedList.get(i))
                        if(secondListAttempt.isEmpty()){
                            var league=GameLeague(round = applicationContext.getString(R.string.no_results_league))
                            secondListAttempt.add(arrayOf(Game(league = league)))
                        }
                    }
                    definedList=secondListAttempt
                    cupFinalList=definedList
                    launch(Dispatchers.Main) {
                        tableList.adapter = PostAdapter(applicationContext, definedList)
                    }
                }

            } else{
            tableList.adapter=PostAdapter(applicationContext,cupFinalList)
        }
    }
    fun getStandingsCup(){
        if(cupStandingList.isEmpty()){
            GlobalScope.launch(Dispatchers.IO) {
                val cupStandingListFirst= getDifficultStanding(mGameLeague.id!!.toInt(),currentSeason)
                var currentGrou=""
                for(i in 0 until cupStandingListFirst.size){
                    for(t in 0 until cupStandingListFirst.get(i).size){
                        if(!cupStandingListFirst.get(i).get(t).group.equals(currentGrou)){
                            currentGrou= cupStandingListFirst.get(i).get(t).group.toString()
                            cupStandingList.add(tempCup(groupName = currentGrou))
                            break
                        }
                    }
                    cupStandingList.add(tempCup(cupStandingListFirst.get(i)))
                }


                launch(Dispatchers.Main){
                    val adapter=CupStandingAdapter(applicationContext,cupStandingList)

                    tableList.adapter=adapter
                }
            }
        }else{
            tableList.adapter=CupStandingAdapter(applicationContext,cupStandingList)
        }
    }
}