package com.example.myapplication

import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var binding: ActivityMainBinding

    private lateinit var presenter : MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.rgb(0, 0, 245)))
        //presenter = MainPresenter.Base(MainModel.Base(this))
        presenter=(application as App).presenter
        loadData(presenter.loadData())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_exit -> finish()
            R.id.action_save -> saveData()
            R.id.action_del -> deleteData()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        saveData()
        super.onDestroy()
    }

    override fun loadData(data: String) {
        binding.noteText.setText(data)
        Toast.makeText(applicationContext, "Load Text", Toast.LENGTH_SHORT).show()
    }

    override fun saveData() {
        Toast.makeText(applicationContext, "Save Text", Toast.LENGTH_SHORT).show()
        presenter.saveData(binding.noteText.text.toString())
    }

    override fun deleteData() {
        Toast.makeText(applicationContext, "Delete text", Toast.LENGTH_SHORT).show()
        binding.noteText.setText("")
        presenter.deleteData()
    }
}