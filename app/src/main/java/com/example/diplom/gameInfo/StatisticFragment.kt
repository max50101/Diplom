package com.example.diplom.gameInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.models.game.Game
import com.example.diplom.models.game.GameStatisticForHolder
import com.google.gson.Gson

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StatisticFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StatisticFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var statistic:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_statistic, container, false)
        statistic=view.findViewById(R.id.statistics)
        statistic.layoutManager=LinearLayoutManager(context)
        val game= Gson().fromJson(param1,Game::class.java)
        if(game.gameStatistic!!.size>0) {
            val list = createListForHolder(game)
            statistic.adapter = context?.let { StatisticAdapter(it, list, game) }
        }
        return view
    }
    fun createListForHolder(game: Game):MutableList<GameStatisticForHolder>{
        var statisticList= mutableListOf<GameStatisticForHolder>()
        val homeTeam= game.gameStatistic?.get(0)
        val awayTeam= game.gameStatistic?.get(1)
        for(elem in  homeTeam!!.statistics){
            val type=elem.type
            for(element in awayTeam!!.statistics){
                if(element.type.equals(type)){
                    statisticList.add(GameStatisticForHolder(type, elem.value,element.value))
                }
            }
        }
        return statisticList
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StatisticFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            StatisticFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)

                }
            }
    }
}