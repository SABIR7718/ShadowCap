package com.sabir7718.shadowcap.recording

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.sabir7718.shadowcap.R
import com.sabir7718.shadowcap.storage.SABIR7718_RS
import com.sabir7718.shadowcap.ui.S7HaTe_MA
import com.sabir7718.shadowcap.utils.S7HaTe_App

class S7_RS : Service() {

    companion object {
        const val S7_ACT_TG = "com.sabir7718.shadowcap.TOGGLE"
        const val S7_ACT_ST = "com.sabir7718.shadowcap.STOP"
        const val S7_NID = 7718
        var SYHaTE_IR = false
    }

    private val S7HaTe_AR = S7HaTe_AR()
    private lateinit var SABIR7718_RS: SABIR7718_RS

    override fun onCreate() {
        super.onCreate()
        SABIR7718_RS = SABIR7718_RS(this)
    }

    override fun onStartCommand(S7_INT: Intent?, S7_F: Int, S7_SID: Int): Int {
        when (S7_INT?.action) {
            S7_ACT_TG -> if (SYHaTE_IR) SYHaTeS7_SR() else SYHaTeS7_SS()
            S7_ACT_ST -> SYHaTeS7_SR()
        }
        return START_NOT_STICKY
    }

    private fun SYHaTeS7_SS() {
        val S7_FP = SABIR7718_RS.SYHaTE_NF()
        S7HaTe_AR.SYHaTE_ST(S7_FP)
        SYHaTE_IR = true
        startForeground(S7_NID, SABIR7718_BN())
    }

    private fun SYHaTeS7_SR() {
        val S7_F = S7HaTe_AR.SYHaTE_SP()
        SYHaTE_IR = false
        if (S7_F != null) {
            SABIR7718_RS.SYHaTE_SR(S7_F)
        }
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
        S7HaTe_RS()
    }

    private fun SABIR7718_BN(): Notification {
        val S7_OI = Intent(this, S7HaTe_MA::class.java)
        val S7_OPI = PendingIntent.getActivity(
            this, 0, S7_OI,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val S7_STI = Intent(this, S7_RS::class.java).apply { action = S7_ACT_ST }
        val S7_STPI = PendingIntent.getService(
            this, 1, S7_STI,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        return NotificationCompat.Builder(this, S7HaTe_App.S7_NCI)
            .setContentTitle(getString(R.string.notif_title))
            .setContentText(getString(R.string.notif_text))
            .setSmallIcon(R.drawable.ic_mic)
            .setContentIntent(S7_OPI)
            .addAction(R.drawable.ic_stop, getString(R.string.stop_action), S7_STPI)
            .setOngoing(true)
            .setSilent(true)
            .build()
    }

    private fun S7HaTe_RS() {
        sendBroadcast(Intent("com.sabir7718.shadowcap.REFRESH_LIST"))
    }

    override fun onBind(S7_INT: Intent?): IBinder? = null
}
