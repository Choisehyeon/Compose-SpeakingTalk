package com.example.compose_speakingtalk

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.compose.ComposespeakingtalkTheme
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : ComponentActivity() {

    private lateinit var textToSpeech: TextToSpeech
    private val onOffScreenViewModel: OnOffScreenViewModel by viewModels()
    private val audioManager by lazy { getSystemService(Context.AUDIO_SERVICE) as AudioManager }

    private val kakaoReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action.equals(NotificationListener.ACTION_TAG)) {
                val sender = intent?.getStringExtra("sender")
                val content = intent?.getStringExtra("content")
                if (onOffScreenViewModel.onOffScreenUIState.value.isOn) {
                    speak(sender, content)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTextToSpeech()
        initAudioVolume()
        initRingerMode()
        initCollect()
        if (!permissionGranted()) {
            val intent = Intent(
                "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"
            )
            startActivity(intent)
        }
        val permissions = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_NOTIFICATION_POLICY
        )
        ActivityCompat.requestPermissions(this, permissions, 0)
        setContent {
            ComposespeakingtalkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OnOffScreen(onOffScreenViewModel)
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

    private fun initAudioVolume() {
        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        onOffScreenViewModel.updateSliderValue(currentVolume.toFloat())
    }

    private fun initRingerMode() {
        onOffScreenViewModel.updateNotificationState(audioManager.ringerMode == AudioManager.RINGER_MODE_VIBRATE)
    }

    private fun initCollect() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                onOffScreenViewModel.onOffScreenUIState.collect {
                    textToSpeech.setSpeechRate(it.selectedSpeed.toFloat())
                    setAudioVolume(it.sliderValue)
                    setRingerMode(it.isNotificationChecked)
                }
            }
        }
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

    private fun setAudioVolume(value: Float) {
        audioManager.setStreamVolume(
            AudioManager.STREAM_MUSIC,
            (audioManager.getStreamMaxVolume(AudioManager.STREAM_RING) * value / 100.0).toInt(),
            0
        )
    }

    private fun setRingerMode(value: Boolean) {
        audioManager.ringerMode = if (value) AudioManager.RINGER_MODE_VIBRATE else AudioManager.RINGER_MODE_NORMAL
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


