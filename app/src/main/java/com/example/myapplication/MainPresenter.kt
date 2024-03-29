package com.example.myapplication

interface MainPresenter {

    fun loadData(): String
    fun saveData(date: String)
    fun deleteData()

    class Base(private val model: MainModel) : MainPresenter {

        private var view: MainView = MainView.Empty()

        override fun loadData(): String {
            return model.loadData() ?: ""
        }

        override fun saveData(date: String) {
            model.saveData(date)
            view.saveData()
        }

        override fun deleteData() {
            model.deleteData()
            view.deleteData()
        }
    }
}
