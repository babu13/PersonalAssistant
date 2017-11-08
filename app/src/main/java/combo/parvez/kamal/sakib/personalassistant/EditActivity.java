package combo.parvez.kamal.sakib.personalassistant;

/**
 * Created by sakib on 11/6/17.
 */

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import combo.parvez.kamal.sakib.personalassistant.DB.DatabaseAccess;

public class EditActivity extends ActionBarActivity {
    private EditText etText;
    private Button btnSave;
    private Button btnCancel;
    private Notepad notepad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        this.etText = (EditText) findViewById(R.id.etText);
        this.btnSave = (Button) findViewById(R.id.btnSave);
        this.btnCancel = (Button) findViewById(R.id.btnCancel);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            notepad = (Notepad) bundle.get("NOTEPAD");
            if(notepad != null) {
                this.etText.setText(notepad.getText());
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
    }

    public void onSaveClicked() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        if(notepad == null) {
            // Add new memo
            Notepad temp = new Notepad();
            temp.setText(etText.getText().toString());
            databaseAccess.save(temp);
        } else {
            // Update the memo

            Notepad temp = new Notepad();
            temp.setText(etText.getText().toString());
            databaseAccess.update(temp);
            databaseAccess.delete(notepad);


        }
        databaseAccess.close();
        this.finish();
    }

    public void onCancelClicked() {
        this.finish();
    }
}
