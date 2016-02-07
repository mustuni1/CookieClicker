package com.github.mustuni1.cookieclicker;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import com.github.mustuni1.cookieclicker.Book;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "BookDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_BOOK_TABLE = "CREATE TABLE books ( " +
                "int ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "time LONG, " +
                "score INT, "+
                "onOrOff BOOLEAN )";

        // create books table
        db.execSQL(CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS books");

        // create fresh books table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
     */

    // Books table name
    private static final String TABLE_BOOKS = "books";

    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TIME = "time";
    private static final String KEY_SCORE = "score";
    private static final String KEY_ONOROFF = "onOrOff";

    private static final String[] COLUMNS = {KEY_TIME,KEY_SCORE,KEY_ONOROFF};

    public void addBook(Book book){
        Log.d("addBook", book.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        long time = Calendar.getInstance().getTimeInMillis();

        book.setTime(time);
        book.addTime(time);

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TIME, book.getTime()); // get time
        values.put(KEY_SCORE, book.getScore()); // get score
        values.put(KEY_ONOROFF, book.getOnOff());

        // 3. insert
        db.insert(TABLE_BOOKS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    // Updating single book
    public int updateBook(Book book) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        book.addTime(Calendar.getInstance().getTimeInMillis());

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("time", book.getTime()); // get title
        values.put("score", book.getScore()); // get author

        // 3. updating row
        int i = db.update(TABLE_BOOKS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(1) }); //selection args

        // 4. close
        db.close();

        return i;

    }
}