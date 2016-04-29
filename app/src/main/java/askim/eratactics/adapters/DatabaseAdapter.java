package askim.eratactics.adapters;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by addykim on 4/29/16.
 */
public class DatabaseAdapter {
    private static final String DATABASE_NAME = "Characters";
    private SQLiteDatabase database;
    private DatabaseOpenHelper databaseOpenHelper;

    public DatabaseAdapter(Context context) {
        databaseOpenHelper =
                new DatabaseOpenHelper(context, DATABASE_NAME);
    }

    public void open() throws SQLException {
        database = databaseOpenHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null)
            database.close();
    }

    private class DatabaseOpenHelper extends SQLiteOpenHelper {
        public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, null, 1);
            }
        }
    }

}
