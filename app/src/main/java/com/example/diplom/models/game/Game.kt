package com.example.diplom.models.game

import com.example.diplom.models.game.Fixture
import com.example.diplom.models.game.GameLeague
import com.example.diplom.models.game.GameTeam
import com.example.diplom.models.game.event.Event
import com.example.diplom.models.game.event.EventPlayer
import com.example.diplom.models.game.score.Score

data class Game(var fixture: Fixture?=null, var league:GameLeague?=null,var teams:Array<GameTeam>?=null,var goalsGame: GoalsGame?=null,var score:Score?=null
                ,var eventList:MutableList<Event>?=null,var lineups:Array<Lineups>?=null,var gameStatistic: Array<GameTeamStatistic>?=null)
