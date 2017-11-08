package combo.parvez.kamal.sakib.personalassistant.Reminder;

/**
 * Created by sakib on 11/8/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by username on 11/6/17.
 */

public class DatabaseHandler {

    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;
    private static volatile DatabaseHandler instance;
    private DatabaseHandler(Context context){
        openHelper = new Table(context);
    }

    public DatabaseHandler(){}

    public static synchronized DatabaseHandler getInstance(Context context){
        if(instance==null){
            instance = new DatabaseHandler(context);
        }
        return instance;
    }
    public void open(){
        database = openHelper.getWritableDatabase();
    }
    public void close(){
        if(database!=null){
            this.database.close();
        }
    }
    public int save(Data data){
        ContentValues values = new ContentValues();
        values.put("date",data.getTime());
        values.put("description",data.getText());
        values.put("tarikh",data.getTarikh());
        values.put("time",data.getTIME());
        long l = database.insert(Table.TABLE,null,values);
        Log.d("Kamal",l+" "+values.get("tarikh")+" "+values.get("time"));
        return 1;
    }
    public void update(Data data){
        ContentValues values = new ContentValues();
        values.put("date",data.getTime());
        values.put("description",data.getText());
        values.put("tarikh",data.getTarikh());
        values.put("time",data.getTIME());
        String date = Long.toString(data.getTime());
        // database.update(Table.TABLE,values,"date = ?",new String[]{date});
        database.insert(Table.TABLE,null,values);
    }
    public void delete(Data data){
        String date = Long.toString(data.getTime());
        database.delete(Table.TABLE,"date = ?",new String[]{date});
    }
    public List getAllEvent(){
        List list  = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * From Reminder ORDER BY date DESC",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            long date = cursor.getLong(0);
            String text = cursor.getString(1);
            String tarikh = cursor.getString(2);
            String time = cursor.getString(3);
            list.add(new Data(date,text,tarikh,time));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}
