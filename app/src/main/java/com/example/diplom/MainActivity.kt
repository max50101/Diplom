package com.example.diplom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.diplom.databinding.ActivityMainBinding
import com.example.diplom.games.GamesFragment
import com.example.diplom.models.CountryLeague
import com.example.diplom.models.User
import com.example.diplom.models.game.GameLeague
import com.example.diplom.news.NewsFragment
import com.example.diplom.profile.ProfileFragment
import com.example.diplom.table.TableFragment
import com.example.diplom.table.temp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.gson.Gson
import nl.joery.animatedbottombar.AnimatedBottomBar
import java.io.IOException
import android.app.AlarmManager

import android.app.PendingIntent
import android.content.Context

import android.content.Intent
import android.util.Log
import java.util.*


fun isReallyOnline(): Boolean {
    val runtime = Runtime.getRuntime()
    try {
        val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
        val exitValue = ipProcess.waitFor()
        return exitValue == 0
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }
    return false
}

class MainActivity : AppCompatActivity() {
    lateinit var user: User
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser
    lateinit var firebaseReference: DatabaseReference
    lateinit var leagueList:MutableList<CountryLeague>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        initLeagues()
        val sharedPreferences=applicationContext.getSharedPreferences(shareName, Context.MODE_PRIVATE)
//        if(!sharedPreferences.contains(notification)){
//            val sharedEd=sharedPreferences.edit()
//            sharedEd.putInt(notification,1)
//            sharedEd.commit()
//
//        }
        myAlarm()
        val bar=findViewById<AnimatedBottomBar>(R.id.bottom_bar)
       // setCurrentFragment(NewsFragment())
        //bar.selectTabAt(0)
        bar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {

            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
              when(newTab.id){
                  R.id.news->setCurrentFragment(NewsFragment())
                  R.id.games_playes->setCurrentFragment(GamesFragment())
                  R.id.tables-> {
                      if (this@MainActivity::leagueList.isInitialized) {
                          setCurrentFragment(TableFragment.newInstance(Gson().toJson(temp(leagueList))))
                      } else {
                          setCurrentFragment(NewsFragment())
                          bar.selectTabAt(0)
                      }

                  }
                  R.id.profile->{
                      if(this@MainActivity::user.isInitialized) {
                          setCurrentFragment(ProfileFragment.newInstance(Gson().toJson(user)))
                      }else{
                          setCurrentFragment(NewsFragment())
                          bar.selectTabAt(0)
                      }
                  }
              }
            }
        })
    }
    fun myAlarm() {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 21)
        calendar.set(Calendar.MINUTE, 13)
        calendar.set(Calendar.SECOND, 1)
        if (calendar.getTime().compareTo(Date()) < 0) calendar.add(Calendar.DAY_OF_MONTH, 1)
        val intent = Intent(applicationContext, NotificationReceiver::class.java)
        intent.putExtra("Name","Main")
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            1000,
            pendingIntent
        )
    }
    private fun init(){
        firebaseAuth= FirebaseAuth.getInstance()
        firebaseUser=firebaseAuth.currentUser!!
        firebaseReference=FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.uid)
        firebaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(User::class.java)!!
                //init()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun initLeagues(){

        firebaseAuth= FirebaseAuth.getInstance()
        firebaseUser=firebaseAuth.currentUser!!
        firebaseReference=FirebaseDatabase.getInstance().getReference("Leagues")
        firebaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                leagueList= mutableListOf()
                for(ds in snapshot.children){
                    val countryName=ds.key
                    val leagueListLeagues= mutableListOf<GameLeague>()
                    for(elem in ds.children){
                        elem.getValue(GameLeague::class.java)?.let { leagueListLeagues.add(it) }
                    }
                    leagueList.add(CountryLeague(countryName,leagueListLeagues))
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

}