package combo.parvez.kamal.sakib.personalassistant;

/**
 * Created by sakib on 11/5/17.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.util.Log;
import android.provider.Settings;
import android.widget.Toast;

/**
 * Created by Belal on 8/29/2017.
 */

//class extending the Broadcast Receiver
public class MyAlarm extends BroadcastReceiver {
    static MediaPlayer mediaPlayer;
    //the method will be fired when the alarm is triggerred
    @Override
    public void onReceive(Context context, Intent intent) {

         mediaPlayer = MediaPlayer.create(context,
                Settings.System.DEFAULT_RINGTONE_URI);

        mediaPlayer.start();
        //Log.d("MyAlarmSakib", "Alarm just fired");
    }

}


