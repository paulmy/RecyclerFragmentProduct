package ru.myitschool.lesson20230214;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



import java.util.ArrayList;


public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "product.db"; // название бд
    private static final int VERSION = 1; // версия базы данных
    static final String TABLE_NAME = "products"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_COUNT = "count";

    public static int NUM_COLUMN_ID = 0;
    public static final int NUM_COLUMN_TITLE = 1;
    public static final int NUM_COLUMN_DESCRIPTION = 2;
    public static final int NUM_COLUMN_COUNT = 3;

   static SQLiteDatabase database;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        database = getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TITLE
                + " TEXT, " + COLUMN_DESCRIPTION
                + " TEXT, " + COLUMN_COUNT + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void add(ProductData data) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, data.getName());
        cv.put(COLUMN_DESCRIPTION, data.getDescription());
        cv.put(COLUMN_COUNT, data.getCount());
        database.insert(TABLE_NAME, null, cv);
    }

    public ArrayList<ProductData> getAll() {
        Cursor cursor = database.query(TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        ArrayList<ProductData> result = new ArrayList<>();
        //if (cursor.isAfterLast()) return new ArrayList<>();
        do {
            result.add(
                    new ProductData(
                            cursor.getString(NUM_COLUMN_TITLE),
                            cursor.getString(NUM_COLUMN_DESCRIPTION),
                            cursor.getInt(NUM_COLUMN_COUNT))
            );

        } while (cursor.moveToNext());
        cursor.close();
        return result;
    }

    @Override
    public synchronized void close() {
        super.close();
        database.close();
        Log.d("DB","CLOSE DB in DBH synchronized void close()");
    }

    public void removeProduct(long position) {
        NUM_COLUMN_ID = (int) position;
        //db.delete(DatabaseHelper.TABLE, "_id = ?", new String[]{String.valueOf(userId)});
        //database.delete(TABLE_NAME,"_id = ?",new String[]{String.valueOf(NUM_COLUMN_ID)});
    }
}
