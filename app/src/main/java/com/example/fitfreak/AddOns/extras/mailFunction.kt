package com.example.fitfreak.AddOns.extras

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri

fun sendEmail(context: Context) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = "mailto:MAIL HERE".toUri()
        putExtra(Intent.EXTRA_SUBJECT, "FitFreak Support")
    }

    context.startActivity(intent)
}