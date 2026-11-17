package com.sabir7718.shadowcap.recording

import android.media.MediaRecorder
import android.os.Build
import java.io.File

class S7HaTe_AR {

    private var SABIR7718_MR: MediaRecorder? = null
    private var S7_OF: File? = null

    fun SYHaTE_ST(S7_FP: File, S7_BR: Int = 128000) {
        S7_OF = S7_FP
        SABIR7718_MR = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setAudioEncodingBitRate(S7_BR)
            setAudioSamplingRate(44100)
            setOutputFile(S7_FP.absolutePath)
            prepare()
            start()
        }
    }

    fun SYHaTE_SP(): File? {
        return try {
            SABIR7718_MR?.apply {
                stop()
                release()
            }
            SABIR7718_MR = null
            S7_OF
        } catch (S7_E: Exception) {
            S7_OF?.delete()
            null
        }
    }

    fun SYHaTE_GA(): Int = try {
        SABIR7718_MR?.maxAmplitude ?: 0
    } catch (S7_E: Exception) { 0 }

    fun S7_IS(): Boolean = SABIR7718_MR != null
}
