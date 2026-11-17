package com.sabir7718.shadowcap.recording

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.sabir7718.shadowcap.R
import com.sabir7718.shadowcap.storage.SABIR7718_RS
import com.sabir7718.shadowcap.ui.S7HaTe_MA
import com.sabir7718.shadowcap.utils.S7HaTe_App
import com.sabir7718.shadowcap.utils.SYHaTE_PH

class S7HaTe_VS : Service() {

    companion object {
        const val S7_ACT_TG = "com.sabir7718.shadowcap.VIDEO_TOGGLE"
        const val S7_ACT_ST = "com.sabir7718.shadowcap.VIDEO_STOP"
        const val S7_NID    = 7719
        var SYHaTE_IV       = false
        var SYHaTE_UF       = false
    }

    private lateinit var S7HaTe_VR: S7HaTe_VR
    private lateinit var SABIR7718_RS: SABIR7718_RS
    private lateinit var SYHaTE_PH: SYHaTE_PH
    private val S7_MH = Handler(Looper.getMainLooper())

    override fun onCreate() {
        super.onCreate()
        SABIR7718_RS = SABIR7718_RS(this)
        S7HaTe_VR = S7HaTe_VR(this)
        SYHaTE_PH = SYHaTE_PH(this)
    }

    override fun onStartCommand(S7_INT: Intent?, S7_F: Int, S7_SID: Int): Int {
        when (S7_INT?.action) {
            S7_ACT_TG -> if (SYHaTE_IV) SYHaTeS7_SV() else SYHaTeS7_RV()
            S7_ACT_ST -> SYHaTeS7_SV()
        }
        return START_NOT_STICKY
    }

    private fun SYHaTeS7_RV() {
        startForeground(S7_NID, SABIR7718_BN("Starting…"))
        val useFront = SYHaTE_PH.SABIR7718_GCAM() == "front"
        val S7_FP = SABIR7718_RS.SYHaTE_NVF()
        S7HaTe_VR.SYHaTE_ST(S7_FP, useFront) { S7_OK ->
            S7_MH.post {
                if (S7_OK) {
                    SYHaTE_IV = true
                    SYHaTE_UF = S7HaTe_VR.SYHaTE_UF
                    SYHaTE_UN()
                } else {
                    SYHaTE_IV = false
                    stopForeground(STOP_FOREGROUND_REMOVE)
                    stopSelf()
                }
            }
        }
    }

    private fun SYHaTeS7_SV() {
        val S7_F = S7HaTe_VR.SYHaTE_SP()
        SYHaTE_IV = false
        if (S7_F != null) SABIR7718_RS.SYHaTE_SV(S7_F)
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
        sendBroadcast(Intent("com.sabir7718.shadowcap.REFRESH_LIST"))
    }

    private fun SYHaTE_UN() {
        val S7_NM = getSystemService(android.app.NotificationManager::class.java)
        val camLabel = if (SYHaTE_UF) "Front camera" else "Back camera"
        S7_NM.notify(S7_NID, SABIR7718_BN(camLabel))
    }

    private fun SABIR7718_BN(S7_SUB: String): Notification {
        val S7_OPI = PendingIntent.getActivity(
            this, 0, Intent(this, S7HaTe_MA::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val S7_STPI = PendingIntent.getService(
            this, 2, Intent(this, S7HaTe_VS::class.java).apply { action = S7_ACT_ST },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        return NotificationCompat.Builder(this, S7HaTe_App.S7_NCI)
            .setContentTitle("ShadowCap · Video  ·  $S7_SUB")
            .setContentText("Tap to open app")
            .setSmallIcon(R.drawable.ic_videocam)
            .setContentIntent(S7_OPI)
            .addAction(R.drawable.ic_stop, "Stop", S7_STPI)
            .setOngoing(true)
            .setSilent(true)
            .build()
    }

    override fun onBind(S7_INT: Intent?): IBinder? = null
}
