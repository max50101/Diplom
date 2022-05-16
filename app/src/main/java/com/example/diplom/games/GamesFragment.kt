package com.example.diplom.games

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.models.game.Game
import com.example.diplom.models.game.GameLeague
import com.example.diplom.models.getGamesByDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GamesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GamesFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    lateinit var date:TextView
    lateinit var changeDate:AppCompatButton
    lateinit var activeLeague:RecyclerView
    lateinit var gameList:MutableList<Game>
    private var param1: String? = null
    private var param2: String? = null
    var day = 0
    var month: Int = 0
    var year: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var dateNew=""
    val formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_games, container, false)
        date=view.findViewById(R.id.date)
        changeDate=view.findViewById(R.id.change_date)
        activeLeague=view.findViewById(R.id.active_leagues)
        activeLeague.layoutManager=LinearLayoutManager(requireContext())
        dateNew=LocalDate.now().format(formatter)
        date.text=requireContext().getString(R.string.today_date)+dateNew
        changeDate.setOnClickListener{v->changeDate(v)}
        initMatchGames()
        return view
    }
    fun  changeDate(view:View){
        val calendar: Calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        val datePickerDialog =
            this.context?.let { DatePickerDialog(it, this, year, month,day) }
        datePickerDialog!!.show()
    }
    fun initMatchGames(){
        GlobalScope.launch (Dispatchers.IO){
            gameList=getGamesByDate(dateNew)
            val resultList= mutableListOf<tempLeagueGame>()
            val listOfLeagues= mutableListOf<String>()
            for(i in gameList.indices){
                val league=gameList[i].league
                if(listOfLeagues.contains(league!!.country)){
                    continue
                }else{
                    if (league != null) {
                        league.country?.let { listOfLeagues.add(it) }
                    }
                    val tempList= mutableListOf<Game>()
                    for(t in i until gameList.size){
                        val game=gameList[t]
                        if(game.league!!.country.equals(league!!.country)){
                            tempList.add(game)
                        }
                    }
                    resultList.add(tempLeagueGame(league.country,tempList))
                    val comparatorOne = ComparatorOne()
                    resultList.sortWith(comparatorOne)

                }
            }
            launch(Dispatchers.Main) {
                if(!resultList.isEmpty()) {
                    val adapter = GameLeagueAdapter(requireContext(), resultList)
                    activeLeague.adapter = adapter
                }
            }
        }
    }
    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GamesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        dateNew=LocalDate.of(p1,p2+1,p3).format(formatter)
        date.text=requireContext().getString(R.string.today_date)+dateNew
        initMatchGames()
    }
    internal class ComparatorOne: Comparator<tempLeagueGame>{
        override fun compare(o1: tempLeagueGame, o2: tempLeagueGame): Int {
            if(o1 == null || o2 == null){
                return 0;
            }
            return o2.country?.let { o1.country?.compareTo(it) }!!
        }
    }
}