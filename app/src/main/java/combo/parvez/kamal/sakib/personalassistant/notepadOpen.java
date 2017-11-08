package combo.parvez.kamal.sakib.personalassistant;

/**
 * Created by sakib on 11/6/17.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;

import combo.parvez.kamal.sakib.personalassistant.DB.DatabaseAccess;

import static android.media.CamcorderProfile.get;


public class notepadOpen extends ActionBarActivity {
    private ListView listView;
    private Button btnAdd;
    private DatabaseAccess databaseAccess;
    private List<Notepad> notepads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);

        this.databaseAccess = DatabaseAccess.getInstance(this);

        this.listView = (ListView) findViewById(R.id.listView);
        this.btnAdd = (Button) findViewById(R.id.btnAdd);

        this.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddClicked();
            }
        });

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Notepad notepad = notepads.get(position);
                TextView txtMemo = (TextView) view.findViewById(R.id.txtMemo);
                if (notepad.isFullDisplayed()) {
                    txtMemo.setText(notepad.getShortText());
                    notepad.setFullDisplayed(false);
                } else {
                    txtMemo.setText(notepad.getText());
                    notepad.setFullDisplayed(true);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        databaseAccess.open();
        this.notepads= databaseAccess.getAllNote();
        databaseAccess.close();
        NotepadAdapter adapter = new NotepadAdapter(this,notepads);
        this.listView.setAdapter(adapter);
    }

    public void onAddClicked() {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }

    public void onDeleteClicked(Notepad notepad) {
        databaseAccess.open();
        databaseAccess.delete(notepad);
        databaseAccess.close();

        ArrayAdapter<Notepad> adapter = (ArrayAdapter<Notepad>) listView.getAdapter();
        adapter.remove(notepad);
        adapter.notifyDataSetChanged();
    }

    public void onEditClicked(Notepad notepad) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("NOTEPAD", notepad);
        startActivity(intent);
    }

    private class NotepadAdapter extends ArrayAdapter<Notepad> {


        public NotepadAdapter(Context context, List<Notepad> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_note, parent, false);
            }

            ImageView btnEdit = (ImageView) convertView.findViewById(R.id.btnEdit);
            ImageView btnDelete = (ImageView) convertView.findViewById(R.id.btnDelete);
            TextView txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            TextView txtMemo = (TextView) convertView.findViewById(R.id.txtMemo);

            final Notepad notepad = notepads.get(position);
            notepad.setFullDisplayed(false);
            txtDate.setText(notepad.getDate());
            txtMemo.setText(notepad.getShortText());
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditClicked(notepad);
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClicked(notepad);
                }
            });
            return convertView;
        }
    }
}
