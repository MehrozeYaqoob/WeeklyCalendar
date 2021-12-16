package com.parim.weeklycalendar.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.parim.weeklycalendar.BuildConfig
import com.parim.weeklycalendar.databinding.ActivitySplashBinding
import com.parim.weeklycalendar.manager.PreferenceManager
import com.parim.weeklycalendar.utils.Constants
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
        firebaseRemoteConfig.setDefaultsAsync(mapOf("refresh_lapse" to 10))
        val configBuilder = FirebaseRemoteConfigSettings.Builder()
        if (BuildConfig.DEBUG) {
            val cacheInterval: Long = 0
            configBuilder.minimumFetchIntervalInSeconds = cacheInterval
        }
        firebaseRemoteConfig.setConfigSettingsAsync(configBuilder.build())

        firebaseRemoteConfig.fetchAndActivate()
            .addOnCompleteListener(this@SplashActivity) { task ->
                if (task.isSuccessful) {
                    PreferenceManager.setDaysAfterRemoteDataFetched(this,firebaseRemoteConfig.getLong(Constants.FIREBASE_KEY_REFRESH_LAPSE))
                    val intent  =  Intent(this, CalendarActivity::class.java)
                    startActivity(intent)
                }
            }
    }
}