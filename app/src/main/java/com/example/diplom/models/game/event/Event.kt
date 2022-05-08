package com.example.diplom.models.game.event

import com.example.diplom.models.game.GameTeam

data class Event(var time:Time,var gameTeam: GameTeam?=null,var eventPlayer: EventPlayer?=null, val assistEvent:EventPlayer?=null,var type:String?=null, var detail:String?=null, var comment:String?=null)
