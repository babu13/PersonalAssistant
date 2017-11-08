package combo.parvez.kamal.sakib.personalassistant;

import android.app.Notification;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sakib on 10/28/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "routineManager";
    public static final String Table_Routine = "Routine_info";
    public static final String KEY_ID = "id";
    public static final String KEY_Time = "time";
    public static final String KEY_Task = "task";


    //database object

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String Create_table = "CREATE TABLE " + Table_Routine + "("+ KEY_ID +" INTEGER PRIMARY KEY, "+KEY_Time+" TEXT, "+ KEY_Task +
                " TEXT " + ")";
        db.execSQL(Create_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXIST " + Table_Routine);
        onCreate(db);
    }

    //add routine for the database

    void addRoutine(Routine_info routine_info){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // values.put(String.valueOf(KEY_ID),routine_info.getId());
        values.put(KEY_Time,routine_info.getTime());
        values.put(KEY_Task,routine_info.getTask());
        db.insert(Table_Routine, null, values);
        db.close();
    }

    //fetch routine
    Routine_info getRoutine(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Table_Routine, new String[]{KEY_ID, KEY_Time, KEY_Task}, KEY_ID + " = '"+
                String.valueOf(id)+"'", null,null,null,null);
        if(cursor != null){
            cursor.moveToFirst();
        }

        Routine_info routine_info = new Routine_info(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2));
        return routine_info;
    }


    //display the whole routine

    public List<Routine_info> getAllRoutine(){
        List<Routine_info> routineList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Table_Routine;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Routine_info routine_info = new Routine_info();
                routine_info.setId(Integer.parseInt(cursor.getString(0)));
                routine_info.setTime(cursor.getString(1));
                routine_info.setTask(cursor.getString(2));

                routineList.add(routine_info);

            }while(cursor.moveToNext());
        }
        return routineList;
    }
    // update the routine
    //this function has not been used yet
    public int updateRoutine(Routine_info routine_info){
        SQLiteDatabase db  = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_Time, routine_info.getTime());
        values.put(KEY_Task, routine_info.getTask());
        return db.update(Table_Routine,values, KEY_ID + "=?",
                new String[]{String.valueOf(routine_info.getId())});
    }


    //delete the routine
    //remove the routine base on id taken fron the user

    public void deleteRoutine(Routine_info routine_info){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table_Routine, KEY_ID + " = '"+
                String.valueOf(routine_info.getId())+"'",null);
        db.close();




    }

    //get routine count
    //this function has not been used yet
    public int getRoutineCount(){
        String countQuery = "SELECT * FROM " + Table_Routine;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        cursor.close();
        return cursor.getCount();
    }

}
