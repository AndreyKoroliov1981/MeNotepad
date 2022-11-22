package com.example.myapplication

interface MainView {

    fun loadData(data: String)
    fun saveData()
    fun deleteData()

    class Empty() : MainView {

        override fun loadData(data: String) = Unit

        override fun saveData() = Unit

        override fun deleteData() = Unit

    }

}