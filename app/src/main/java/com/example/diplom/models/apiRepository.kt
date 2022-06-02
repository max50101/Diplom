package com.example.diplom.models

import android.app.Dialog
import android.content.Context
import android.widget.TextView
import com.example.diplom.models.game.*
import com.example.diplom.models.game.event.Event
import com.example.diplom.models.game.event.EventPlayer
import com.example.diplom.models.game.event.Time
import com.example.diplom.models.game.score.Results
import com.example.diplom.models.game.score.Score
import com.example.diplom.models.standings.Standings
import com.example.diplom.models.standings.StandingsStatistic
import com.example.diplom.models.statistic.*
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun addLeaguesToFirebase(){
    val gson=Gson()
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://api-football-v1.p.rapidapi.com/v3/leagues?id=6")
        .get()
        .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
        .addHeader("X-RapidAPI-Key", "2189b0cc44msh3d61c8e39f613b8p12ec99jsn820e38f51e4e")
        .build()

    val response = client.newCall(request).execute().body!!.string()
    val firebaseDatabase=FirebaseDatabase.getInstance().getReference("Leagues").child("National")
    val jsonArray=JSONObject(response).getJSONArray("response")
    for(i in 0 until 1){
        val jsonObject=jsonArray.getJSONObject(i)
        val jsonLeague=jsonObject.getJSONObject("league")
        val flag=jsonObject.getJSONObject("country").getString("flag")
        val league=gson.fromJson(jsonLeague.toString(),GameLeague::class.java)
        league.countryPhoto=flag
        league.name?.let { firebaseDatabase.child(it).setValue(league) }
    }
}
fun makeTeamList(mutableList: MutableList<Team>,response:String,  id:Int){
    val gson= Gson()
    val jsonObject= JSONObject(response)
    val jsonArray=jsonObject.getJSONArray("response")

    for(i in 0 until jsonArray.length()){
        val jsonTemp=jsonArray.getJSONObject(i)
        val jsonArrTemp=jsonTemp.getJSONObject("team")
        val team=gson.fromJson<Team>(jsonArrTemp.toString(),Team::class.java)
        team.leagueId=id.toString()
        mutableList.add(team)
    }

}
fun getData(id:Int):MutableList<Team>{
    val client = OkHttpClient()
    val mutableList= mutableListOf<Team>()
    val request = Request.Builder()
        .url("https://api-football-v1.p.rapidapi.com/v3/teams?league=$id&season=2021")
        .get()
        .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
        .addHeader("X-RapidAPI-Key", "2189b0cc44msh3d61c8e39f613b8p12ec99jsn820e38f51e4e")
        .build()
    val response = client.newCall(request).execute().body!!.string()
    makeTeamList(mutableList,response,id)
    return mutableList
}
fun getNationalData():MutableList<Team>{
    val client = OkHttpClient()
    val mutableList= mutableListOf<Team>()
    var request = Request.Builder()
        .url("https://api-football-v1.p.rapidapi.com/v3/teams?league=5&season=2022")
        .get()
        .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
        .addHeader("X-RapidAPI-Key", "2189b0cc44msh3d61c8e39f613b8p12ec99jsn820e38f51e4e")
        .build()

    var response = client.newCall(request).execute().body!!.string()
    makeTeamList(mutableList,response,5)
    request = Request.Builder()
        .url("https://api-football-v1.p.rapidapi.com/v3/teams?league=34&season=2022")
        .get()
        .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
        .addHeader("X-RapidAPI-Key", "2189b0cc44msh3d61c8e39f613b8p12ec99jsn820e38f51e4e")
        .build()

     response = client.newCall(request).execute().body!!.string()
    makeTeamList(mutableList,response,34)
    return mutableList
}
fun getTeamForm( teamId:String, leagueId:String,season:String):MutableList<Char>{
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://api-football-v1.p.rapidapi.com/v3/teams/statistics?league=${leagueId}&season=$season&team=$teamId")
        .get()
        .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
        .addHeader("X-RapidAPI-Key", "2189b0cc44msh3d61c8e39f613b8p12ec99jsn820e38f51e4e")
        .build()

    val response = client.newCall(request).execute().body!!.string()
    val answer= getTeamFormJson(response)
    val list= mutableListOf<Char>()
    answer.toCharArray().forEach {
        list.add(it)
    }
    return list
}
fun getTeamFormJson(response:String):String{
    val gson= Gson()
    val jsonObject= JSONObject(response)
    val responseObject=jsonObject.getJSONObject("response")
    return responseObject.getString("form")
}
fun getPlayerFromJson(mutableList: MutableList<Player>,response: String):MutableList<Player>{
    val gson= Gson()
    val jsonObject= JSONObject(response)
    val jsonObjectResponse=jsonObject.getJSONArray("response")
    val jsonArrTmp=jsonObjectResponse.getJSONObject(0)
    val jsonArrayPlayers=jsonArrTmp.getJSONArray("players")
    for(i in 0 until jsonArrayPlayers.length()){
        val jsonTemp=jsonArrayPlayers.getJSONObject(i)
        val player=gson.fromJson<Player>(jsonTemp.toString(),Player::class.java)
        mutableList.add(player)
    }
    return mutableList
}
fun getPlayerList(id:Int):MutableList<Player>{
    val client = OkHttpClient()
    val mutableList= mutableListOf<Player>()
    val request = Request.Builder()
        .url("https://api-football-v1.p.rapidapi.com/v3/players/squads?team=$id")
        .get()
        .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
        .addHeader("X-RapidAPI-Key", "2189b0cc44msh3d61c8e39f613b8p12ec99jsn820e38f51e4e")
        .build()

    val response = client.newCall(request).execute().body!!.string()
    return getPlayerFromJson(mutableList,response)
}
fun getGamesByTeam(id:Int,from:String,to:String,season:String):MutableList<Game>{
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://api-football-v1.p.rapidapi.com/v3/fixtures?season=$season&team=$id&from=$from&to=$to")
        .get()
        .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
        .addHeader("X-RapidAPI-Key", "2189b0cc44msh3d61c8e39f613b8p12ec99jsn820e38f51e4e")
        .build()
    val response = client.newCall(request).execute()

    return getGameFromJson(response.body!!.string())
}
fun getGameFromJson(response: String):MutableList<Game>{
    val gson=Gson()
    val jsonObject= JSONObject(response)
    val list= mutableListOf<Game>()
    val jsonArray=jsonObject.getJSONArray("response")
    for(i in 0 until jsonArray.length()){
        val jsonTemp=jsonArray.getJSONObject(i)
        var jsonArrTemp=jsonTemp.getJSONObject("fixture")
        val fixture= getFixtureJson(jsonArrTemp)
        val league=Gson().fromJson(jsonTemp.getJSONObject("league").toString(),GameLeague::class.java)
        var jsonArrTeams=jsonTemp.getJSONObject("teams")
        val teamOne=Gson().fromJson(jsonArrTeams.getJSONObject("home").toString(),GameTeam::class.java)
        val teamTwo=Gson().fromJson(jsonArrTeams.getJSONObject("away").toString(),GameTeam::class.java)
        val teamArr= arrayOf(teamOne,teamTwo)
        var jsonObjectGoals=jsonTemp.getJSONObject("goals")
        val goalsGame=GoalsGame(jsonObjectGoals.getString("home")!!,jsonObjectGoals.getString("away"))
        val jsonObjectScore=jsonTemp.getJSONObject("score")
        val score= Score(gson.fromJson(jsonObjectScore.getJSONObject("halftime").toString(), Results::class.java),
                        gson.fromJson(jsonObjectScore.getJSONObject("fulltime").toString(),Results::class.java),
                        gson.fromJson(jsonObjectScore.getJSONObject("extratime").toString(),Results::class.java),
                         gson.fromJson(jsonObjectScore.getJSONObject("penalty").toString(),Results::class.java))
        var game=Game()
        game.fixture=fixture
        game.league=league
        game.teams=teamArr
        game.goalsGame=goalsGame
        game.score=score
        list.add(game)

    }
    return list
}
fun getFixtureJson(jsonObject:JSONObject): Fixture {
    val fixture= Fixture()
    fixture.id=jsonObject.getInt("id")
    fixture.referee=jsonObject.getString("referee")
    fixture.date=jsonObject.getString("date")
    val venue=Gson().fromJson(jsonObject.getJSONObject("venue").toString(), Venue::class.java)
    val status=Gson().fromJson(jsonObject.getJSONObject("status").toString(), GameStatus::class.java)
    fixture.venue=venue
    fixture.status=status
    return fixture
}
fun getPlayerStatistic(id:Int): PlayerStatistic {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://api-football-v1.p.rapidapi.com/v3/players?id=$id&season=2021")
        .get()
        .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
        .addHeader("X-RapidAPI-Key", "2189b0cc44msh3d61c8e39f613b8p12ec99jsn820e38f51e4e")
        .build()

    val response = client.newCall(request).execute().body!!.string()
    return getPlayerStatisticFromJson(response)
}
fun getPlayerStatisticFromJson( response: String): PlayerStatistic {
    val gson=Gson()
    val jsonObject=JSONObject(response)
    val playerStatistic=PlayerStatistic()
    val jsonArray=jsonObject.getJSONArray("response")
    for(i in 0 until jsonArray.length()){
        val jsonTemp=jsonArray.getJSONObject(i)
        val jsonObjectTempPlayer=jsonTemp.getJSONObject("player")
        val player= getPlayerFromJson(jsonObjectTempPlayer)
        val jsonArrayCurrent=jsonTemp.getJSONArray("statistics")
        val list= getPlayerStatistic(jsonArrayCurrent)
        playerStatistic.player=player
        playerStatistic.statistic=list
    }
    return playerStatistic
}
fun getPlayerFromJson(jsonObject:JSONObject):Player{
    val player=Player()
    player.id=jsonObject.getInt("id")
    player.name=jsonObject.getString("name")
    player.firstName=jsonObject.getString("firstname")
    player.lastName=jsonObject.getString("lastname")
    player.age=jsonObject.getInt("age")
    player.birth=Gson().fromJson(jsonObject.getString("birth"),PlayerBirth::class.java)
    player.nationality=jsonObject.getString("nationality")
    player.height=jsonObject.getString("height")
    player.weight=jsonObject.getString("weight")
    player.injured=jsonObject.getBoolean("injured")
    player.photo=jsonObject.getString("photo")
    return player
}
fun getPlayerStatistic(jsonArray: JSONArray):MutableList<Statistics>{
    val gson=Gson()
    val mutableList= mutableListOf<Statistics>()
    for(i in 0 until jsonArray.length()){
        var playerStatistic=Statistics()
        val jsonTemp=jsonArray.getJSONObject(i)
        playerStatistic.team=gson.fromJson(jsonTemp.getJSONObject("team").toString(),Team::class.java)
        playerStatistic.league=gson.fromJson(jsonTemp.getJSONObject("league").toString(),GameLeague::class.java)
        playerStatistic.substitutes= getSubstituesPlayer(jsonTemp.getJSONObject("substitutes"))
        playerStatistic.games=gson.fromJson(jsonTemp.getJSONObject("games").toString(),PlayerGameStatistic::class.java)
        playerStatistic.shots=gson.fromJson(jsonTemp.getJSONObject("shots").toString(),ShotsGameStatistic::class.java)
        playerStatistic.goal=gson.fromJson(jsonTemp.getJSONObject("goals").toString(),GoalsGameStatistic::class.java)
        playerStatistic.passes=gson.fromJson(jsonTemp.getJSONObject("passes").toString(),PassesGameStatistic::class.java)
        playerStatistic.tacklesGameStatistic=gson.fromJson(jsonTemp.getJSONObject("tackles").toString(),TacklesGameStatistic::class.java)
        playerStatistic.duealsGameStatistic=gson.fromJson(jsonTemp.getJSONObject("duels").toString(),DuealsGameStatistic::class.java)
        playerStatistic.dribblesGameStatistic=gson.fromJson(jsonTemp.getJSONObject("dribbles").toString(),DribblesGameStatistic::class.java)
        playerStatistic.foulsGameStatistic=gson.fromJson(jsonTemp.getJSONObject("fouls").toString(),FoulsGameStatistic::class.java)
        playerStatistic.cardsGameStatistic=gson.fromJson(jsonTemp.getJSONObject("cards").toString(),CardsGameStatistic::class.java)
        playerStatistic.penaltyGameStatistic=gson.fromJson(jsonTemp.getJSONObject("penalty").toString(),PenaltyGameStatistic::class.java)
        mutableList.add(playerStatistic)

    }
    return mutableList
}
fun getSubstituesPlayer(jsonObject: JSONObject):SubstitutesGameStatistic{
    val substitutesGameStatistic=SubstitutesGameStatistic()
    substitutesGameStatistic.inGame=jsonObject.getString("in")
    substitutesGameStatistic.out=jsonObject.getString("out")
    substitutesGameStatistic.bench=jsonObject.getString("bench")
    return substitutesGameStatistic
}
fun getGameInfo(game:Game):Game{
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://api-football-v1.p.rapidapi.com/v3/fixtures?id=${game.fixture!!.id}")
        .get()
        .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
        .addHeader("X-RapidAPI-Key", "2189b0cc44msh3d61c8e39f613b8p12ec99jsn820e38f51e4e")
        .build()

    val response = client.newCall(request).execute().body!!.string()
    return getGameInfoFromJson(response,game)
}
fun getGameInfoFromJson(response: String,game:Game):Game{
    var gson=Gson()
    val jsonObject= JSONObject(response)
    val jsonArray=jsonObject.getJSONArray("response")
    for(i in 0 until jsonArray.length()){
        val jsonTemp=jsonArray.getJSONObject(i)
        game.eventList= getEventsFromJsonGame(jsonTemp.getJSONArray("events"))
        game.lineups= getLineupsFromJson(jsonTemp.getJSONArray("lineups"))
        game.gameStatistic= getStatistic(jsonTemp.getJSONArray("statistics"))
    }

    return game
}
fun getEventsFromJsonGame(jsonArray: JSONArray):MutableList<Event>{
    val gson=Gson()
    var mutableList= mutableListOf<Event>()
    for(i in 0 until jsonArray.length()){
        val jsonTemp=jsonArray.getJSONObject(i)
        var time=gson.fromJson(jsonTemp.getJSONObject("time").toString(), Time::class.java)
        var team=gson.fromJson(jsonTemp.getJSONObject("team").toString(),GameTeam::class.java)
        var player=gson.fromJson(jsonTemp.getJSONObject("player").toString(),EventPlayer::class.java)
        var assist=gson.fromJson(jsonTemp.getJSONObject("assist").toString(),EventPlayer::class.java)
        var type=jsonTemp.getString("type")
        var detail=jsonTemp.getString("detail")
        var comments=jsonTemp.getString("comments")
        mutableList.add(Event(time,team,player,assist,type,detail,comments))
    }
    return mutableList
}
fun getLineupsFromJson(jsonArray: JSONArray):Array<Lineups>{
    val gson=Gson()
    var mutableListLineup= mutableListOf<Lineups>()
    for(i in 0 until jsonArray.length()){
        val jsonObjectTemp=jsonArray.getJSONObject(i)
        var gameTeam=gson.fromJson(jsonObjectTemp.getJSONObject("team").toString(),GameTeam::class.java)
        var coach=gson.fromJson(jsonObjectTemp.getJSONObject("coach").toString(),EventPlayer::class.java)
        var formation=jsonObjectTemp.getString("formation")
        val jsonArrStart=jsonObjectTemp.getJSONArray("startXI")
        var startXI= mutableListOf<GamePlayer>()
        for(i in 0 until jsonArrStart.length()){
            val jsonObjectStart=jsonArrStart.getJSONObject(i)
            startXI.add(gson.fromJson(jsonObjectStart.getJSONObject("player").toString(),GamePlayer::class.java))
        }
        var sub= mutableListOf<GamePlayer>()
        val jsonArrSub=jsonObjectTemp.getJSONArray("substitutes")
        for(i in 0 until jsonArrSub.length()){
            val jsonObjectSub=jsonArrSub.getJSONObject(i)
            sub.add(gson.fromJson(jsonObjectSub.getJSONObject("player").toString(),GamePlayer::class.java))
        }
        var lineups=Lineups(gameTeam,coach,formation,startXI,sub)
        mutableListLineup.add(lineups)
    }
    return mutableListLineup.toTypedArray()
}
fun getStatistic(jsonArray: JSONArray):Array<GameTeamStatistic>{
    var gson=Gson()
    val mutableList= mutableListOf<GameTeamStatistic>()
    for(i in 0 until jsonArray.length()){
        val jsonTemp=jsonArray.getJSONObject(i)
        val team=gson.fromJson(jsonTemp.getJSONObject("team").toString(),GameTeam::class.java)
        var mutableListStatistic= mutableListOf<GameStatistic>()
        val jsonArrStat=jsonTemp.getJSONArray("statistics")
        for (i in 0 until  jsonArrStat.length()){
            mutableListStatistic.add(gson.fromJson(jsonArrStat.getJSONObject(i).toString(),GameStatistic::class.java))
        }
        var gameTeamStatistic=GameTeamStatistic(team,mutableListStatistic)
        mutableList.add(gameTeamStatistic)
    }
    return mutableList.toTypedArray()
}

fun getStanding( id:Int,  season:Int):MutableList<Standings>{
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://api-football-v1.p.rapidapi.com/v3/standings?season=$season&league=$id")
        .get()
        .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
        .addHeader("X-RapidAPI-Key", "2189b0cc44msh3d61c8e39f613b8p12ec99jsn820e38f51e4e")
        .build()

    val response = client.newCall(request).execute().body!!.string()
    return getStandingsJson(response)
}

fun getStandingsJson(response:String):MutableList<Standings>{
    val gson=Gson()
    val mutableList= mutableListOf<Standings>()
    val jsonObject= JSONObject(response)
    val jsonArray=jsonObject.getJSONArray("response")
    for(i in 0 until jsonArray.length()){
        val objectTempJson=jsonArray.getJSONObject(i)
        val objectTempLeague=objectTempJson.getJSONObject("league")
        val objectTempArr=objectTempLeague.getJSONArray("standings")
        for(i in 0 until objectTempArr.length()){
            val objectTempArrayStanding=objectTempArr.getJSONArray(i)
            for(i in 0 until objectTempArrayStanding.length()) {
                val objectTemp=objectTempArrayStanding.getJSONObject(i)
                val rank = objectTemp.getString("rank")
                var team =
                    gson.fromJson(objectTemp.getJSONObject("team").toString(), GameTeam::class.java)
                var points = objectTemp.getString("points")
                var goalsDiff = objectTemp.getString("goalsDiff")
                var group = objectTemp.getString("group")
                var form = objectTemp.getString("form")
                var status = objectTemp.getString("status")
                var description = objectTemp.getString("description")
                var all = getStandingsStatistic(objectTemp.getJSONObject("all"))
                var home = getStandingsStatistic(objectTemp.getJSONObject("home"))
                var away = getStandingsStatistic(objectTemp.getJSONObject("away"))
                val standing = Standings(
                    rank,
                    team,
                    points,
                    goalsDiff,
                    group,
                    form,
                    status,
                    description,
                    all,
                    home,
                    away
                )
                mutableList.add(standing)
            }
            }
    }
    return mutableList
}
fun getStandingsStatistic(jsonObject:JSONObject):StandingsStatistic{
    val played=jsonObject.getString("played")
    val win=jsonObject.getString("win")
    val draw=jsonObject.getString("draw")
    val lose=jsonObject.getString("lose")
    val goalsGame=GoalsGame()
    val objectGoals=jsonObject.getJSONObject("goals")
    val forGoals=objectGoals.getString("for")
    val against=objectGoals.getString("against")
    goalsGame.home=forGoals
    goalsGame.away=against
    val standingStatistic=StandingsStatistic(played, win, draw,lose,goalsGame)
    return standingStatistic
}
fun getTopScorrect(id:Int,season:Int):MutableList<PlayerStatistic>{
    val client = OkHttpClient()
    val gson=Gson()
    val request = Request.Builder()
        .url("https://api-football-v1.p.rapidapi.com/v3/players/topscorers?league=$id&season=$season")
        .get()
        .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
        .addHeader("X-RapidAPI-Key", "2189b0cc44msh3d61c8e39f613b8p12ec99jsn820e38f51e4e")
        .build()

    val response = client.newCall(request).execute().body!!.string()
    val mutableList= mutableListOf<PlayerStatistic>()
    val jsonObject= JSONObject(response)
    val jsonArray=jsonObject.getJSONArray("response")
    for(i in 0 until jsonArray.length()){
        val tmp=jsonArray.getJSONObject(i)
        val playerStatistic=PlayerStatistic()
        playerStatistic.player=getPlayerFromJson(tmp.getJSONObject("player"))
        playerStatistic.statistic=getPlayerStatistic(tmp.getJSONArray("statistics"))
        mutableList.add(playerStatistic)
    }
    return mutableList
}
fun getGameByTours(id:Int,season: Int):MutableList<Game>{
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://api-football-v1.p.rapidapi.com/v3/fixtures?league=$id&season=$season")
        .get()
        .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
        .addHeader("X-RapidAPI-Key", "2189b0cc44msh3d61c8e39f613b8p12ec99jsn820e38f51e4e")
        .build()

    val response = client.newCall(request).execute().body!!.string()
    return getGameFromJson(response)
}
fun getGameByToursNotStarted(id:Int,season: Int):MutableList<Game>{
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://api-football-v1.p.rapidapi.com/v3/fixtures?league=$id&season=$season&status=NS")
        .get()
        .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
        .addHeader("X-RapidAPI-Key", "2189b0cc44msh3d61c8e39f613b8p12ec99jsn820e38f51e4e")
        .build()

    val response = client.newCall(request).execute().body!!.string()
    return getGameFromJson(response)
}
fun getDifficultStanding(id:Int,season:Int):MutableList<MutableList<Standings>>{
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://api-football-v1.p.rapidapi.com/v3/standings?season=$season&league=$id")
        .get()
        .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
        .addHeader("X-RapidAPI-Key", "2189b0cc44msh3d61c8e39f613b8p12ec99jsn820e38f51e4e")
        .build()

    val response = client.newCall(request).execute().body!!.string()
    return (getDifficultStandingFromJson(response))
}
fun getDifficultStandingFromJson(response:String):MutableList<MutableList<Standings>>{
    val gson=Gson()
    val mutableList= mutableListOf<MutableList<Standings>>()
    val jsonObject= JSONObject(response)
    val jsonArray=jsonObject.getJSONArray("response")
    for(i in 0 until jsonArray.length()) {
        val objectTempJson = jsonArray.getJSONObject(i)
        val objectTempLeague = objectTempJson.getJSONObject("league")
        val objectTempArr = objectTempLeague.getJSONArray("standings")
        for (i in 0 until objectTempArr.length()) {
            val objectTempFF = objectTempArr.getJSONArray(i)
            val tempList = mutableListOf<Standings>()
            for (i in 0 until objectTempFF.length()) {
                val objectTemp=objectTempFF.getJSONObject(i)
                val rank = objectTemp.getString("rank")
                var team =
                    gson.fromJson(objectTemp.getJSONObject("team").toString(), GameTeam::class.java)
                var points = objectTemp.getString("points")
                var goalsDiff = objectTemp.getString("goalsDiff")
                var group = objectTemp.getString("group")
                var form = objectTemp.getString("form")
                var status = objectTemp.getString("status")
                var description = objectTemp.getString("description")
                var all = getStandingsStatistic(objectTemp.getJSONObject("all"))
                var home = getStandingsStatistic(objectTemp.getJSONObject("home"))
                var away = getStandingsStatistic(objectTemp.getJSONObject("away"))
                val standing = Standings(
                    rank,
                    team,
                    points,
                    goalsDiff,
                    group,
                    form,
                    status,
                    description,
                    all,
                    home,
                    away
                )
                tempList.add(standing)
            }
            mutableList.add(tempList)
        }
    }
    return mutableList
}

fun getGamesByDate(date:String):MutableList<Game>{
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://api-football-v1.p.rapidapi.com/v3/fixtures?date=$date")
        .get()
        .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
        .addHeader("X-RapidAPI-Key", "2189b0cc44msh3d61c8e39f613b8p12ec99jsn820e38f51e4e")
        .build()

    val response = client.newCall(request).execute().body!!.string()
    return getGameFromJson(response)
}
fun getGameByDateNotification(id:Int):MutableList<Game>{
    val formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val m=LocalDate.now().format(formatter)
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://api-football-v1.p.rapidapi.com/v3/fixtures?date=$m&season=2021&team=$id")
        .get()
        .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
        .addHeader("X-RapidAPI-Key", "2189b0cc44msh3d61c8e39f613b8p12ec99jsn820e38f51e4e")
        .build()

    val response = client.newCall(request).execute().body!!.string()
    return getGameFromJson(response)

}



