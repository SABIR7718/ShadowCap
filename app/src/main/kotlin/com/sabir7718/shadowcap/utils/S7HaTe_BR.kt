package com.sabir7718.shadowcap.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class S7HaTe_BR : BroadcastReceiver() {
    override fun onReceive(S7_CTX: Context, S7_INT: Intent) {
        if (S7_INT.action == Intent.ACTION_BOOT_COMPLETED) {
            val SYHaTE_PH = SYHaTE_PH(S7_CTX)
        }
    }
}
