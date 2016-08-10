package com.binarybricks.mytodolist.provider.todo;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.binarybricks.mytodolist.provider.base.AbstractSelection;

/**
 * Selection for the {@code todo} table.
 */
public class TodoSelection extends AbstractSelection<TodoSelection> {
    @Override
    protected Uri baseUri() {
        return TodoColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code TodoCursor} object, which is positioned before the first entry, or null.
     */
    public TodoCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new TodoCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public TodoCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code TodoCursor} object, which is positioned before the first entry, or null.
     */
    public TodoCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new TodoCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public TodoCursor query(Context context) {
        return query(context, null);
    }


    public TodoSelection id(long... value) {
        addEquals("todo." + TodoColumns._ID, toObjectArray(value));
        return this;
    }

    public TodoSelection idNot(long... value) {
        addNotEquals("todo." + TodoColumns._ID, toObjectArray(value));
        return this;
    }

    public TodoSelection orderById(boolean desc) {
        orderBy("todo." + TodoColumns._ID, desc);
        return this;
    }

    public TodoSelection orderById() {
        return orderById(false);
    }

    public TodoSelection id(String... value) {
        addEquals(TodoColumns.ID, value);
        return this;
    }

    public TodoSelection idNot(String... value) {
        addNotEquals(TodoColumns.ID, value);
        return this;
    }

    public TodoSelection idLike(String... value) {
        addLike(TodoColumns.ID, value);
        return this;
    }

    public TodoSelection idContains(String... value) {
        addContains(TodoColumns.ID, value);
        return this;
    }

    public TodoSelection idStartsWith(String... value) {
        addStartsWith(TodoColumns.ID, value);
        return this;
    }

    public TodoSelection idEndsWith(String... value) {
        addEndsWith(TodoColumns.ID, value);
        return this;
    }

    public TodoSelection task(String... value) {
        addEquals(TodoColumns.TASK, value);
        return this;
    }

    public TodoSelection taskNot(String... value) {
        addNotEquals(TodoColumns.TASK, value);
        return this;
    }

    public TodoSelection taskLike(String... value) {
        addLike(TodoColumns.TASK, value);
        return this;
    }

    public TodoSelection taskContains(String... value) {
        addContains(TodoColumns.TASK, value);
        return this;
    }

    public TodoSelection taskStartsWith(String... value) {
        addStartsWith(TodoColumns.TASK, value);
        return this;
    }

    public TodoSelection taskEndsWith(String... value) {
        addEndsWith(TodoColumns.TASK, value);
        return this;
    }

    public TodoSelection orderByTask(boolean desc) {
        orderBy(TodoColumns.TASK, desc);
        return this;
    }

    public TodoSelection orderByTask() {
        orderBy(TodoColumns.TASK, false);
        return this;
    }

    public TodoSelection description(String... value) {
        addEquals(TodoColumns.DESCRIPTION, value);
        return this;
    }

    public TodoSelection descriptionNot(String... value) {
        addNotEquals(TodoColumns.DESCRIPTION, value);
        return this;
    }

    public TodoSelection descriptionLike(String... value) {
        addLike(TodoColumns.DESCRIPTION, value);
        return this;
    }

    public TodoSelection descriptionContains(String... value) {
        addContains(TodoColumns.DESCRIPTION, value);
        return this;
    }

    public TodoSelection descriptionStartsWith(String... value) {
        addStartsWith(TodoColumns.DESCRIPTION, value);
        return this;
    }

    public TodoSelection descriptionEndsWith(String... value) {
        addEndsWith(TodoColumns.DESCRIPTION, value);
        return this;
    }

    public TodoSelection orderByDescription(boolean desc) {
        orderBy(TodoColumns.DESCRIPTION, desc);
        return this;
    }

    public TodoSelection orderByDescription() {
        orderBy(TodoColumns.DESCRIPTION, false);
        return this;
    }

    public TodoSelection duedate(String... value) {
        addEquals(TodoColumns.DUEDATE, value);
        return this;
    }

    public TodoSelection duedateNot(String... value) {
        addNotEquals(TodoColumns.DUEDATE, value);
        return this;
    }

    public TodoSelection duedateLike(String... value) {
        addLike(TodoColumns.DUEDATE, value);
        return this;
    }

    public TodoSelection duedateContains(String... value) {
        addContains(TodoColumns.DUEDATE, value);
        return this;
    }

    public TodoSelection duedateStartsWith(String... value) {
        addStartsWith(TodoColumns.DUEDATE, value);
        return this;
    }

    public TodoSelection duedateEndsWith(String... value) {
        addEndsWith(TodoColumns.DUEDATE, value);
        return this;
    }

    public TodoSelection orderByDuedate(boolean desc) {
        orderBy(TodoColumns.DUEDATE, desc);
        return this;
    }

    public TodoSelection orderByDuedate() {
        orderBy(TodoColumns.DUEDATE, false);
        return this;
    }

    public TodoSelection priority(String... value) {
        addEquals(TodoColumns.PRIORITY, value);
        return this;
    }

    public TodoSelection priorityNot(String... value) {
        addNotEquals(TodoColumns.PRIORITY, value);
        return this;
    }

    public TodoSelection priorityLike(String... value) {
        addLike(TodoColumns.PRIORITY, value);
        return this;
    }

    public TodoSelection priorityContains(String... value) {
        addContains(TodoColumns.PRIORITY, value);
        return this;
    }

    public TodoSelection priorityStartsWith(String... value) {
        addStartsWith(TodoColumns.PRIORITY, value);
        return this;
    }

    public TodoSelection priorityEndsWith(String... value) {
        addEndsWith(TodoColumns.PRIORITY, value);
        return this;
    }

    public TodoSelection orderByPriority(boolean desc) {
        orderBy(TodoColumns.PRIORITY, desc);
        return this;
    }

    public TodoSelection orderByPriority() {
        orderBy(TodoColumns.PRIORITY, false);
        return this;
    }

    public TodoSelection hashtag(String... value) {
        addEquals(TodoColumns.HASHTAG, value);
        return this;
    }

    public TodoSelection hashtagNot(String... value) {
        addNotEquals(TodoColumns.HASHTAG, value);
        return this;
    }

    public TodoSelection hashtagLike(String... value) {
        addLike(TodoColumns.HASHTAG, value);
        return this;
    }

    public TodoSelection hashtagContains(String... value) {
        addContains(TodoColumns.HASHTAG, value);
        return this;
    }

    public TodoSelection hashtagStartsWith(String... value) {
        addStartsWith(TodoColumns.HASHTAG, value);
        return this;
    }

    public TodoSelection hashtagEndsWith(String... value) {
        addEndsWith(TodoColumns.HASHTAG, value);
        return this;
    }

    public TodoSelection orderByHashtag(boolean desc) {
        orderBy(TodoColumns.HASHTAG, desc);
        return this;
    }

    public TodoSelection orderByHashtag() {
        orderBy(TodoColumns.HASHTAG, false);
        return this;
    }

    public TodoSelection status(String... value) {
        addEquals(TodoColumns.STATUS, value);
        return this;
    }

    public TodoSelection statusNot(String... value) {
        addNotEquals(TodoColumns.STATUS, value);
        return this;
    }

    public TodoSelection statusLike(String... value) {
        addLike(TodoColumns.STATUS, value);
        return this;
    }

    public TodoSelection statusContains(String... value) {
        addContains(TodoColumns.STATUS, value);
        return this;
    }

    public TodoSelection statusStartsWith(String... value) {
        addStartsWith(TodoColumns.STATUS, value);
        return this;
    }

    public TodoSelection statusEndsWith(String... value) {
        addEndsWith(TodoColumns.STATUS, value);
        return this;
    }

    public TodoSelection orderByStatus(boolean desc) {
        orderBy(TodoColumns.STATUS, desc);
        return this;
    }

    public TodoSelection orderByStatus() {
        orderBy(TodoColumns.STATUS, false);
        return this;
    }
}
