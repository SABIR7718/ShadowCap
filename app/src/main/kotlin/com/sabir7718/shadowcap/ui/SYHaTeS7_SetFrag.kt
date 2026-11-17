package com.sabir7718.shadowcap.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.sabir7718.shadowcap.R
import com.sabir7718.shadowcap.storage.SABIR7718_RS
import com.sabir7718.shadowcap.utils.SYHaTE_PH

class SYHaTeS7_SetFrag : Fragment() {

    private lateinit var prefs: SYHaTE_PH

    override fun onCreateView(S7_LI: LayoutInflater, S7_VG: ViewGroup?, S7_SIS: Bundle?): View =
        S7_LI.inflate(R.layout.frag_settings, S7_VG, false)

    override fun onViewCreated(S7_V: View, S7_SIS: Bundle?) {
        super.onViewCreated(S7_V, S7_SIS)
        prefs = SYHaTE_PH(requireContext())
        SYHaTE_LV(S7_V)
        S7HaTe_SL(S7_V)
        val path = SABIR7718_RS(requireContext()).SYHaTE_GET_EXT_PATH()
        S7_V.findViewById<TextView>(R.id.tv_storage_path).text = path
    }

    private fun SYHaTE_LV(S7_V: View) {
        // Camera
        S7_V.findViewById<RadioGroup>(R.id.rg_camera).check(
            if (prefs.SABIR7718_GCAM() == "front") R.id.rb_cam_front else R.id.rb_cam_back
        )
        // Shortcut mode
        S7_V.findViewById<RadioGroup>(R.id.rg_shortcut_mode).check(
            if (prefs.SABIR7718_GSM() == "hold") R.id.rb_hold else R.id.rb_toggle
        )
        val S7_HD = prefs.SABIR7718_GHD()
        S7_V.findViewById<SeekBar>(R.id.sb_hold_duration).progress = (S7_HD / 100) - 3
        S7_V.findViewById<TextView>(R.id.tv_hold_ms).text = "${S7_HD}ms"
    }

    private fun S7HaTe_SL(S7_V: View) {
        S7_V.findViewById<RadioGroup>(R.id.rg_camera)
            .setOnCheckedChangeListener { _, S7_CID ->
                prefs.SABIR7718_SCAM(if (S7_CID == R.id.rb_cam_front) "front" else "back")
            }
        S7_V.findViewById<RadioGroup>(R.id.rg_shortcut_mode)
            .setOnCheckedChangeListener { _, S7_CID ->
                prefs.SYHaTE_SS(SYHaTE_PH.S7_KSM, if (S7_CID == R.id.rb_hold) "hold" else "toggle")
            }
        val S7_SB = S7_V.findViewById<SeekBar>(R.id.sb_hold_duration)
        val S7_TV = S7_V.findViewById<TextView>(R.id.tv_hold_ms)
        S7_SB.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(S7_S: SeekBar, S7_P: Int, S7_FU: Boolean) {
                val S7_MS = (S7_P + 3) * 100
                S7_TV.text = "${S7_MS}ms"
                if (S7_FU) prefs.SYHaTE_SI(SYHaTE_PH.S7_KHD, S7_MS)
            }
            override fun onStartTrackingTouch(S7_S: SeekBar) {}
            override fun onStopTrackingTouch(S7_S: SeekBar) {
                prefs.SYHaTE_SI(SYHaTE_PH.S7_KHD, (S7_S.progress + 3) * 100)
            }
        })
    }
}
