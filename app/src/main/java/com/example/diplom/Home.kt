package com.example.diplom

import android.app.Application
import android.content.Intent
import androidx.core.app.ActivityCompat.finishAffinity
import com.example.diplom.loggin.LoginActivity
import com.example.diplom.models.getGamesByTeam
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import java.io.IOException

const val shareName="MyPreference"
const val teamId="TeamId"
const val countryId="CountryId"
const val notification="Notification"

class Home :Application(){
    override fun onCreate() {
        super.onCreate()
        val firebaseAuth=FirebaseAuth.getInstance()
        val firebaseUser=firebaseAuth.currentUser

        if(firebaseUser!=null){
            val intent=Intent(applicationContext,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK )
            startActivity(intent)
        }

    }

}