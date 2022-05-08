package com.example.diplom.models.standings

import com.example.diplom.models.game.GoalsGame

data class StandingsStatistic(var played:String?=null, var win:String?=null, var draw:String?=null,var loose:String?=null,var goals:GoalsGame?=null)
