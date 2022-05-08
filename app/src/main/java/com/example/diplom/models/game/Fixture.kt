package com.example.diplom.models.game

import com.example.diplom.models.game.GameStatus
import com.example.diplom.models.game.Venue

data class Fixture(var id:Int?=null, var referee:String?=null, var date:String?=null, var venue: Venue?=null, var status: GameStatus?=null)
