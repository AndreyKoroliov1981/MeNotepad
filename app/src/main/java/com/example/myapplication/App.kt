package com.example.myapplication

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class App: Application() {

    lateinit var presenter:MainPresenter

    override fun onCreate() {
       super.onCreate()
        presenter = MainPresenter.Base(MainModel.Base(this))
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
    }
}