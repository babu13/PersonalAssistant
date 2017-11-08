package combo.parvez.kamal.sakib.personalassistant;

/**
 * Created by sakib on 11/6/17.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DataBase  extends SQLiteOpenHelper {

    // Define the SQLite database name
    private static final String DATABASE_NAME = "call_blocker.db";

    // Define the SQLite database version
    private static final int DATABASE_VERSION = 1;

    // Define the SQLite Table name to create
    public static final String TABLE_BLACKLIST = "blocklist";

    // Table creation SQL statement
    private static final String TABLE_CREATE = "create table "  + TABLE_BLACKLIST + "( id "
            + " integer primary key autoincrement, phone_number  text not null);";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // This method will execute once in the application entire life cycle
    // All table creation code should put here
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }

}
