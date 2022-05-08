package com.example.diplom.models.statistic

import com.example.diplom.models.Player
import com.example.diplom.models.PlayerBirth

data class PlayerStatistic(var player: Player?=null, var statistic: MutableList<Statistics>?=null, var rank:String?=null)
