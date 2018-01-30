package pk.encodersolutions.findmyphone

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.media.AudioManager
import android.media.RingtoneManager
import android.os.CountDownTimer
import android.telephony.SmsMessage
import android.util.Log

class MyBroadcastReceiver : BroadcastReceiver() {

    private lateinit var preferences: SharedPreferences

    override fun onReceive(context: Context, intent: Intent) {

        preferences = context.getSharedPreferences("FindMyPhonePreferences", MODE_PRIVATE)
        val normalMode: Boolean?
        val turnAlarm: Boolean?

        normalMode = preferences.getBoolean("NormalMode", false)
        turnAlarm = preferences.getBoolean("TurnAlarm", false)

        val bundle = intent.extras

        try {

            if (bundle != null) {

                val pdusObj = bundle.get("pdus") as Array<Any>

                for (aPdusObj in pdusObj) {

                    val currentMessage = SmsMessage.createFromPdu(aPdusObj as ByteArray)

                    val message = currentMessage.displayMessageBody

                    if (message.equals("FindME", true)) {

                        if (turnAlarm && normalMode) {

                            val audioManager: AudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                            audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
                            audioManager.setStreamVolume(AudioManager.STREAM_RING, audioManager.getStreamMaxVolume(AudioManager.STREAM_RING), 0)

                            val alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                            val r = RingtoneManager.getRingtone(context, alarm)
                            r.play()

                            object : CountDownTimer(5000, 1000) {

                                override fun onTick(millisUntilFinished: Long) {}

                                override fun onFinish() {
                                    r.stop()
                                }

                            }.start()

                        } else if (turnAlarm) {

                            val alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                            val r = RingtoneManager.getRingtone(context, alarm)
                            r.streamType = AudioManager.STREAM_ALARM
                            r.play()

                            object : CountDownTimer(5000, 1000) {

                                override fun onTick(millisUntilFinished: Long) {}

                                override fun onFinish() {
                                    r.stop()
                                }

                            }.start()

                        } else if (normalMode) {

                            val audioManager: AudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                            audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
                            audioManager.getStreamMaxVolume(AudioManager.STREAM_RING)
                            audioManager.setStreamVolume(AudioManager.STREAM_RING, audioManager.getStreamMaxVolume(AudioManager.STREAM_RING), 0)

                        }
                    }
                }
            }

        } catch (e: Exception) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e)

        }
    }
}
