package com.uphero.sadda.findmyphone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsMessage;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

public class MyBroadcastReceiver extends BroadcastReceiver {

    SharedPreferences sharedPreferences;
    String passcode;

    @Override
    public void onReceive(Context context, Intent intent) {

        sharedPreferences = context.getSharedPreferences("LoginInfo",MODE_PRIVATE);
        passcode = sharedPreferences.getString("Passcode","0");
        Boolean normalMode,turnAlarm;

        normalMode = sharedPreferences.getBoolean("NormalMode",false);
        turnAlarm = sharedPreferences.getBoolean("TurnAlarm",false);

        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);

                    String message = currentMessage.getDisplayMessageBody();

                    if (message.equalsIgnoreCase("FindMe:"+passcode)) {

                        if (turnAlarm && normalMode){

                            AudioManager audioManager;
                            audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                            audioManager.setStreamVolume(AudioManager.STREAM_RING,audioManager.getStreamMaxVolume(AudioManager.STREAM_RING),0);

                            Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                            final Ringtone r = RingtoneManager.getRingtone(context, alarm);
                            r.play();

                            new CountDownTimer(5000, 1000) {

                                @Override
                                public void onTick(long millisUntilFinished) {}

                                public void onFinish() {
                                    r.stop();
                                }

                            }.start();

                        }

                        else if (turnAlarm) {

                            Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                            final Ringtone r = RingtoneManager.getRingtone(context, alarm);
                            r.setStreamType(AudioManager.STREAM_ALARM);
                            r.play();

                            new CountDownTimer(5000, 1000) {

                                @Override
                                public void onTick(long millisUntilFinished) {}

                                public void onFinish() {
                                    r.stop();
                                }

                            }.start();

                        }

                        else if (normalMode) {

                            AudioManager audioManager;
                            audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                            audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
                            audioManager.setStreamVolume(AudioManager.STREAM_RING,audioManager.getStreamMaxVolume(AudioManager.STREAM_RING),0);

                        }
                    }
                }
            }

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }
}
