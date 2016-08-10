package com.binarybricks.mytodolist.provider;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.binarybricks.mytodolist.BuildConfig;
import com.binarybricks.mytodolist.provider.todo.TodoColumns;

public class SQLOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = SQLOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 1;
    private static SQLOpenHelper sInstance;
    private final Context mContext;
    private final SQLOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_TODO = "CREATE TABLE IF NOT EXISTS "
            + TodoColumns.TABLE_NAME + " ( "
            + TodoColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TodoColumns.ID + " TEXT, "
            + TodoColumns.TASK + " TEXT, "
            + TodoColumns.DESCRIPTION + " TEXT, "
            + TodoColumns.DUEDATE + " TEXT, "
            + TodoColumns.PRIORITY + " TEXT, "
            + TodoColumns.HASHTAG + " TEXT, "
            + TodoColumns.STATUS + " TEXT "
            + " );";

    // @formatter:on

    public static SQLOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static SQLOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static SQLOpenHelper newInstancePreHoneycomb(Context context) {
        return new SQLOpenHelper(context);
    }

    private SQLOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new SQLOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static SQLOpenHelper newInstancePostHoneycomb(Context context) {
        return new SQLOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private SQLOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new SQLOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_TODO);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }

    public boolean insertTodoItem(String task, String description, String dueDate, String priority, String hashTag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TodoColumns.TASK, task);
        contentValues.put(TodoColumns.DESCRIPTION, description);
        contentValues.put(TodoColumns.DUEDATE, dueDate);
        contentValues.put(TodoColumns.PRIORITY, priority);
        contentValues.put(TodoColumns.HASHTAG, hashTag);
        contentValues.put(TodoColumns.STATUS, 0);
        db.insert(TodoColumns.TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getTodoList() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TodoColumns.TABLE_NAME +" where "+TodoColumns.STATUS+"!=1", null);
        return res;
    }

    public boolean updateTodoList(Long id, String updatedTask, String updatedDescription, String updatedDueDate, String updatedPriotity, String updatedHashTag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TodoColumns.TASK, updatedTask);
        contentValues.put(TodoColumns.DESCRIPTION, updatedDescription);
        contentValues.put(TodoColumns.DUEDATE, updatedDueDate);
        contentValues.put(TodoColumns.PRIORITY, updatedPriotity);
        contentValues.put(TodoColumns.HASHTAG, updatedHashTag);
        db.update(TodoColumns.TABLE_NAME, contentValues, "_id = ? ", new String[]{Long.toString(id)});
        return true;
    }

    public boolean updateStatus(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TodoColumns.STATUS, 1);
        db.update(TodoColumns.TABLE_NAME, contentValues, TodoColumns._ID+" = ?", new String[]{Long.toString(id)});
        return true;
    }

    public Integer deleteTodoItem(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TodoColumns.TABLE_NAME,
                TodoColumns._ID+" = ?",
                new String[]{Long.toString(id)});
    }

    public Cursor getSearchResult(String task) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+ TodoColumns.TABLE_NAME +" where "+ TodoColumns.TASK +" like '%" + task +
                "%' or "+TodoColumns.HASHTAG+" like '%" + task + "%'", null);
        return res;
    }

    public Cursor getCompletedTodoList() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TodoColumns.TABLE_NAME+" where "+TodoColumns.STATUS+"==1", null);
        return res;
    }
}
