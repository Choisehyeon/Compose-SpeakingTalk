package com.example.compose_speakingtalk

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
import com.example.compose.ComposespeakingtalkTheme
import java.util.Locale

class MainActivity : ComponentActivity() {

    private val textToSpeech: TextToSpeech by lazy {
        TextToSpeech(this) {
            if (it != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale.KOREAN)
            }
        }
    }

    private val kakaoReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent?.action.equals(NotificationListener.ACTION_TAG)) {
                val sender = intent?.getStringExtra("sender")
                val content = intent?.getStringExtra("content")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onResume() {
        super.onResume()
        registerReceiver(kakaoReceiver, IntentFilter(NotificationListener.ACTION_TAG),
            RECEIVER_NOT_EXPORTED
        )
    }

}


