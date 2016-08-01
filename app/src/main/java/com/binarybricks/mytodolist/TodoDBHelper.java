package com.binarybricks.mytodolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pragya on 6/22/16.
 */
public class TodoDBHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "TodoDatabase";
    public static final String TODO_TABLE_NAME = "todoList";
    public static final String TODO_COLUMN_ID = "_id";
    public static final String TODO_COLUMN_TASK = "task";
    public static final String TODO_COLUMN_DESCRIPTION = "description";
    public static final String TODO_COLUMN_DUE_DATE = "dueDate";
    public static final String TODO_COLUMN_PRIORITY = "priority";
    public static final String TODO_COLUMN_HASHTAG = "hashTag";

    public TodoDBHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table todoList " +
                        "(_id integer primary key, task text, description text, dueDate text, priority text, hashTag text)"
        );
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
    }

    public boolean insertTodoItem(String task, String description, String dueDate, String priority, String hashTag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TODO_COLUMN_TASK, task);
        contentValues.put(TODO_COLUMN_DESCRIPTION, description);
        contentValues.put(TODO_COLUMN_DUE_DATE, dueDate);
        contentValues.put(TODO_COLUMN_PRIORITY, priority);
        contentValues.put(TODO_COLUMN_HASHTAG, hashTag);
        db.insert(TODO_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getTodoList() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from todoList", null);
        return res;
    }

    public boolean updateTodoList(Integer id, String updatedTask, String updatedDescription, String updatedDueDate, String updatedPriotity, String updatedHashTag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TODO_COLUMN_TASK, updatedTask);
        contentValues.put(TODO_COLUMN_DESCRIPTION, updatedDescription);
        contentValues.put(TODO_COLUMN_DUE_DATE, updatedDueDate);
        contentValues.put(TODO_COLUMN_PRIORITY, updatedPriotity);
        contentValues.put(TODO_COLUMN_HASHTAG, updatedHashTag);
        db.update(TODO_TABLE_NAME, contentValues, "_id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteTodoItem(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TODO_TABLE_NAME,
                "_id = ? ",
                new String[]{Integer.toString(id)});
    }
}
