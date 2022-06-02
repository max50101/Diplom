package com.example.diplom

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.ReceiverCallNotAllowedException
import android.graphics.Color
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.diplom.gameInfo.GameActivity
import com.example.diplom.models.game.Game
import com.example.diplom.models.getGameByDateNotification
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*

const val NOTIFICATION_CHANNEL_ID = "10001"
const val NOTIFICATION_CHANNEL_ID_TWO="10002"
class NotificationHelper(val context:Context){
    lateinit var mGame: Game

    fun createNotification(value:Int){
        GlobalScope.launch(Dispatchers.IO) {
            val sharedPreferences=context.getSharedPreferences(shareName,Context.MODE_PRIVATE)
            val teamId= sharedPreferences.getInt(teamId,0)
            val countryId=sharedPreferences.getInt(countryId,0)
            val list=getGameByDateNotification(teamId)
            if(!list.isEmpty()){
                launch(Dispatchers.Main) {
                    val game=list[0]
                    mGame=game
                    val mBuilder = NotificationCompat.Builder(
                        context!!, NOTIFICATION_CHANNEL_ID
                    )
                    mBuilder.setSmallIcon(R.drawable.ball)
                    if(value==0){
                        val intent = Intent(context, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        val resultPendingIntent = PendingIntent.getActivity(
                            context,
                            0 /* Request code */, intent,PendingIntent.FLAG_UPDATE_CURRENT
                        )
                        val simpleDateFormat= SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ", Locale.getDefault())
                        simpleDateFormat.timeZone= TimeZone.getTimeZone("GMT")
                        val resultDate= SimpleDateFormat("HH:mm",Locale.getDefault()).format(simpleDateFormat.parse(game.fixture!!.date))
                        mBuilder.setContentTitle(game!!.teams?.get(0)!!.name+"-"+ game!!.teams?.get(1)!!.name)
                            .setContentText("Game today at $resultDate")
                            .setAutoCancel(false)
                            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                            .setContentIntent(resultPendingIntent)

                    }else if(value==1){
                        val intent = Intent(context, GameActivity::class.java)
                        intent.putExtra("game", Gson().toJson(game))
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        val resultPendingIntent = PendingIntent.getActivity(
                            context,
                            1/* Request code */, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                        )
                        mBuilder.setContentTitle(game!!.teams?.get(0)!!.name+"-"+ game!!.teams?.get(1)!!.name)
                            .setContentText("Game starts in 45 minutes")
                            .setAutoCancel(false)
                            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                            .setContentIntent(resultPendingIntent)

                    }else if(value==2){
                        val intent = Intent(context, GameActivity::class.java)
                        intent.putExtra("game", Gson().toJson(game))
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        val resultPendingIntent = PendingIntent.getActivity(
                            context,
                            2/* Request code */, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                        )
                        mBuilder.setContentTitle(game!!.teams?.get(0)!!.name+"-"+ game!!.teams?.get(1)!!.name)
                            .setContentText("Game ended")
                            .setAutoCancel(false)
                            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                            .setContentIntent(resultPendingIntent)

                    }

                    val mNotificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val importance = NotificationManager.IMPORTANCE_HIGH
                        val notificationChannel = NotificationChannel(
                            NOTIFICATION_CHANNEL_ID,
                            "NOTIFICATION_CHANNEL_NAME",
                            importance
                        )
                        notificationChannel.enableLights(true)
                        notificationChannel.lightColor = Color.RED
                        assert(mNotificationManager != null)
                        mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID)
                        mNotificationManager.createNotificationChannel(notificationChannel)
                    }
                    mNotificationManager.notify(0 /* Request Code */, mBuilder.build())
                    if(value==0){
                        game.fixture!!.date?.let { setDateAlarm(it) }
                    }
                    if(value==1){
                        game.fixture!!.date?.let { setGameResult(it) }
                    }
                    if(value==2){
                        setCalendarLast()
                    }
                }

            }
        }

    }
    fun setDateAlarm(date:String){
        val simpleDateFormat= SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ", Locale.getDefault())
        simpleDateFormat.timeZone= TimeZone.getTimeZone("GMT")
        val resultDate= SimpleDateFormat("HH:mm:ss",Locale.getDefault()).format(simpleDateFormat.parse(date))
        val intent = Intent(context, NotificationReceiver::class.java)
        val calendar: Calendar = Calendar.getInstance()
        val arr=resultDate.split(":")
        var hours=arr[0].toInt()
        var minutes=arr[1].toInt()
        val seconds=arr[2].toInt()
        if(minutes>45){
            minutes=minutes-45
        }else{
            var tempmins=60-(45-minutes)
            hours=hours-1
            minutes=tempmins
        }
        calendar.set(Calendar.HOUR_OF_DAY, hours)
        calendar.set(Calendar.MINUTE, minutes)
        calendar.set(Calendar.SECOND, 1)
        intent.putExtra("Name","Squads")
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        val alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
        Log.i("Start","SecondAlarm")

    }
    fun setGameResult(date:String){
        val simpleDateFormat= SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ", Locale.getDefault())
        simpleDateFormat.timeZone= TimeZone.getTimeZone("GMT")
        val resultDate= SimpleDateFormat("HH:mm:ss",Locale.getDefault()).format(simpleDateFormat.parse(date))
        val calendar: Calendar = Calendar.getInstance()
        val arr=resultDate.split(":")
        var hours=arr[0].toInt()
        var minutes=arr[1].toInt()
        val seconds=arr[2].toInt()
        if(hours>22){
            calendar.add(Calendar.DATE, 1)
            hours=1
        }else{
            hours=hours+2
        }
        calendar.set(Calendar.HOUR_OF_DAY, 21)
        calendar.set(Calendar.MINUTE, 17)
        calendar.set(Calendar.SECOND, 1)
        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra("Name","Result")
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            1,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        val alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
        Log.i("Start","Third Alarm")
    }

    fun setCalendarLast(){
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 1)
        calendar.set(Calendar.MINUTE, 1)
        calendar.set(Calendar.SECOND, 1)
        if (calendar.getTime().compareTo(Date()) < 0) calendar.add(Calendar.DAY_OF_MONTH, 1)
        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra("Name","Main")
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            2,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        val alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }


}