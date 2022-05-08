package com.example.diplom.gameInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.models.game.Game
import com.google.gson.Gson

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SquadFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SquadFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null

    lateinit var startSquadRecyclerView: RecyclerView
    lateinit var subSquadRecyclerView: RecyclerView
    lateinit var awayStartSquadRecyclerView: RecyclerView
    lateinit var awaySubSquadRecyclerView: RecyclerView
    lateinit var coachHome:TextView
    lateinit var coachAway:TextView
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
        val view= inflater.inflate(R.layout.fragment_squad, container, false)
        val game= Gson().fromJson(param1,Game::class.java)
        coachHome=view.findViewById(R.id.coach_home)
        coachAway=view.findViewById(R.id.coach_away)
        if(game.lineups!=null&& !game.lineups!!.isEmpty()) {
            coachHome.text = game.lineups!!.get(0).coach!!.name
            coachAway.text = game.lineups!!.get(1).coach!!.name
        }
        startSquadRecyclerView=view.findViewById(R.id.start_squad_home)
        subSquadRecyclerView=view.findViewById(R.id.sub_squad_home)
        awayStartSquadRecyclerView=view.findViewById(R.id.start_squad_away)
        awaySubSquadRecyclerView=view.findViewById(R.id.sub_squad_away)
        startSquadRecyclerView.layoutManager=LinearLayoutManager(context)
        subSquadRecyclerView.layoutManager=LinearLayoutManager(context)
        awayStartSquadRecyclerView.layoutManager=LinearLayoutManager(context)
        awaySubSquadRecyclerView.layoutManager=LinearLayoutManager(context)
        if(game.lineups!=null&&game.lineups!!.size>0) {
            startSquadRecyclerView.adapter =
                context?.let { SquadAdapter(it, game.lineups!!.get(0).startXI, game, true) }
            subSquadRecyclerView.adapter =
                context?.let { SquadAdapter(it, game.lineups!!.get(0).substitutes, game, true) }
            awayStartSquadRecyclerView.adapter =
                context?.let { SquadAdapter(it, game.lineups!!.get(1).startXI, game, false) }
            awaySubSquadRecyclerView.adapter =
                context?.let { SquadAdapter(it, game.lineups!!.get(1).substitutes, game, false) }
        }


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SquadFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            SquadFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)

                }
            }
    }
}