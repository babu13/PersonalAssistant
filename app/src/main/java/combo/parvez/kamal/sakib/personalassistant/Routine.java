package combo.parvez.kamal.sakib.personalassistant;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by sakib on 9/30/17.
 */

public class Routine extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String text = "";
        TextView textView;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);
        TextView time = (TextView)findViewById(R.id.textView);
        Button add = (Button) findViewById(R.id.add);
        Button  dlete = (Button) findViewById(R.id.dlete);

        final DatabaseHelper db = new DatabaseHelper(this);
        final EditText picktime = (EditText) findViewById(R.id.addtime);
        final EditText pickid = (EditText) findViewById(R.id.id);
        final EditText picktask = (EditText) findViewById(R.id.addtask);
        textView = (TextView) findViewById(R.id.textView);

        //add the new tasks.
        add.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view){

                String time = picktime.getText().toString();
                String task = picktask.getText().toString();
                if(time.equals("")|| task.equals("") ||(time.equals("") && task.equals("")) )
                    Toast.makeText(getApplicationContext(), "Pleasr Input correctly:  __:__:am/pm", Toast.LENGTH_SHORT).show();

                else if( time.charAt(2) !=':' || time.charAt(5) !=':')
                {
                    Toast.makeText(getApplicationContext(), "Pleasr Input correctly:  __:__:am/pm", Toast.LENGTH_SHORT).show();
                }

                else if( time.charAt(0) <'0' || time.charAt(0) >'1' || time.charAt(1) >'9' ||time.charAt(1) <'0')
                {
                    Toast.makeText(getApplicationContext(), "Pleasr Input correctly:  __:__:am/pm", Toast.LENGTH_SHORT).show();
                }

                else if( time.charAt(3) <'0' || time.charAt(3) >'5' || time.charAt(4) >'9' ||time.charAt(4) < '0')
                {
                    Toast.makeText(getApplicationContext(), "Pleasr Input correctly:  __:__:am/pm", Toast.LENGTH_SHORT).show();
                }

                else {
                    db.addRoutine(new Routine_info(time, task));


                    Toast.makeText(getApplicationContext(), "routine is set", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
        //for deleting the task

        //for(int i=0; i<80 ;i++)
          //  db.deleteRoutine(new Routine_info(i));
        dlete.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view){
                int id = Integer.parseInt(pickid.getText().toString());
                db.deleteRoutine(new Routine_info(id));


                Toast.makeText(getApplicationContext(), id+" Task has been deleted", Toast.LENGTH_SHORT).show();

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(Routine.this)
                        .setSmallIcon(android.R.drawable.stat_notify_error)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                        .setContentTitle("Task has been deleted")
                        .setContentText("Routine notification");
                notificationBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(Routine.this);
                notificationManagerCompat.notify(1, notificationBuilder.build());
                finish();

            }
        });

        //this line display the tasks

        List<Routine_info> routine_info = db.getAllRoutine();
        for (Routine_info r : routine_info) {
            String log ="Task ID: " + r.getId() + ", Time: " + r.getTime() + ", Task: " + r.getTask() + "\n";
            text = text + log;
            String[] tme = r.getTime().split(":");
            int hour = Integer.parseInt(tme[0]);
            int munite = Integer.parseInt(tme[1]);
            String ampm = tme[2];
            //if(ampm.equals("pm"))
            //{
              //  hour = hour+12;
            //}

            java.util.Calendar calendar = java.util.Calendar.getInstance();
            int Chour = calendar.get(java.util.Calendar.HOUR);
            int Cmunite = calendar.get(java.util.Calendar.MINUTE);
            if(hour == Chour && Cmunite-20 <= munite )
            {
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(Routine.this)
                        .setSmallIcon(android.R.drawable.stat_notify_error)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                        .setContentTitle("You nees to attend " + r.getTask())
                        .setContentText("Routine notification");
                notificationBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(Routine.this);
                notificationManagerCompat.notify(1, notificationBuilder.build());
            }
            else if(hour < Chour && ampm.equals("pm") || hour > Chour && ampm.equals("am")  )
            {
                db.deleteRoutine(new Routine_info(r.getId()));
            }



        }

        textView.setText(text);


    }



}
