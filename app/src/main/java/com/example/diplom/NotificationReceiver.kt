package com.example.diplom

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

import android.os.Build

import android.R
import android.app.*
import com.example.diplom.gameInfo.GameActivity
import com.example.diplom.models.game.Game
import com.example.diplom.models.getGameByDateNotification
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val TAG = "aaa"
class NotificationReceiver:BroadcastReceiver() {
    val NOTIFICATION_CHANNEL_ID = "10001"
    val NOTIFICATION_CHANNEL_IDTWO = "10002"


        override fun onReceive(context: Context, intent: Intent) {
            val intentName=intent.getStringExtra("Name")
            if(intentName.equals("Main")) {
                val notificationHelper = NotificationHelper(context)
                notificationHelper.createNotification(0)
            }
            if(intentName.equals("Squads")){
                val notificationHelper = NotificationHelper(context)
                notificationHelper.createNotification(1)
            }
            if(intentName.equals("Result")){
                val notificationHelper = NotificationHelper(context)
                notificationHelper.createNotification(2)
            }

        }
}