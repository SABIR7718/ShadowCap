package com.sabir7718.shadowcap.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sabir7718.shadowcap.R

class S7HaTe_MA : AppCompatActivity() {

    private val SYHaTE_PLR = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {}

    override fun onCreate(S7_SIS: Bundle?) {
        super.onCreate(S7_SIS)
        setContentView(R.layout.activity_main)
        SYHaTE_CP()
        SYHaTE_REQ_STORAGE()
        SYHaTeS7_SBN()
        SABIR7718_LF(SYHaTE_DF())
    }

    private fun SYHaTE_CP() {
        val S7_PERMS = mutableListOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
        )
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            S7_PERMS.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            S7_PERMS.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            S7_PERMS.add(Manifest.permission.READ_MEDIA_AUDIO)
            S7_PERMS.add(Manifest.permission.READ_MEDIA_VIDEO)
        }
        val need = S7_PERMS.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()
        if (need.isNotEmpty()) SYHaTE_PLR.launch(need)
    }

    private fun SYHaTE_REQ_STORAGE() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                try {
                    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    intent.data = Uri.parse("package:$packageName")
                    startActivity(intent)
                } catch (e: Exception) {
                    val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                    startActivity(intent)
                }
            }
        }
    }

    private fun SYHaTeS7_SBN() {
        findViewById<BottomNavigationView>(R.id.bottom_nav).setOnItemSelectedListener { S7_MI ->
            SABIR7718_LF(when (S7_MI.itemId) {
                R.id.nav_dashboard  -> SYHaTE_DF()
                R.id.nav_recordings -> SABIR7718_RF()
                R.id.nav_settings   -> SYHaTeS7_SF()
                else                -> SYHaTE_DF()
            })
            true
        }
    }

    private fun SABIR7718_LF(S7_F: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, S7_F).commit()
    }

    private fun SYHaTE_DF()  = SYHaTE_DashFrag()
    private fun SABIR7718_RF() = SABIR7718_RecFrag()
    private fun SYHaTeS7_SF() = SYHaTeS7_SetFrag()
}
