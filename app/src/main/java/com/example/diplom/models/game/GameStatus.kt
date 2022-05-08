package com.example.diplom.models.game

import com.example.diplom.models.game.score.Score

data class GameStatus(val long:String?=null,val short:String?=null, val elapsed:String?=null, var score: Score?=null)
