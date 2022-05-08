package com.example.diplom.models.standings

import com.example.diplom.models.game.GameTeam

data class Standings(var rank:String?=null, var team:GameTeam?=null, var points:String?=null, var goalsDiff:String?=null, var group:String?=null,var form:String?=null,var status:String?=null,
                    var desription:String?=null, var all:StandingsStatistic?=null, var home:StandingsStatistic?=null, var away:StandingsStatistic?=null)
