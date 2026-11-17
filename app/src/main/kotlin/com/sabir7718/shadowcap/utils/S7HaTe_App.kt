package com.sabir7718.shadowcap.utils

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class S7HaTe_App : Application() {

    companion object {
        const val S7_NCI = "shadowcap_recording"
    }

    override fun onCreate() {
        super.onCreate()
        S7HaTe_NC()
    }

    private fun S7HaTe_NC() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val S7_CH = NotificationChannel(
                S7_NCI,
                getString(com.sabir7718.shadowcap.R.string.notif_channel_name),
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                setShowBadge(false)
                setSound(null, null)
            }
            val S7_NM = getSystemService(NotificationManager::class.java)
            S7_NM.createNotificationChannel(S7_CH)
        }
    }
}
