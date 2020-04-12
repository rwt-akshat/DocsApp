package com.akrwt.docsapp


import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService


class MyFirebaseInstanceIDService : FirebaseMessagingService() {
    private val SUBSCRIBE_TO = "Doctor"

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)

        FirebaseMessaging.getInstance().subscribeToTopic(SUBSCRIBE_TO)
        Log.i("NEW_TOKEN",p0)
    }
}