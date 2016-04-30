package askim.eratactics.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import askim.eratactics.gamelogic.Adventurer;

/**
 * Created by addykim on 4/29/16.
 */
public class AdventurerDb {
    private static final String DATABASE_NAME = "EraTactics";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "adventurers";
    private SQLiteDatabase database;
    private DatabaseOpenHelper databaseOpenHelper;

    public AdventurerDb(Context context) {
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
//                    TODO query "" +
                    "leaderskilldescription TEXT);";

            db.execSQL(createQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
            onCreate(db);
        }

    }

    public void updateAdventurer() {
        // TODO
    }

    public void updateAdventurerLeaderSkill(String description) {
        ContentValues adv = new ContentValues();
        adv.put("leaderskilldescription", description);

        open();
        database.insert(TABLE_NAME, null, adv);
        close();
    }

    public void insertAdventurer(Adventurer adv) {
        ContentValues newAdventurer = new ContentValues();
        newAdventurer.put("name", adv.getAdventurerName());
        newAdventurer.put("", adv.getAdventurerClass());
        // TODO pass equipments somehow
//        newAdventurer.put("", adv.getEquipments());
        newAdventurer.put("leaderskilldescription", adv.getLeaderSkillDescription());

        open();
        database.insert(TABLE_NAME, null, newAdventurer);
        close();
    }

    public Cursor getAdventurer(long id) {
        return database.query(TABLE_NAME, null, "_id=" + id, null, null, null, null);
    }

    public Cursor getAllAdventurers() {
        return database.query(TABLE_NAME, new String[] {"_id", "name"}, null, null, null, null, "name");
    }

    public void deleteAdventurer(long id) {
        open();
        database.delete(TABLE_NAME, "_id=" + id, null);
        close();
    }
}
