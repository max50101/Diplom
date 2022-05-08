package com.example.diplom.loggin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.diplom.R

import com.example.diplom.teamChoose.TeamChooseFragment
import com.example.diplom.teamChoose.TeamListFragment

class ChooseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)
        val code=intent.getIntExtra("value",0)
        when(code){
            1->supportFragmentManager.beginTransaction()
                .add(R.id.activity_choose, TeamChooseFragment.newInstance())
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
            2->supportFragmentManager.beginTransaction()
                .add(R.id.activity_choose, TeamListFragment.newInstance("5"))
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
        }

    }


}