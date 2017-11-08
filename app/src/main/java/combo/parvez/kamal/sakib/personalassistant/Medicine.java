package combo.parvez.kamal.sakib.personalassistant;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by sakib on 11/5/17.
 */

public class Medicine extends AppCompatActivity {
    TimePicker timePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);
        final EditText day = (EditText)findViewById(R.id.day);
        final EditText count = (EditText)findViewById(R.id.count);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        Button off = (Button) findViewById(R.id.off);

        //attaching clicklistener on button
        findViewById(R.id.buttonAlarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //We need a calendar object to get the specified time in millis
                //as the alarm manager method takes time in millis to setup the alarm

//                if(  mediCount < 0 || mediCount > 4 || mediDay < 0 || mediDay > 60  )
               // {
                   Toast.makeText(getApplicationContext(), "Medicine alarm is set", Toast.LENGTH_SHORT).show();
                //}
                //else {
                    //for (int i = 0; i < mediCount; i++)
                    //{

                        int add =0;//= 24/mediCount;
                        Calendar calendar = Calendar.getInstance();
                        if (android.os.Build.VERSION.SDK_INT >= 23) {
                            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                                    timePicker.getHour() + add, timePicker.getMinute(), 0);
                        } else {
                            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                                    timePicker.getCurrentHour() + add, timePicker.getCurrentMinute(), 0);
                        }


                        setAlarm(calendar.getTimeInMillis());
                   // }
                //}
            }
        });



        off.setOnClickListener(

                new View.OnClickListener()
        {
            public void onClick(View view){
                Toast.makeText(getApplicationContext(), "Alarm off", Toast.LENGTH_SHORT).show();
                MyAlarm.mediaPlayer.stop();

                }
        }
        );
    }




    private void setAlarm(long time) {
        //getting the alarm manager
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //creating a new intent specifying the broadcast receiver
        Intent i = new Intent(this, MyAlarm.class);

        //creating a pending intent using the intent
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);

        //setting the repeating alarm that will be fired every day
        am.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_HALF_DAY, pi);
        Toast.makeText(this, "Set alarm for Medicine ", Toast.LENGTH_SHORT).show();
    }




}
