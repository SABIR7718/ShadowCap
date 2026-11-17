package com.sabir7718.shadowcap.ui

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sabir7718.shadowcap.R
import com.sabir7718.shadowcap.storage.SABIR7718_RS
import com.sabir7718.shadowcap.storage.SYHaTE_RM
import com.sabir7718.shadowcap.storage.SYHaTE_RT
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SABIR7718_RecFrag : Fragment() {

    private lateinit var S7_RST: SABIR7718_RS
    private lateinit var SABIR7718_RA: S7_RecAdapter
    private var SYHaTE_MP: MediaPlayer? = null
    private var videoDialog: Dialog? = null

    private val S7_RR = object : BroadcastReceiver() {
        override fun onReceive(S7_C: Context, S7_I: Intent) { SYHaTE_RL() }
    }

    override fun onCreateView(S7_LI: LayoutInflater, S7_VG: ViewGroup?, S7_SIS: Bundle?): View =
        S7_LI.inflate(R.layout.frag_recordings, S7_VG, false)

    override fun onViewCreated(S7_V: View, S7_SIS: Bundle?) {
        super.onViewCreated(S7_V, S7_SIS)
        S7_RST = SABIR7718_RS(requireContext())
        SABIR7718_RA = S7_RecAdapter(
            onPlay   = { S7_RM -> SABIR7718_PR(S7_RM) },
            onDelete = { S7_RM -> SYHaTE_CD(S7_RM) },
            onSave   = { S7_RM -> SYHaTE_SAVE(S7_RM) },
            S7_RST
        )
        S7_V.findViewById<RecyclerView>(R.id.rv_recordings).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = SABIR7718_RA
        }
        SYHaTE_RL()
        requireContext().registerReceiver(
            S7_RR, IntentFilter("com.sabir7718.shadowcap.REFRESH_LIST"),
            Context.RECEIVER_NOT_EXPORTED
        )
    }

    private fun SYHaTE_RL() {
        val list = S7_RST.SABIR7718_GRL()
        SABIR7718_RA.SYHaTE_SL(list)
        view?.findViewById<TextView>(R.id.tv_empty)?.visibility =
            if (list.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun SABIR7718_PR(S7_RM: SYHaTE_RM) {
        if (S7_RM.S7_TYPE == SYHaTE_RT.VIDEO) {
            playVideoInApp(S7_RM.S7_FP)
            return
        }
        // Audio
        try {
            SYHaTE_MP?.release()
            SYHaTE_MP = MediaPlayer().apply {
                setDataSource(S7_RM.S7_FP)
                prepare()
                start()
            }
            Toast.makeText(requireContext(), "Playing audio…", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Play failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun playVideoInApp(path: String) {
        try {
            videoDialog?.dismiss()
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_video_player)
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            val videoView = dialog.findViewById<VideoView>(R.id.video_view)
            val btnClose = dialog.findViewById<ImageView>(R.id.btn_close_player)

            videoView.setVideoPath(path)
            videoView.setOnPreparedListener { mp ->
                mp.isLooping = false
                videoView.start()
            }
            videoView.setOnCompletionListener {
                // keep dialog open so user can replay by tapping if needed
            }
            videoView.setOnErrorListener { _, _, _ ->
                Toast.makeText(requireContext(), "Cannot play this video", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                true
            }

            btnClose.setOnClickListener {
                videoView.stopPlayback()
                dialog.dismiss()
            }

            dialog.setOnDismissListener {
                videoView.stopPlayback()
            }

            videoDialog = dialog
            dialog.show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Video play error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun SYHaTE_SAVE(S7_RM: SYHaTE_RM) {
        val ok = S7_RST.SYHaTE_STD(S7_RM)
        if (ok) {
            Toast.makeText(
                requireContext(),
                "Saved to ${S7_RST.SYHaTE_GET_EXT_PATH()}",
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(requireContext(), "Save failed. Check storage permission.", Toast.LENGTH_LONG).show()
        }
    }

    private fun SYHaTE_CD(S7_RM: SYHaTE_RM) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete file?")
            .setMessage(S7_RM.S7_ID)
            .setPositiveButton("Delete") { _, _ ->
                S7_RST.S7_DR(S7_RM.S7_ID)
                SYHaTE_RL()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        SYHaTE_MP?.release()
        videoDialog?.dismiss()
        requireContext().unregisterReceiver(S7_RR)
    }
}

class S7_RecAdapter(
    private val onPlay: (SYHaTE_RM) -> Unit,
    private val onDelete: (SYHaTE_RM) -> Unit,
    private val onSave: (SYHaTE_RM) -> Unit,
    private val S7_RST: SABIR7718_RS
) : RecyclerView.Adapter<S7_RecAdapter.SYHaTE_VH>() {

    private var SABIR7718_RL: List<SYHaTE_RM> = emptyList()
    private val S7_DF = SimpleDateFormat("dd MMM yyyy  HH:mm", Locale.getDefault())

    fun SYHaTE_SL(S7_L: List<SYHaTE_RM>) {
        SABIR7718_RL = S7_L
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(S7_VG: ViewGroup, S7_VT: Int): SYHaTE_VH =
        SYHaTE_VH(LayoutInflater.from(S7_VG.context).inflate(R.layout.item_recording, S7_VG, false))

    override fun onBindViewHolder(S7_VH: SYHaTE_VH, S7_P: Int) = S7_VH.S7_B(SABIR7718_RL[S7_P])
    override fun getItemCount() = SABIR7718_RL.size

    inner class SYHaTE_VH(S7_V: View) : RecyclerView.ViewHolder(S7_V) {
        fun S7_B(S7_RM: SYHaTE_RM) {
            val S7_IS_V = S7_RM.S7_TYPE == SYHaTE_RT.VIDEO
            itemView.findViewById<TextView>(R.id.tv_rec_name).text = S7_RM.S7_ID
            itemView.findViewById<TextView>(R.id.tv_rec_date).text = S7_DF.format(Date(S7_RM.S7_TS))
            itemView.findViewById<TextView>(R.id.tv_rec_size).text = S7_RST.SYHaTE_FS(S7_RM.S7_SZ)
            itemView.findViewById<ImageView>(R.id.iv_type_icon).apply {
                setImageResource(if (S7_IS_V) R.drawable.ic_videocam else R.drawable.ic_mic)
                imageTintList = android.content.res.ColorStateList.valueOf(
                    itemView.context.getColor(if (S7_IS_V) R.color.void_purple else R.color.void_accent)
                )
            }
            itemView.findViewById<View>(R.id.btn_play).apply {
                alpha = 1f
                setOnClickListener { onPlay(S7_RM) }
            }
            itemView.findViewById<View>(R.id.btn_save).setOnClickListener { onSave(S7_RM) }
            itemView.findViewById<View>(R.id.btn_delete).setOnClickListener { onDelete(S7_RM) }
        }
    }
}
