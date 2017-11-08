package combo.parvez.kamal.sakib.personalassistant.Reminder;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Table extends SQLiteOpenHelper {
    public static final  String DATABASE ="reminder.db";
    public static final String TABLE = "Reminder";
    public static final  int VERSION = 1;

    //public DatabaseOpenHelper(){}

    public Table(Context context){
        super(context,DATABASE,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE Reminder(date INTEGER PRIMARY KEY, description TEXT , tarikh TEXT , time TEXT);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
}
