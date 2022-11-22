package com.example.myapplication

import android.app.Application

class App: Application() {

    lateinit var presenter:MainPresenter

    override fun onCreate() {
       super.onCreate()
        presenter = MainPresenter.Base(MainModel.Base(this))
    }
}