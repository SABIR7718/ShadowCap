package com.sabir7718.shadowcap.ui

import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.sabir7718.shadowcap.R
import com.sabir7718.shadowcap.recording.S7HaTe_VS
import com.sabir7718.shadowcap.recording.S7_RS
import com.sabir7718.shadowcap.storage.SABIR7718_RS

class SYHaTE_DashFrag : Fragment() {

    private lateinit var SABIR7718_RS: SABIR7718_RS
    private var SYHaTE_UH: Handler? = null
    private var S7_UR: Runnable? = null

    override fun onCreateView(S7_LI: LayoutInflater, S7_VG: ViewGroup?, S7_SIS: Bundle?): View =
        S7_LI.inflate(R.layout.frag_dashboard, S7_VG, false)

    override fun onViewCreated(S7_V: View, S7_SIS: Bundle?) {
        super.onViewCreated(S7_V, S7_SIS)
        SABIR7718_RS = SABIR7718_RS(requireContext())
        SYHaTE_UH = Handler(Looper.getMainLooper())
        S7HaTe_SV(S7_V)
        SYHaTE_SU(S7_V)
    }

    private fun S7HaTe_SV(S7_V: View) {
        S7_V.findViewById<MaterialButton>(R.id.btn_record).setOnClickListener {
            requireContext().startForegroundService(
                Intent(requireContext(), S7_RS::class.java).apply { action = S7_RS.S7_ACT_TG }
            )
        }
        S7_V.findViewById<MaterialButton>(R.id.btn_video).setOnClickListener {
            requireContext().startForegroundService(
                Intent(requireContext(), S7HaTe_VS::class.java).apply { action = S7HaTe_VS.S7_ACT_TG }
            )
        }
        S7_V.findViewById<MaterialCardView>(R.id.card_accessibility).setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }
    }

    private fun SYHaTE_SU(S7_V: View) {
        S7_UR = Runnable {
            if (!isAdded) return@Runnable
            SABIR7718_URS(S7_V)
            SYHaTE_UH?.postDelayed(S7_UR!!, 500)
        }
        SYHaTE_UH?.post(S7_UR!!)
    }

    private fun SABIR7718_URS(S7_V: View) {
        val S7_IA = S7_RS.SYHaTE_IR
        val S7_IV = S7HaTe_VS.SYHaTE_IV
        val S7_AE = SYHaTE_IAE()
        val S7_RC = SABIR7718_RS.SABIR7718_GRL().size

        S7_V.findViewById<MaterialButton>(R.id.btn_record).apply {
            text = if (S7_IA) "Stop Audio" else "Start Audio"
            backgroundTintList = ContextCompat.getColorStateList(
                requireContext(), if (S7_IA) R.color.void_red else R.color.void_accent
            )
        }

        S7_V.findViewById<MaterialButton>(R.id.btn_video).apply {
            text = if (S7_IV) "Stop Video" else "Start Video"
            backgroundTintList = ContextCompat.getColorStateList(
                requireContext(), if (S7_IV) R.color.void_red else R.color.void_purple
            )
        }

        S7_V.findViewById<View>(R.id.indicator_audio).visibility =
            if (S7_IA) View.VISIBLE else View.INVISIBLE
        S7_V.findViewById<View>(R.id.indicator_video).visibility =
            if (S7_IV) View.VISIBLE else View.INVISIBLE

        S7_V.findViewById<TextView>(R.id.tv_status).text = when {
            S7_IA && S7_IV -> "Audio + Video recording"
            S7_IA          -> "Audio recording…"
            S7_IV          -> {
                val cam = if (S7HaTe_VS.SYHaTE_UF) "Front" else "Back"
                "Video recording… ($cam)"
            }
            else           -> "Idle"
        }

        S7_V.findViewById<TextView>(R.id.tv_rec_count).text = "$S7_RC files"

        S7_V.findViewById<View>(R.id.dot_accessibility).backgroundTintList =
            ContextCompat.getColorStateList(
                requireContext(), if (S7_AE) R.color.void_green else R.color.void_red
            )
        S7_V.findViewById<TextView>(R.id.tv_accessibility).text =
            if (S7_AE) "Accessibility: Active" else "Accessibility: Tap to enable"
    }

    private fun SYHaTE_IAE(): Boolean {
        val S7_AM = requireContext().getSystemService(AccessibilityManager::class.java)
        return S7_AM.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
            .any { it.id.contains("sabir7718") }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        S7_UR?.let { SYHaTE_UH?.removeCallbacks(it) }
        SYHaTE_UH = null
        S7_UR = null
    }
}
