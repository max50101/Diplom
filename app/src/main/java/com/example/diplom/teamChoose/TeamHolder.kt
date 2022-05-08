package com.example.diplom.teamChoose

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplom.R
import com.example.diplom.models.Team
import com.google.gson.Gson

class TeamHolder (val context: Context, val itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    lateinit var mTeamName: TextView
    lateinit var mTeamDate: TextView
    lateinit var mTeam: Team
    lateinit var mLogo: ImageView
    lateinit var mactivity:AppCompatActivity
    var mposition=0
    override fun onClick(p0: View?) {
        val intent= Intent()
        intent.putExtra("team", Gson().toJson(mTeam))
        mactivity.setResult(RESULT_OK,intent)
        mactivity.finish()

    }
    init{
        itemView.setOnClickListener(this)
        mTeamName=itemView.findViewById<TextView>(R.id.name)
        mTeamDate=itemView.findViewById<TextView>(R.id.foundation)
        mLogo=itemView.findViewById<ImageView>(R.id.logo)
    }
    fun bindTask(team: Team, position:Int,activity:AppCompatActivity){
        mactivity=activity
        mposition=position
        mTeam=team
        mTeamName.setText(mTeam.name)
        mTeamDate.setText(mTeam.founded)
        Glide.with(context)
            .load(mTeam.logo)
            .fitCenter()
            .into(mLogo)
    }

}