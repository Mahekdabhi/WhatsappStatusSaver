package com.example.whatsappstatussaver.utils

import android.os.Environment
import java.io.File

object Common {
    val GRID_COUNT = 2
    val STATUS_DIRECTORY = File(
        Environment.getExternalStorageDirectory().toString() +
                File.separator + "WhatsApp/Media/.Statuses"
    )
    var APP_DIR = "/SavedStatus"
}