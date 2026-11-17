package com.sabir7718.shadowcap.utils

import android.content.Context
import android.content.SharedPreferences

class SYHaTE_PH(SABIR7718_CTX: Context) {

    private val SYHaTE_SP: SharedPreferences =
        SABIR7718_CTX.getSharedPreferences("shadowcap_prefs", Context.MODE_PRIVATE)

    companion object {
        const val S7_KSM = "shortcut_mode"
        const val S7_KAF = "audio_format"
        const val S7_KAQ = "audio_quality"
        const val S7_KST = "save_to"
        const val S7_KAE = "accessibility_enabled"
        const val S7_KHD = "hold_duration_ms"
        const val S7_KCAM = "default_camera"   // "front" or "back"
    }

    fun SYHaTE_GS(S7_K: String, S7_D: String = ""): String =
        SYHaTE_SP.getString(S7_K, S7_D) ?: S7_D

    fun SYHaTE_GB(S7_K: String, S7_D: Boolean = false): Boolean =
        SYHaTE_SP.getBoolean(S7_K, S7_D)

    fun SYHaTE_GI(S7_K: String, S7_D: Int = 0): Int =
        SYHaTE_SP.getInt(S7_K, S7_D)

    fun SYHaTE_SS(S7_K: String, S7_V: String) =
        SYHaTE_SP.edit().putString(S7_K, S7_V).apply()

    fun SYHaTE_SB(S7_K: String, S7_V: Boolean) =
        SYHaTE_SP.edit().putBoolean(S7_K, S7_V).apply()

    fun SYHaTE_SI(S7_K: String, S7_V: Int) =
        SYHaTE_SP.edit().putInt(S7_K, S7_V).apply()

    fun SABIR7718_GSM(): String = SYHaTE_GS(S7_KSM, "toggle")
    fun SABIR7718_GAF(): String = SYHaTE_GS(S7_KAF, "aac")
    fun SABIR7718_GAQ(): Int = SYHaTE_GI(S7_KAQ, 128)
    fun SABIR7718_GHD(): Int = SYHaTE_GI(S7_KHD, 800)

    /** "front" or "back" */
    fun SABIR7718_GCAM(): String = SYHaTE_GS(S7_KCAM, "back")
    fun SABIR7718_SCAM(S7_V: String) = SYHaTE_SS(S7_KCAM, S7_V)
}
