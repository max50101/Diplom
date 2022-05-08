package com.example.diplom.models.statistic

import com.example.diplom.models.Team
import com.example.diplom.models.game.GameLeague

data class Statistics(var team: Team?=null, var league:GameLeague?=null, var games:PlayerGameStatistic?=null, var substitutes:SubstitutesGameStatistic?=null,
                      var shots:ShotsGameStatistic?=null, var goal:GoalsGameStatistic?=null,
                      var passes:PassesGameStatistic?=null, var tacklesGameStatistic: TacklesGameStatistic?=null,
                      var duealsGameStatistic: DuealsGameStatistic?=null, var dribblesGameStatistic: DribblesGameStatistic?=null,
                      var foulsGameStatistic: FoulsGameStatistic?=null, var cardsGameStatistic: CardsGameStatistic?=null, var penaltyGameStatistic: PenaltyGameStatistic?=null)
