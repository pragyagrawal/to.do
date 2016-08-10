package com.binarybricks.mytodolist.provider.todo;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.binarybricks.mytodolist.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code todo} table.
 */
public class TodoContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return TodoColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable TodoSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable TodoSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * ID of the Todo
     */
    public TodoContentValues putId(@Nullable String value) {
        mContentValues.put(TodoColumns.ID, value);
        return this;
    }

    public TodoContentValues putIdNull() {
        mContentValues.putNull(TodoColumns.ID);
        return this;
    }

    /**
     * Todo Task Title
     */
    public TodoContentValues putTask(@Nullable String value) {
        mContentValues.put(TodoColumns.TASK, value);
        return this;
    }

    public TodoContentValues putTaskNull() {
        mContentValues.putNull(TodoColumns.TASK);
        return this;
    }

    /**
     * Todo Task description
     */
    public TodoContentValues putDescription(@Nullable String value) {
        mContentValues.put(TodoColumns.DESCRIPTION, value);
        return this;
    }

    public TodoContentValues putDescriptionNull() {
        mContentValues.putNull(TodoColumns.DESCRIPTION);
        return this;
    }

    /**
     * Task due date
     */
    public TodoContentValues putDuedate(@Nullable String value) {
        mContentValues.put(TodoColumns.DUEDATE, value);
        return this;
    }

    public TodoContentValues putDuedateNull() {
        mContentValues.putNull(TodoColumns.DUEDATE);
        return this;
    }

    /**
     * Task priority
     */
    public TodoContentValues putPriority(@Nullable String value) {
        mContentValues.put(TodoColumns.PRIORITY, value);
        return this;
    }

    public TodoContentValues putPriorityNull() {
        mContentValues.putNull(TodoColumns.PRIORITY);
        return this;
    }

    /**
     * Task hashtag or label
     */
    public TodoContentValues putHashtag(@Nullable String value) {
        mContentValues.put(TodoColumns.HASHTAG, value);
        return this;
    }

    public TodoContentValues putHashtagNull() {
        mContentValues.putNull(TodoColumns.HASHTAG);
        return this;
    }

    /**
     * Task status
     */
    public TodoContentValues putStatus(@Nullable String value) {
        mContentValues.put(TodoColumns.STATUS, value);
        return this;
    }

    public TodoContentValues putStatusNull() {
        mContentValues.putNull(TodoColumns.STATUS);
        return this;
    }
}
