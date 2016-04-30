package askim.eratactics.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by addykim on 4/29/16.
 */
public class LevelSelectDb {
    private static final String DATABASE_NAME = "EraTactics";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "level";
    private SQLiteDatabase database;
    private DatabaseOpenHelper databaseOpenHelper;

    public LevelSelectDb(Context context) {
        databaseOpenHelper = new DatabaseOpenHelper(context);
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
            String createQuery = "CREATE TABLE " + TABLE_NAME +
                    "(_id INTEGER PRIMARY KEY autoincrement, " +
                    "name TEXT, " +
                    "" +
                    "leaderskilldescription TEXT);";

            db.execSQL(createQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
            onCreate(db);
        }

    }

    public void updateLevel() {
        // TODO
    }

    public void updateLevel(String description) {
        ContentValues adv = new ContentValues();
//        adv.put("leaderskilldescription", description);

        open();
        database.insert(TABLE_NAME, null, adv);
        close();
    }

//    public void insertLevel(Level adv) {
//        ContentValues newLevel = new ContentValues();
//        newLevel.put("name", adv.getLevelName());
//        newLevel.put("", adv.getLevelClass());
//         TODO pass equipments somehow
//        newLevel.put("", adv.getEquipments());
//        newLevel.put("leaderskilldescription", adv.getLeaderSkillDescription());

//        open();
//        database.insert(TABLE_NAME, null, newLevel);
//        close();
//    }

    public Cursor getLevel(long id) {
        return database.query(TABLE_NAME, null, "_id=" + id, null, null, null, null);
    }

    public Cursor getAllLevels() {
        return database.query(TABLE_NAME, new String[] {"_id", "name"}, null, null, null, null, "name");
    }

    public void deleteLevel(long id) {
        open();
        database.delete(TABLE_NAME, "_id=" + id, null);
        close();
    }
}
