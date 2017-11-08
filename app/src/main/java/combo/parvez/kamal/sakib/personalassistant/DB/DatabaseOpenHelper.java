package combo.parvez.kamal.sakib.personalassistant.DB;

/**
 * Created by sakib on 11/6/17.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by parvez on 10/24/17.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    public static final  String DATABASE ="notepad.db";
    public static final String TABLE = "notepad";
    public static final  int VERSION = 1;

    public DatabaseOpenHelper(Context context){
        super(context,DATABASE,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE notepad(date INTEGER PRIMARY KEY, notepad TEXT);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }
}
