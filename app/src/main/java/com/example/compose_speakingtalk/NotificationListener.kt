package com.example.compose_speakingtalk

import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.core.app.NotificationCompat

class NotificationListener : NotificationListenerService() {

    override fun onListenerConnected() {
        super.onListenerConnected()
        Log.e("speakingtalk", "MyNotificationListener.onListenerConnected()")
    }

    override fun onListenerDisconnected() {
        super.onListenerDisconnected()
        Log.e("speakingtalk", "MyNotificationListener.onListenerDisconnected()")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        if (sbn?.packageName.equals("com.kakao.talk")) {
            val bundle = sbn?.notification?.extras
            if (bundle != null) {
                val sender = bundle.getString(NotificationCompat.EXTRA_TITLE) + "님이 카톡을 보냈습니다."
                val content = bundle.getString(NotificationCompat.EXTRA_TEXT) ?: ""
                sendToActivity(sender, content)
            }
        }
    }

    private fun sendToActivity(sender: String, content: String) {
        val intent = Intent(ACTION_TAG).apply {
            putExtra("sender", sender)
            putExtra("content", content)
        }
        sendBroadcast(intent)
    }

    companion object {
        const val ACTION_TAG = "com.notificationListenerService"
    }
}
