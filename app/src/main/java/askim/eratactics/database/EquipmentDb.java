package askim.eratactics.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import askim.eratactics.gamelogic.Equipment;

/**
 * Created by addykim on 4/29/16.
 */
public class EquipmentDb {
    private static final String DATABASE_NAME = "EraTactics";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "equipment";
    private SQLiteDatabase database;
    private DatabaseOpenHelper databaseOpenHelper;

    public EquipmentDb(Context context) {
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

    public void updateEquipment() {
        // TODO
    }

    public void updateEquipmentLeaderSkill(String description) {
        ContentValues adv = new ContentValues();
        adv.put("leaderskilldescription", description);

        open();
        database.insert(TABLE_NAME, null, adv);
        close();
    }

    public void insertEquipment(Equipment equip) {
        ContentValues newEquipment = new ContentValues();
        newEquipment.put("isEquipped", equip.isEquipped());
//        newEquipment.put("", adv.ggietEquipments());

        open();
        database.insert(TABLE_NAME, null, newEquipment);
        close();
    }

    public Cursor getEquipment(long id) {
        return database.query(TABLE_NAME, null, "_id=" + id, null, null, null, null);
    }

    public Cursor getAllEquipments() {
        return database.query(TABLE_NAME, new String[] {"_id", "name"}, null, null, null, null, "name");
    }

    public void deleteEquipment(long id) {
        open();
        database.delete(TABLE_NAME, "_id=" + id, null);
        close();
    }
}
