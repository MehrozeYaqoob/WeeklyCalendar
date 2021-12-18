package com.parim.weeklycalendar.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.parim.weeklycalendar.BuildConfig
import com.parim.weeklycalendar.databinding.ActivitySplashBinding
import com.parim.weeklycalendar.manager.PreferenceManager
import com.parim.weeklycalendar.utils.Constants
import com.parim.weeklycalendar.utils.Constants.FIREBASE_KEY_REFRESH_LAPSE
import com.parim.weeklycalendar.utils.Constants.SPLASH_TIME
import java.util.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onInitFirebase()
    }

    private fun onInitFirebase(){
        val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        firebaseRemoteConfig.setDefaultsAsync(mapOf(FIREBASE_KEY_REFRESH_LAPSE to 10))
        val configBuilder = FirebaseRemoteConfigSettings.Builder()
        if (BuildConfig.DEBUG) {
            val cacheInterval: Long = 0
            configBuilder.minimumFetchIntervalInSeconds = cacheInterval
        }
        firebaseRemoteConfig.setConfigSettingsAsync(configBuilder.build())

        firebaseRemoteConfig.fetchAndActivate()
            .addOnCompleteListener(this@SplashActivity) { task ->
                if (task.isSuccessful) {
                    PreferenceManager.setDaysAfterRemoteDataFetched(this,firebaseRemoteConfig.getLong(FIREBASE_KEY_REFRESH_LAPSE))
                    onStartSplashHandler()
                }
            }.addOnFailureListener {
                onStartSplashHandler()
            }
    }

    private fun onStartSplashHandler(){
        Handler(Looper.getMainLooper()).postDelayed({
            val intent  =  Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }, SPLASH_TIME.toLong())
    }
}
