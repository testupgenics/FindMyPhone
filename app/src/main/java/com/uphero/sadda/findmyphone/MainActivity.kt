package com.uphero.sadda.findmyphone

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SwitchCompat
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var switchNormalMode: SwitchCompat
    private lateinit var switchTurnAlarm: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        requestPermissions()
        registerListeners()

    }

    private fun initViews() {
        switchNormalMode = findViewById(R.id.normalMode)
        switchTurnAlarm = findViewById(R.id.turnAlarm)

        preferences = getSharedPreferences("FindMyPhonePreferences", Context.MODE_PRIVATE)
        editor = preferences.edit()

        if (preferences.getBoolean("NormalMode", false)) {
            switchNormalMode.isChecked = true
        }

        if (preferences.getBoolean("TurnAlarm", false)) {
            switchTurnAlarm.isChecked = true
        }
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission.INTERNET), 1)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission.READ_SMS), 2)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission.RECEIVE_SMS), 3)
        }
    }

    private fun registerListeners() {
        switchNormalMode.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                editor.putBoolean("NormalMode", true)
                Toast.makeText(this@MainActivity, "Normal Mode Enabled", Toast.LENGTH_SHORT).show()
            } else {
                editor.putBoolean("NormalMode", false)
                Toast.makeText(this@MainActivity, "Normal Mode Disabled", Toast.LENGTH_SHORT).show()
            }
            editor.commit()
        }

        switchTurnAlarm.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                editor.putBoolean("TurnAlarm", true)
                Toast.makeText(this@MainActivity, "Turn Alarm Enabled", Toast.LENGTH_SHORT).show()
            } else {
                editor.putBoolean("TurnAlarm", false)
                Toast.makeText(this@MainActivity, "Turn Alarm Disabled", Toast.LENGTH_SHORT).show()
            }
            editor.commit()
        }
    }
}