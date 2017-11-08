package combo.parvez.kamal.sakib.personalassistant;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Created by sakib on 9/30/17.
 */

public class Alarm extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

         Button wake_up = (Button) findViewById(R.id.wakeup);

        wake_up.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view){
                Intent wakeup = new Intent(Alarm.this,WakeUp.class);
                startActivity(wakeup);

            }
        });

         Button medicine_alarm = (Button) findViewById(R.id.medicine);

        medicine_alarm.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view){
                Intent medicine = new Intent(Alarm.this,Medicine.class);
                startActivity(medicine);

            }
        });
        //Button meeting_alarm = (Button) findViewById(R.id.meeting);

        //meeting_alarm.setOnClickListener(new View.OnClickListener()
        //{
         //   public void onClick(View view){
           //     Intent meeting = new Intent(Alarm.this,Meeting.class);
             //   startActivity(meeting);

            //}
        //});


    }


}
