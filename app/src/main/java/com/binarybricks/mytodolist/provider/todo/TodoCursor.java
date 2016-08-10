package com.binarybricks.mytodolist.provider.todo;

import android.database.Cursor;
import android.support.annotation.Nullable;

import com.binarybricks.mytodolist.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code todo} table.
 */
public class TodoCursor extends AbstractCursor implements TodoModel {
    public TodoCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(TodoColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Todo Task Title
     * Can be {@code null}.
     */
    @Nullable
    public String getTask() {
        String res = getStringOrNull(TodoColumns.TASK);
        return res;
    }

    /**
     * Todo Task description
     * Can be {@code null}.
     */
    @Nullable
    public String getDescription() {
        String res = getStringOrNull(TodoColumns.DESCRIPTION);
        return res;
    }

    /**
     * Task due date
     * Can be {@code null}.
     */
    @Nullable
    public String getDuedate() {
        String res = getStringOrNull(TodoColumns.DUEDATE);
        return res;
    }

    /**
     * Task priority
     * Can be {@code null}.
     */
    @Nullable
    public String getPriority() {
        String res = getStringOrNull(TodoColumns.PRIORITY);
        return res;
    }

    /**
     * Task hashtag or label
     * Can be {@code null}.
     */
    @Nullable
    public String getHashtag() {
        String res = getStringOrNull(TodoColumns.HASHTAG);
        return res;
    }

    /**
     * Task status
     * Can be {@code null}.
     */
    @Nullable
    public String getStatus() {
        String res = getStringOrNull(TodoColumns.STATUS);
        return res;
    }
}
