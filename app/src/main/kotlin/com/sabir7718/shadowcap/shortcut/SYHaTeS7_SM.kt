package com.sabir7718.shadowcap.shortcut

import android.content.Context
import android.view.KeyEvent
import com.sabir7718.shadowcap.utils.SYHaTE_PH

class SYHaTeS7_SM(
    private val SABIR7718_CTX: Context,
    private val S7_CB_AUDIO: () -> Unit,
    private val S7_CB_VIDEO: () -> Unit
) {

    private val SYHaTE_PH = SYHaTE_PH(SABIR7718_CTX)
    private val S7_DTW = 450L

    private var S7_LKC_DN = 0L
    private var S7_LKC_UP = 0L

    private var S7_HT = false
    private val SYHaTE_HD get() = SYHaTE_PH.SABIR7718_GHD().toLong()
    private val S7_HND = android.os.Handler(android.os.Looper.getMainLooper())

    private val SABIR7718_HMR_A = Runnable { if (S7_HT) { S7_HT = false; S7_CB_AUDIO() } }
    private val SABIR7718_HMR_V = Runnable { if (S7_HT) { S7_HT = false; S7_CB_VIDEO() } }

    fun SYHaTeS7_OKE(S7_KE: KeyEvent): Boolean {
        return when (SYHaTE_PH.SABIR7718_GSM()) {
            "hold"   -> SYHaTeS7_HM(S7_KE)
            "toggle" -> SYHaTeS7_TM(S7_KE)
            else     -> false
        }
    }

    private fun SYHaTeS7_HM(S7_KE: KeyEvent): Boolean {
        val S7_KC = S7_KE.keyCode
        if (S7_KC != KeyEvent.KEYCODE_VOLUME_UP && S7_KC != KeyEvent.KEYCODE_VOLUME_DOWN) return false
        val S7_HMR = if (S7_KC == KeyEvent.KEYCODE_VOLUME_DOWN) SABIR7718_HMR_A else SABIR7718_HMR_V
        return when (S7_KE.action) {
            KeyEvent.ACTION_DOWN -> {
                S7_HT = true
                S7_HND.postDelayed(S7_HMR, SYHaTE_HD)
                true
            }
            KeyEvent.ACTION_UP -> {
                S7_HT = false
                S7_HND.removeCallbacks(SABIR7718_HMR_A)
                S7_HND.removeCallbacks(SABIR7718_HMR_V)
                true
            }
            else -> false
        }
    }

    private fun SYHaTeS7_TM(S7_KE: KeyEvent): Boolean {
        if (S7_KE.action != KeyEvent.ACTION_DOWN) return false
        val S7_NOW = System.currentTimeMillis()
        return when (S7_KE.keyCode) {
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                val S7_IS_DP = S7_NOW - S7_LKC_DN < S7_DTW
                S7_LKC_DN = S7_NOW
                if (S7_IS_DP) { S7_LKC_DN = 0L; S7_CB_AUDIO(); true }
                else false
            }
            KeyEvent.KEYCODE_VOLUME_UP -> {
                val S7_IS_DP = S7_NOW - S7_LKC_UP < S7_DTW
                S7_LKC_UP = S7_NOW
                if (S7_IS_DP) { S7_LKC_UP = 0L; S7_CB_VIDEO(); true }
                else false
            }
            else -> false
        }
    }
}
