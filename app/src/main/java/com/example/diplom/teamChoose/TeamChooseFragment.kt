package com.example.diplom.teamChoose

import android.app.DownloadManager
import android.content.ContentValues.TAG
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.diplom.models.Team
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

import org.json.JSONArray
import org.json.JSONException

import android.util.Log
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentTransaction
import com.example.diplom.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TeamChooseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TeamChooseFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val  view=inflater.inflate(R.layout.fragment_team_choose, container, false)
        val cardViewApl=view.findViewById<CardView>(R.id.aplCardView)
        val cardViewLigueOne=view.findViewById<CardView>(R.id.ligueOneCarView)
        val cardViewSeria=view.findViewById<CardView>(R.id.seriaaCardView)
        val cardViewLaLiga=view.findViewById<CardView>(R.id.laligaCardView)
        val cardViewBundesliga=view.findViewById<CardView>(R.id.bundesligaCardView)
        val cardViewRpl=view.findViewById<CardView>(R.id.rplCardView)
        cardViewApl.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.activity_choose,TeamListFragment.newInstance("39"))
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()

        }
        cardViewLigueOne.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.activity_choose,TeamListFragment.newInstance("61"))
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        }
        cardViewSeria.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.activity_choose,TeamListFragment.newInstance("135"))
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        }
        cardViewLaLiga.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.activity_choose,TeamListFragment.newInstance("140"))
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        }
        cardViewBundesliga.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.activity_choose,TeamListFragment.newInstance("78"))
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        }
        cardViewRpl.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.activity_choose,TeamListFragment.newInstance("235"))
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        }
        return view
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().finish()
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            TeamChooseFragment().apply {



            }
    }
}