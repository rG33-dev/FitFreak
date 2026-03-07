package com.example.fitfreak.data

import android.content.Context
import android.content.Intent
import android.net.Uri

fun sendEmail(context: Context) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:raghavsankhyaan@gmail.com")
        putExtra(Intent.EXTRA_SUBJECT, "FitFreak Support")
       // putExtra(Intent.EXTRA_TEXT, "Hello developer, I want to report an issue.")
    }

    context.startActivity(intent)
}