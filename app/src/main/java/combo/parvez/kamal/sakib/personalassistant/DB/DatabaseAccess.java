package combo.parvez.kamal.sakib.personalassistant.DB;

/**
 * Created by sakib on 11/6/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import combo.parvez.kamal.sakib.personalassistant.Notepad;

/**
 * Created by parvez on 10/24/17.
 */

public class DatabaseAccess {
    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;
    private static volatile DatabaseAccess instance;
    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseOpenHelper(context);
    }
    public static synchronized DatabaseAccess getInstance(Context context){
        if(instance==null){
            instance = new DatabaseAccess(context);
        }
        return instance;
    }
    public void open(){
        this.database = openHelper.getWritableDatabase();
    }
    public void close(){
        if(database!=null){
            this.database.close();
        }
    }
    public void save(Notepad notepad){
        ContentValues values = new ContentValues();
        values.put("date",notepad.getTime());
        values.put("notepad",notepad.getText());
        database.insert(DatabaseOpenHelper.TABLE,null,values);
    }
    public void update(Notepad notepad){
        ContentValues values = new ContentValues();
        values.put("date",notepad.getTime());
        values.put("notepad",notepad.getText());
        String date = Long.toString(notepad.getTime());
        //database.update(DatabaseOpenHelper.TABLE,values,"date = ?",new String[]{date});
        database.insert(DatabaseOpenHelper.TABLE,null,values);
    }
    public void delete(Notepad notepad){
        String date = Long.toString(notepad.getTime());
        database.delete(DatabaseOpenHelper.TABLE,"date = ?",new String[]{date});
    }
    public List getAllNote(){
        List notepads  = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT *From notepad ORDER BY date DESC",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            long time = cursor.getLong(0);
            String text = cursor.getString(1);
            notepads.add(new Notepad(time,text));
            cursor.moveToNext();
        }
        cursor.close();
        return notepads;
    }


}

