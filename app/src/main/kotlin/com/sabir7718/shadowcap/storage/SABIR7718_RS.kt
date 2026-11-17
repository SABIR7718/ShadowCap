package com.sabir7718.shadowcap.storage

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class SYHaTE_RT { AUDIO, VIDEO }

data class SYHaTE_RM(
    val S7_ID: String,
    val S7_FP: String,
    val S7_TS: Long,
    val S7_SZ: Long = 0L,
    val S7_TYPE: SYHaTE_RT = SYHaTE_RT.AUDIO
)

class SABIR7718_RS(private val SABIR7718_CTX: Context) {

    private val S7_DIR_A: File by lazy {
        File(SABIR7718_CTX.filesDir, "recordings/audio").also { it.mkdirs() }
    }
    private val S7_DIR_V: File by lazy {
        File(SABIR7718_CTX.filesDir, "recordings/video").also { it.mkdirs() }
    }

    /** Public folder: /sdcard/ShadowCap/ */
    private val S7_EXT_ROOT: File by lazy {
        File(Environment.getExternalStorageDirectory(), "ShadowCap").also { it.mkdirs() }
    }
    private val S7_EXT_A: File by lazy {
        File(S7_EXT_ROOT, "Audio").also { it.mkdirs() }
    }
    private val S7_EXT_V: File by lazy {
        File(S7_EXT_ROOT, "Video").also { it.mkdirs() }
    }

    private val SYHaTE_DF = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())

    fun SYHaTE_NF(): File =
        File(S7_DIR_A, "AUD_${SYHaTE_DF.format(Date())}.aac")

    fun SYHaTE_NVF(): File =
        File(S7_DIR_V, "VID_${SYHaTE_DF.format(Date())}.mp4")

    fun SYHaTE_SR(S7_F: File) {}
    fun SYHaTE_SV(S7_F: File) {}

    fun SABIR7718_GRL(): List<SYHaTE_RM> {
        val S7_AU = S7_DIR_A.listFiles()
            ?.filter { it.extension == "aac" || it.extension == "m4a" }
            ?.map { S7HaTe_FM(it, SYHaTE_RT.AUDIO) }
            ?: emptyList()
        val S7_VU = S7_DIR_V.listFiles()
            ?.filter { it.extension == "mp4" }
            ?.map { S7HaTe_FM(it, SYHaTE_RT.VIDEO) }
            ?: emptyList()
        return (S7_AU + S7_VU).sortedByDescending { it.S7_TS }
    }

    private fun S7HaTe_FM(S7_F: File, S7_T: SYHaTE_RT): SYHaTE_RM =
        SYHaTE_RM(
            S7_ID = S7_F.nameWithoutExtension,
            S7_FP = S7_F.absolutePath,
            S7_TS = S7_F.lastModified(),
            S7_SZ = S7_F.length(),
            S7_TYPE = S7_T
        )

    fun S7_DR(S7_ID: String): Boolean {
        listOf(
            File(S7_DIR_A, "$S7_ID.aac"),
            File(S7_DIR_A, "$S7_ID.m4a"),
            File(S7_DIR_V, "$S7_ID.mp4")
        ).forEach { if (it.exists()) return it.delete() }
        return false
    }

    /** Copy recording to /sdcard/ShadowCap/Audio or Video */
    fun SYHaTE_STD(S7_RM: SYHaTE_RM): Boolean {
        return try {
            val S7_SRC = File(S7_RM.S7_FP)
            if (!S7_SRC.exists()) return false

            val S7_DEST_DIR = if (S7_RM.S7_TYPE == SYHaTE_RT.VIDEO) S7_EXT_V else S7_EXT_A
            val S7_EXT = S7_SRC.extension
            val S7_DEST = File(S7_DEST_DIR, "${S7_RM.S7_ID}.$S7_EXT")

            FileInputStream(S7_SRC).use { S7_IN ->
                FileOutputStream(S7_DEST).use { S7_OUT ->
                    S7_IN.copyTo(S7_OUT)
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun SYHaTE_GET_EXT_PATH(): String = S7_EXT_ROOT.absolutePath

    fun SYHaTE_FS(S7_B: Long): String = when {
        S7_B < 1024 -> "$S7_B B"
        S7_B < 1024 * 1024 -> "${"%.1f".format(S7_B / 1024.0)} KB"
        else -> "${"%.1f".format(S7_B / (1024.0 * 1024))} MB"
    }

    fun SYHaTE_FT(S7_MS: Long): String {
        val S7_S = S7_MS / 1000; val S7_M = S7_S / 60; val S7_H = S7_M / 60
        return if (S7_H > 0) "%d:%02d:%02d".format(S7_H, S7_M % 60, S7_S % 60)
        else "%02d:%02d".format(S7_M, S7_S % 60)
    }
}
