package com.example.newsapp

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        val  tokenData : Map<String, Any> = mapOf("token" to token)
        val firestore : FirebaseFirestore =  FirebaseFirestore.getInstance()
        firestore.collection("DeviceTokens").document().set(tokenData)
    }

}