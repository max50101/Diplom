package com.example.diplom.models.game

import com.example.diplom.models.game.event.EventPlayer

data class Lineups(var team:GameTeam,var coach:EventPlayer?=null,var foramation:String?=null,var startXI:MutableList<GamePlayer>, var substitutes:MutableList<GamePlayer>)
