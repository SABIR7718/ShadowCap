package com.sabir7718.shadowcap.recording

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.media.MediaRecorder
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.view.Surface
import java.io.File

class S7HaTe_VR(private val SABIR7718_CTX: Context) {

    private var SABIR7718_MR: MediaRecorder? = null
    private var S7_CD: CameraDevice? = null
    private var S7_CS: CameraCaptureSession? = null
    private var S7_OF: File? = null
    private var S7_HT: HandlerThread? = null
    private var S7_H: Handler? = null

    var SYHaTE_UF = false   // true = front

    @SuppressLint("MissingPermission")
    fun SYHaTE_ST(S7_FP: File, useFront: Boolean, S7_OC: (Boolean) -> Unit) {
        SYHaTE_UF = useFront
        S7_OF = S7_FP
        S7_HT = HandlerThread("S7_CamThread").also { it.start() }
        S7_H = Handler(S7_HT!!.looper)

        val S7_CM = SABIR7718_CTX.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val S7_CID = SYHaTE_GCI(S7_CM)

        SABIR7718_MR = SYHaTE_BMR(S7_FP)

        S7_CM.openCamera(S7_CID, object : CameraDevice.StateCallback() {
            override fun onOpened(S7_D: CameraDevice) {
                S7_CD = S7_D
                SYHaTE_SC(S7_D, S7_OC)
            }
            override fun onDisconnected(S7_D: CameraDevice) {
                S7_D.close(); S7_OC(false)
            }
            override fun onError(S7_D: CameraDevice, S7_E: Int) {
                S7_D.close(); S7_OC(false)
            }
        }, S7_H)
    }

    private fun SYHaTE_SC(S7_D: CameraDevice, S7_OC: (Boolean) -> Unit) {
        val S7_MRS = SABIR7718_MR?.surface ?: run { S7_OC(false); return }
        val S7_ST = SurfaceTexture(1).also { it.setDefaultBufferSize(1280, 720) }
        val S7_PS = Surface(S7_ST)

        val S7_RB = S7_D.createCaptureRequest(CameraDevice.TEMPLATE_RECORD).apply {
            addTarget(S7_MRS)
            addTarget(S7_PS)
            set(CaptureRequest.CONTROL_MODE, CaptureRequest.CONTROL_MODE_AUTO)
            set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_VIDEO)
            set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON)
        }

        @Suppress("DEPRECATION")
        S7_D.createCaptureSession(
            listOf(S7_MRS, S7_PS),
            object : CameraCaptureSession.StateCallback() {
                override fun onConfigured(S7_SS: CameraCaptureSession) {
                    S7_CS = S7_SS
                    try {
                        S7_SS.setRepeatingRequest(S7_RB.build(), null, S7_H)
                        SABIR7718_MR?.start()
                        S7_OC(true)
                    } catch (S7_E: Exception) {
                        S7_OC(false)
                    }
                }
                override fun onConfigureFailed(S7_SS: CameraCaptureSession) { S7_OC(false) }
            },
            S7_H
        )
    }

    private fun SYHaTE_BMR(S7_FP: File): MediaRecorder {
        return (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            MediaRecorder(SABIR7718_CTX)
        else
            @Suppress("DEPRECATION") MediaRecorder()
        ).apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setVideoSource(MediaRecorder.VideoSource.SURFACE)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setVideoEncoder(MediaRecorder.VideoEncoder.H264)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setVideoSize(1280, 720)
            setVideoFrameRate(30)
            setVideoEncodingBitRate(4_000_000)
            setAudioEncodingBitRate(128_000)
            setAudioSamplingRate(44100)
            setOutputFile(S7_FP.absolutePath)
            prepare()
        }
    }

    fun SYHaTE_SP(): File? {
        return try {
            S7_CS?.stopRepeating()
            S7_CS?.close()
            SABIR7718_MR?.apply { stop(); release() }
            S7_CD?.close()
            S7_HT?.quitSafely()
            S7_CS = null; SABIR7718_MR = null; S7_CD = null; S7_HT = null; S7_H = null
            S7_OF
        } catch (S7_E: Exception) {
            S7_OF?.delete()
            null
        }
    }

    fun S7_IS(): Boolean = SABIR7718_MR != null

    private fun SYHaTE_GCI(S7_CM: CameraManager): String {
        return S7_CM.cameraIdList.firstOrNull { S7_ID ->
            val S7_LENS = S7_CM.getCameraCharacteristics(S7_ID)
                .get(CameraCharacteristics.LENS_FACING)
            if (SYHaTE_UF) S7_LENS == CameraCharacteristics.LENS_FACING_FRONT
            else S7_LENS == CameraCharacteristics.LENS_FACING_BACK
        } ?: S7_CM.cameraIdList.firstOrNull() ?: "0"
    }
}
