package combo.parvez.kamal.sakib.personalassistant.Reminder;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import combo.parvez.kamal.sakib.personalassistant.R;
import combo.parvez.kamal.sakib.personalassistant.Routine;

public class EventActivity extends AppCompatActivity {

    private ListView listView;
    private Button btnAdd;
    private DatabaseHandler database;
    private List<Data> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        database = DatabaseHandler.getInstance(this);

        listView = (ListView) findViewById(R.id.listView);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        // getNotific();


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddClicked();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Data data = list.get(position);
                TextView txtMemo = (TextView) view.findViewById(R.id.txtMemo);
                if (data.isFullDisplayed()) {
                    txtMemo.setText(data.getShortText());
                    data.setFullDisplayed(false);
                } else {
                    txtMemo.setText(data.getText());
                    data.setFullDisplayed(true);
                }

              // getNotific();
            }
        });
    }

    public void getNotific(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String currentTime = hour + ":" + minute;
        String currentDate = day+"-"+(month+1)+"-"+year;

        List<Data> list = new ArrayList<>();

        list = database.getAllEvent();

        for(Data data: list){

            String eventDate =data.getTarikh(); //list.get(i).getTarikh();
            String eventTime = data.getTIME();//list.get(i).getTIME();

            String ara[] = eventTime.split(":");

            int databaseHour = Integer.parseInt(ara[0]);
            int databaseMin = Integer.parseInt(ara[1]);

            if(currentDate.equals(eventDate) && databaseHour == hour){

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(EventActivity.this)
                        .setSmallIcon(android.R.drawable.stat_notify_error)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                        .setContentTitle("You need to join an event")
                        .setContentText("Event notification");
                notificationBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(EventActivity.this);
                notificationManagerCompat.notify(1, notificationBuilder.build());
                //break;
            }

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        database.open();
        list= database.getAllEvent();
        database.close();
        NotepadAdapter adapter = new NotepadAdapter(this,list);
        this.listView.setAdapter(adapter);
    }

    public void onAddClicked() {
        Intent intent = new Intent(this, AddEventActivity.class);
        startActivity(intent);
    }

    public void onDeleteClicked(Data data) {
        database.open();
        database.delete(data);
        database.close();

        ArrayAdapter<Data> adapter = (ArrayAdapter<Data>) listView.getAdapter();
        adapter.remove(data);
        adapter.notifyDataSetChanged();
    }

    public void onEditClicked(Data data) {
        Intent intent = new Intent(this, AddEventActivity.class);
        intent.putExtra("REMINDER", data);
        startActivity(intent);
    }

    private class NotepadAdapter extends ArrayAdapter<Data> {


        public NotepadAdapter(Context context, List<Data> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.reminderlist, parent, false);
            }

            ImageView btnEdit = (ImageView) convertView.findViewById(R.id.btnEdit);
            ImageView btnDelete = (ImageView) convertView.findViewById(R.id.btnDelete);
            TextView txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            TextView txtMemo = (TextView) convertView.findViewById(R.id.txtMemo);
            TextView txtTime = (TextView) convertView.findViewById(R.id.txtTime);

            final Data data = list.get(position);
            data.setFullDisplayed(false);
            txtDate.setText(data.getTarikh());
            txtMemo.setText(data.getShortText());
            txtTime.setText(data.getTIME());

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditClicked(data);
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClicked(data);
                }
            });
            return convertView;
        }
    }


}
