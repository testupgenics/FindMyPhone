package com.uphero.sadda.findmyphone;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Button register;
    TextInputEditText name, passcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        sharedPreferences = getSharedPreferences("LoginInfo", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean("Status", false)) {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
            finish();
        }

        register = findViewById(R.id.register);
        name = findViewById(R.id.name);
        passcode = findViewById(R.id.passcode);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tmpName, tmpPassCode;
                tmpName = name.getText().toString();
                tmpPassCode = passcode.getText().toString();
                if (tmpName.equals("")) {
                    name.requestFocus();
                    name.setError("Name is required!");
                } else if (tmpName.trim().length() == 0) {
                    name.requestFocus();
                    name.setError("Name is required!");
                } else if (tmpName.startsWith(" ") || tmpName.endsWith(" ")) {
                    name.requestFocus();
                    name.setError("Please enter a valid Name!");
                } else if (tmpName.length() < 3) {
                    name.requestFocus();
                    name.setError("Enter your full name!");
                } else if (tmpPassCode.equals("")) {
                    passcode.requestFocus();
                    passcode.setError("Password is required!");
                } else if (tmpPassCode.length() < 4) {
                    passcode.requestFocus();
                    passcode.setText(null);
                    passcode.setError("Password must be at least 4 characters long");
                } else {
                    editor.putString("Name", tmpName);
                    editor.putString("Passcode", tmpPassCode);
                    editor.putBoolean("Status", true);
                    editor.apply();

                    Intent intent = new Intent(MainActivity.this, Settings.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }
}
