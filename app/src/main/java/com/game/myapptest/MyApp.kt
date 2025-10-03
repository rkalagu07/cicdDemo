package com.game.myapptest

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG,"ComeInsideOncreate")
    }
    companion object{
        const val TAG = "MyApplication"
    }
}