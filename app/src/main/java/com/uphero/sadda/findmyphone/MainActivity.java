package com.uphero.sadda.findmyphone;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    SwitchCompat switchNormalMode, switchTurnAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        requestPermissions();
        registerListeners();

    }

    private void initViews() {
        switchNormalMode = findViewById(R.id.normalMode);
        switchTurnAlarm = findViewById(R.id.turnAlarm);

        preferences = getSharedPreferences("FindMyPhonePreferences", MODE_PRIVATE);
        editor = preferences.edit();

        if (preferences.getBoolean("NormalMode", false)) {
            switchNormalMode.setChecked(true);
        }

        if (preferences.getBoolean("TurnAlarm", false)) {
            switchTurnAlarm.setChecked(true);
        }
    }

    private void requestPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.INTERNET}, 1);
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_SMS}, 2);
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 3);
            }
        }

    }

    private void registerListeners(){
        switchNormalMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    editor.putBoolean("NormalMode", true);
                    Toast.makeText(MainActivity.this, "Normal Mode Enabled", Toast.LENGTH_SHORT).show();
                } else{
                    editor.putBoolean("NormalMode", false);
                    Toast.makeText(MainActivity.this, "Normal Mode Disabled", Toast.LENGTH_SHORT).show();
                }
                editor.commit();
            }
        });

        switchTurnAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    editor.putBoolean("TurnAlarm", true);
                    Toast.makeText(MainActivity.this, "Turn Alarm Enabled", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putBoolean("TurnAlarm", false);
                    Toast.makeText(MainActivity.this, "Turn Alarm Disabled", Toast.LENGTH_SHORT).show();
                }
                editor.commit();
            }
        });
    }
}
