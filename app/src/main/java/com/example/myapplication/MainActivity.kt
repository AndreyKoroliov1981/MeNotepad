package com.example.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val APP_PREFERENCES = "mytext"
    val APP_PREFERENCES_TEXT = "text"

    lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSupportActionBar()!!.setBackgroundDrawable(ColorDrawable(Color.rgb(0,0,245)))
        pref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        loadText()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_exit ->finish()
            R.id.action_save -> saveText()
            R.id.action_del -> {deleteText(); loadText();}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadText() {
        val savedText=pref.getString(APP_PREFERENCES_TEXT,"")
        note_Text.setText(savedText)
        Toast.makeText(applicationContext,"Load Text", Toast.LENGTH_SHORT).show()

    }

    private fun deleteText() {
        val editor=pref.edit()
        editor.putString(APP_PREFERENCES_TEXT,"")
        editor.apply()
        Toast.makeText(applicationContext,"Delete Text", Toast.LENGTH_SHORT).show()

    }

    private fun saveText() {
        val editor=pref.edit()
        editor.putString(APP_PREFERENCES_TEXT,note_Text.text.toString())
        editor.apply()
        Toast.makeText(applicationContext,"Save Text", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        saveText()
    }
}
