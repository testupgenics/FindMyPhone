package com.uphero.sadda.findmyphone;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.uphero.sadda.findmyphone.R;

public class Settings extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    SwitchCompat normalMode,turnAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        normalMode = findViewById(R.id.normalMode);
        turnAlarm = findViewById(R.id.turnAlarm);

        sharedPreferences = getSharedPreferences("LoginInfo",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //Toast.makeText(this,"Welcome "+sharedPreferences.getString("Name","User"),Toast.LENGTH_LONG).show();

        if(sharedPreferences.getBoolean("NormalMode",false)){
            normalMode.setChecked(true);
        }

        if(sharedPreferences.getBoolean("TurnAlarm",false)){
            turnAlarm.setChecked(true);
        }



        normalMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    editor.putBoolean("NormalMode",true);
                    Toast.makeText(Settings.this,"Normal Mode Enabled",Toast.LENGTH_SHORT).show();
                }
                else if(!isChecked){
                    editor.putBoolean("NormalMode",false);
                    Toast.makeText(Settings.this,"Normal Mode Disabled",Toast.LENGTH_SHORT).show();
                }
                editor.commit();
            }
        });

        turnAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    editor.putBoolean("TurnAlarm",true);
                    Toast.makeText(Settings.this,"Turn Alarm Enabled",Toast.LENGTH_SHORT).show();
                }
                else if(!isChecked){
                    editor.putBoolean("TurnAlarm",false);
                    Toast.makeText(Settings.this,"Turn Alarm Disabled",Toast.LENGTH_SHORT).show();
                }
                editor.commit();
            }
        });
    }
}
