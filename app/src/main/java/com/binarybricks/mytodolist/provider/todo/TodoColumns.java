package com.binarybricks.mytodolist.provider.todo;

import android.net.Uri;
import android.provider.BaseColumns;

import com.binarybricks.mytodolist.provider.TodoDataProvider;
import com.binarybricks.mytodolist.provider.todo.TodoColumns;

/**
 * This table will be storing Todo List
 */
public class TodoColumns implements BaseColumns {
    public static final String TABLE_NAME = "todo";
    public static final Uri CONTENT_URI = Uri.parse(TodoDataProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * ID of the Todo
     */
    public static final String ID = "id";

    /**
     * Todo Task Title
     */
    public static final String TASK = "task";

    /**
     * Todo Task description
     */
    public static final String DESCRIPTION = "description";

    /**
     * Task due date
     */
    public static final String DUEDATE = "dueDate";

    /**
     * Task priority
     */
    public static final String PRIORITY = "priority";

    /**
     * Task hashtag or label
     */
    public static final String HASHTAG = "hashTag";

    /**
     * Task status
     */
    public static final String STATUS = "status";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            ID,
            TASK,
            DESCRIPTION,
            DUEDATE,
            PRIORITY,
            HASHTAG,
            STATUS
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(ID) || c.contains("." + ID)) return true;
            if (c.equals(TASK) || c.contains("." + TASK)) return true;
            if (c.equals(DESCRIPTION) || c.contains("." + DESCRIPTION)) return true;
            if (c.equals(DUEDATE) || c.contains("." + DUEDATE)) return true;
            if (c.equals(PRIORITY) || c.contains("." + PRIORITY)) return true;
            if (c.equals(HASHTAG) || c.contains("." + HASHTAG)) return true;
            if (c.equals(STATUS) || c.contains("." + STATUS)) return true;
        }
        return false;
    }

}
