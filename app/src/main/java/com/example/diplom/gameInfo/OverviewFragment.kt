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
import com.example.diplom.models.game.Venue
import com.google.gson.Gson

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OverviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OverviewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    lateinit var eventsRecycleView: RecyclerView
    lateinit var venue:TextView
    lateinit var referee:TextView
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
        val game= Gson().fromJson(param1, Game::class.java)
        val view= inflater.inflate(R.layout.fragment_overview, container, false )
        eventsRecycleView=view.findViewById(R.id.events)
        eventsRecycleView.layoutManager=LinearLayoutManager(context)
        if(!game.eventList!!.isEmpty()) {
            val eventsAdapter =
                context?.let { game.eventList?.let { it1 -> EventAdapter(it, it1, game) } }
            eventsRecycleView.adapter = eventsAdapter
        }
        venue=view.findViewById(R.id.venue)
        venue.text=game.fixture!!.venue!!.name
        referee=view.findViewById(R.id.referee)
        referee.text=game.fixture!!.referee
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OverviewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            OverviewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)

                }
            }
    }
}