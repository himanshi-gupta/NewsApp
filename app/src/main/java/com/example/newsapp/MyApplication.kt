package com.example.newsapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
//import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import java.util.prefs.Preferences

//Create Singleton instance of DataStore Preference
val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "LocalStore")
class MyApplication : Application() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        //Let's call the function.
        createNotificationChannel()
    }

    //Create Notification Channel.
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(){
        val name = "JetpackPushNotification"
        val description ="Jetpack Push Notification"
        val importance = NotificationManager.IMPORTANCE_DEFAULT

        //Now Create Notification Channel.
        // it take three parameters. notification id,name, and importance.
        val channel = NotificationChannel("Global",name,importance)
        channel.description = description;

        // Get Notification Manager.
        val notificationManager : NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //Lets Create Notification channel.
        notificationManager.createNotificationChannel(channel)

    }

}