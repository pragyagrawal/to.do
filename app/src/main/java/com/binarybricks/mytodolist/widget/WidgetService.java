package com.binarybricks.mytodolist.widget;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViewsService;

import com.binarybricks.mytodolist.provider.TodoDataProvider;
import com.binarybricks.mytodolist.provider.todo.TodoColumns;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        WidgetDataProvider dataProvider = new WidgetDataProvider(getApplicationContext(), intent);
        Uri CONTENT_URI = Uri.parse(TodoDataProvider.CONTENT_URI_BASE + "/" + TodoColumns.TABLE_NAME);
        Cursor cursor = getContentResolver().query(CONTENT_URI,
                new String[]{ TodoColumns._ID, TodoColumns.TASK, TodoColumns.DESCRIPTION,
                        TodoColumns.HASHTAG, TodoColumns.DUEDATE,TodoColumns.PRIORITY},
                TodoColumns.STATUS + " != ?",
                new String[]{"1"},
                null);
        dataProvider.setWidgetCursor(cursor);
        return dataProvider;
    }
}
