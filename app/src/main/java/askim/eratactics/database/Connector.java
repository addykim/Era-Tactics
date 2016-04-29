package askim.eratactics.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by addykim on 4/29/16.
 */
public class Connector {
    private static final String DATABASE_NAME = "Characters";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase database;
    private DatabaseOpenHelper databaseOpenHelper;

    public Connector(Context context) {
        databaseOpenHelper =
                new DatabaseOpenHelper(context);
    }

    public void open() throws SQLException {
        database = databaseOpenHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null)
            database.close();
    }

    private class DatabaseOpenHelper extends SQLiteOpenHelper {
        public DatabaseOpenHelper(Context context) {
            super (context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createQuery = "";
            db.execSQL(createQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);;
            onCreate(db);
        }

    }

}
