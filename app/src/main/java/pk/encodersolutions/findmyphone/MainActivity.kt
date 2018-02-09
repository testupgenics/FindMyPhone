package pk.encodersolutions.findmyphone

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SwitchCompat
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds


class MainActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var switchNormalMode: SwitchCompat
    private lateinit var switchTurnAlarm: SwitchCompat
    private lateinit var mInterstitialAd : InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAds()
        initViews()
        requestPermissions()
        registerListeners()
    }

    private fun initAds() {
        MobileAds.initialize(this, "ca-app-pub-6728394946634137~6666637867")
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-6728394946634137/5597098770"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        val mAdView: AdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
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

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_SMS),1)
            }

        else if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS),2)
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

    private fun showInterstitialAd(){
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show()
        }
    }

    override fun onBackPressed() {
        showInterstitialAd()
        super.onBackPressed()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}