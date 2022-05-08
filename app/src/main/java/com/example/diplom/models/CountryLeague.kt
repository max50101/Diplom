package com.example.diplom.models

import com.example.diplom.models.game.GameLeague

data class CountryLeague(val countryName:String?=null, val leagueList:MutableList<GameLeague>)
