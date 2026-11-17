package com.sabir7718.shadowcap.accessibility

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent
import com.sabir7718.shadowcap.recording.S7_RS
import com.sabir7718.shadowcap.recording.S7HaTe_VS
import com.sabir7718.shadowcap.shortcut.SYHaTeS7_SM

class SABIR7718_VKS : AccessibilityService() {

    private lateinit var SYHaTeS7_SM: SYHaTeS7_SM

    override fun onCreate() {
        super.onCreate()
        SYHaTeS7_SM = SYHaTeS7_SM(
            this,
            S7_CB_AUDIO = { SABIR7718_TR_A() },
            S7_CB_VIDEO = { SABIR7718_TR_V() }
        )
    }

    override fun onKeyEvent(S7_KE: KeyEvent): Boolean =
        SYHaTeS7_SM.SYHaTeS7_OKE(S7_KE)

    private fun SABIR7718_TR_A() {
        startForegroundService(
            Intent(this, S7_RS::class.java).apply { action = S7_RS.S7_ACT_TG }
        )
    }

    private fun SABIR7718_TR_V() {
        startForegroundService(
            Intent(this, S7HaTe_VS::class.java).apply { action = S7HaTe_VS.S7_ACT_TG }
        )
    }

    override fun onAccessibilityEvent(S7_AE: AccessibilityEvent?) {}
    override fun onInterrupt() {}
}
