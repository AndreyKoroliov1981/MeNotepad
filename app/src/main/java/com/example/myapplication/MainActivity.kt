package com.example.myapplication

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.myapplication.choicetheme.ChoiceThemeDialogFragment
import com.example.myapplication.databinding.ActivityMainBinding

const val PREFS_NAME = "theme_prefs"

const val KEY_THEME = "prefs.theme"
const val THEME_UNDEFINED = -1
const val THEME_LIGHT = 0
const val THEME_DARK = 1
const val THEME_SYSTEM = 2
const val DELAY_FOR_COLORED_ICON_MS = 800L

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var binding: ActivityMainBinding

    private lateinit var presenter: MainPresenter

    private val sharedPrefs by lazy { getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }

    private var iconSaveLoadMenu: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initTheme()
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.rgb(0, 0, 245)))
        presenter = (application as App).presenter
//        loadData(presenter.loadData())
        supportFragmentManager.setFragmentResultListener(
            REQUEST_KEY_FOR_DIALOG,
            this,
        ) { _, bundle ->
            when (bundle.getString(KEY_FOR_PRESSED_BUTTON)) {
                KEY_FOR_LIGHT -> {
                    setTheme(AppCompatDelegate.MODE_NIGHT_NO, THEME_LIGHT)
                }
                KEY_FOR_DARK -> {
                    setTheme(AppCompatDelegate.MODE_NIGHT_YES, THEME_DARK)
                }
                KEY_FOR_SYSTEM -> {
                    setTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM, THEME_SYSTEM)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (menu != null) {
                menu.getItem(2).isVisible = true
            }
        } else {
            if (menu != null) {
                menu.getItem(2).isVisible = false
            }
        }
        iconSaveLoadMenu = menu?.getItem(0)
        val data = presenter.loadData()
        loadData(data)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_exit -> finish()
            R.id.action_save -> {
                saveData()
                item.iconTintList = ContextCompat.getColorStateList(this, R.color.colorSave)
                Handler(Looper.getMainLooper()).postDelayed({
                    item.iconTintList = ContextCompat.getColorStateList(this, R.color.colorWhite)
                }, DELAY_FOR_COLORED_ICON_MS)
            }
            R.id.action_theme -> choiceTheme()
            R.id.action_del -> deleteData()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        saveData()
        super.onDestroy()
    }

    private fun initTheme() {
        when (getSavedTheme()) {
            THEME_LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            THEME_DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            THEME_SYSTEM -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    override fun loadData(data: String) {
        binding.noteText.setText(data)
        if (iconSaveLoadMenu != null) {
            iconSaveLoadMenu?.iconTintList =
                ContextCompat.getColorStateList(this, R.color.colorLoad)
            Handler(Looper.getMainLooper()).postDelayed({
                iconSaveLoadMenu?.iconTintList = ContextCompat.getColorStateList(this, R.color.colorWhite)
            }, DELAY_FOR_COLORED_ICON_MS)
        }
    }

    override fun saveData() {
        presenter.saveData(binding.noteText.text.toString())
    }

    override fun deleteData() {
        binding.noteText.setText("")
        presenter.deleteData()
    }

    private fun choiceTheme() {
        val dialog = ChoiceThemeDialogFragment()
        supportFragmentManager.let {
            dialog.show(it, TAG_DIALOG)
        }
    }

    private fun setTheme(themeMode: Int, prefsMode: Int) {
        AppCompatDelegate.setDefaultNightMode(themeMode)
        saveTheme(prefsMode)
    }

    private fun saveTheme(theme: Int) = sharedPrefs.edit().putInt(KEY_THEME, theme).apply()

    private fun getSavedTheme() = sharedPrefs.getInt(KEY_THEME, THEME_UNDEFINED)

    companion object {
        const val TAG_DIALOG = "tag_dialog"
        const val REQUEST_KEY_FOR_DIALOG = "request_key_for_dialog"
        const val KEY_FOR_PRESSED_BUTTON = "key_for_pressed_button"
        const val KEY_FOR_LIGHT = "key_for_light"
        const val KEY_FOR_DARK = "key_for_dark"
        const val KEY_FOR_SYSTEM = "key_for_system"
    }
}
