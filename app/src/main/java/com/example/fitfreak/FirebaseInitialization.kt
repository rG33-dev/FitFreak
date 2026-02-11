package com.example.fitfreak

import android.app.Application
import android.os.Bundle
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MyApp : Application()
{
    fun OnCreate(){
        super.onCreate()
        FirebaseApp.initializeApp(this)

    }
}
