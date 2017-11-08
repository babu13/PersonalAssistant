package combo.parvez.kamal.sakib.personalassistant.Reminder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import combo.parvez.kamal.sakib.personalassistant.R;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity {

    TextView time;
    TextView date;
    private EditText etText;
    private Button btnSave;
    private Button btnCancel;
    private Data data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        this.etText = (EditText) findViewById(R.id.editText);
        this.btnSave = (Button) findViewById(R.id.btnSave);
        this.btnCancel = (Button) findViewById(R.id.btnCancel);
        this.time = (TextView) findViewById(R.id.setTime);
        this.date=(TextView) findViewById(R.id.setdate);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String currentTime = hour + ":" + minute;
        String currentDate = day+"-"+(month+1)+"-"+year;

        time.setText(currentTime);
        date.setText(currentDate);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            data = (Data) bundle.get("REMINDER");
            if(data != null) {
                this.etText.setText(data.getText());
            }
        }

        this.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveClicked();
            }
        });

        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelClicked();
            }
        });

        this.time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(view);
            }
        });
    }

    public void onSaveClicked() {
        DatabaseHandler database = DatabaseHandler.getInstance(this);
        database.open();
        if(data == null) {
            // Add new memo
            Data temp = new Data();
            temp.setText(etText.getText().toString());
            temp.setTarikh(date.getText().toString());
            temp.setTIME(time.getText().toString());
            int x = database.save(temp);

        } else {
            // Update the memo
            //System.out.println(databaseAccess.getAllNote());
            //notepad.setText(etText.getText().toString());
            //databaseAccess.save(notepad);
            Data temp = new Data();
            temp.setText(etText.getText().toString());
            temp.setTarikh(date.getText().toString());
            temp.setTIME(time.getText().toString());
            database.update(temp);
            database.delete(data);
        }
        database.close();
        this.finish();
    }
    public void onCancelClicked() {
        this.finish();
    }


    public void onStart(){
        super.onStart();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialog dialog=new DateDialog(view);
                android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                dialog.show(ft, "DatePicker");
            }
        });

    }

    public void setTime(View view){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog;
        timePickerDialog = new TimePickerDialog(AddEventActivity.this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                time.setText(hourOfDay + ":" + minute);
            }
        },hour,minute,true);

        timePickerDialog.show();
    }
}
