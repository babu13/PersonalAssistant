package combo.parvez.kamal.sakib.personalassistant;

/**
 * Created by sakib on 11/6/17.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class BlocklistDAO {

    // SQLiteDatabase and DatabaseHelper objects  to access SQLite database
    private SQLiteDatabase database;
    private DataBase dbHelper;

    // Constructor initiates the DatabaseHelper to make sure, database creation is done
    public BlocklistDAO(Context context) {
        dbHelper = new DataBase(context);
        open();
    }

    private void open() throws SQLException {

        // Opens the database connection to provide the access
        database = dbHelper.getWritableDatabase();
    }

    public void close() {

        // Close it, once done
        dbHelper.close();
    }

    public Blocklist create(final Blocklist blackList) {

        // Steps to insert data into db (instead of using raw SQL query)
        // first, Create an object of ContentValues
        final ContentValues values = new ContentValues();

        // second, put the key-value pair into it
        values.put("phone_number", blackList.phoneNumber);

        // thirst. insert the object into the database
        final long id = database.insert(DataBase.TABLE_BLACKLIST , null, values);

        // set the primary key to object and return back
        blackList.id = id;
        return blackList;
    }

    public void delete(final Blocklist blackList) {

        // Way to delete a record from database
        database.delete(DataBase.TABLE_BLACKLIST, "phone_number = '" + blackList.phoneNumber + "'", null);
    }

    public List<Blocklist> getAllBlacklist() {

        // Steps to fetch all records from a database table
        // first, create the desired object
        final List<Blocklist> blacklistNumbers = new ArrayList<Blocklist>();

        // second, Query the database and set the result into Cursor
        final Cursor cursor = database.query(DataBase.TABLE_BLACKLIST, new String[]{"id","phone_number"}, null, null, null, null, null);

        // Move the Cursor pointer to the first
        cursor.moveToFirst();

        //Iterate over the cursor
        while (!cursor.isAfterLast()) {
            final Blocklist number = new Blocklist();

            // Fetch the desired value from the Cursor by column index
            number.id = cursor.getLong(0);
            number.phoneNumber = cursor.getString(1);

            // Add the object filled with appropriate data into the list
            blacklistNumbers.add(number);

            // Move the Cursor pointer to next for the next record to fetch
            cursor.moveToNext();
        }
        return blacklistNumbers;
    }
}
