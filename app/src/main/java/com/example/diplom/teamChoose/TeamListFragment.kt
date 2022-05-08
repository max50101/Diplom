package com.example.diplom.teamChoose

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.models.Team
import com.example.diplom.models.getData
import com.example.diplom.models.getNationalData
import com.example.diplom.teamChoose.TeamAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class TeamListFragment : Fragment() {
    lateinit var mutableList:MutableList<Team>
    private var param1: String? = null
    private var param2: String? = null
    lateinit var mAdapter: TeamAdapter
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
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_team_list, container, false)
        val mTaskRecyclerView=view.findViewById<RecyclerView>(R.id.teams)
        mTaskRecyclerView.layoutManager = LinearLayoutManager(context)
        GlobalScope.launch(Dispatchers.IO) {
            var data= mutableListOf<Team>()
            if(!param1.equals("5")) {
                data .addAll( getData(param1!!.toInt()))

            }else{
                data.addAll(getNationalData())

            }
            launch(Dispatchers.Main) {
                mAdapter =
                    context?.let { TeamAdapter(it, data, activity as AppCompatActivity) }!!
                mTaskRecyclerView.adapter = mAdapter
            }

        }




        return view
    }

    override fun onDetach() {
        super.onDetach()
        if(param1.equals("5")) {
            requireActivity().finish()
        }
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            TeamListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)

                }
            }
    }
    }
