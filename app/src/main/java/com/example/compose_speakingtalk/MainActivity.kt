package com.example.compose_speakingtalk

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.example.compose.ComposespeakingtalkTheme
import java.util.Locale

class MainActivity : ComponentActivity() {

    private lateinit var textToSpeech: TextToSpeech

    private val kakaoReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action.equals(NotificationListener.ACTION_TAG)) {
                val sender = intent?.getStringExtra("sender")
                val content = intent?.getStringExtra("content")
                speak(sender, content)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTextToSpeech()
        if (!permissionGranted()) {
            val intent = Intent(
                "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"
            )
            startActivity(intent)
        }
        val permissions = arrayOf(Manifest.permission.RECORD_AUDIO)
        ActivityCompat.requestPermissions(this, permissions, 0)
        setContent {
            ComposespeakingtalkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OnOffScreen()
                }
            }
        }

    }

    private fun setTextToSpeech() {
        textToSpeech = TextToSpeech(this) {
            if (it != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale.KOREAN)
                textToSpeech.setSpeechRate(1f)
            }
        }
    }

    private fun permissionGranted(): Boolean {
        val sets = NotificationManagerCompat.getEnabledListenerPackages(this)
        return sets.contains(packageName)
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onResume() {
        super.onResume()
        registerReceiver(
            kakaoReceiver, IntentFilter(NotificationListener.ACTION_TAG),
            RECEIVER_NOT_EXPORTED
        )
    }

    private fun speak(sender: String?, content: String?) {
        val result = sender + content
        textToSpeech.speak(result, 0, null, null)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(kakaoReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}


