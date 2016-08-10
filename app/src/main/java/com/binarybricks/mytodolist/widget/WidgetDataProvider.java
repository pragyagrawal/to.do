package com.binarybricks.mytodolist.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.binarybricks.mytodolist.R;
import com.binarybricks.mytodolist.provider.todo.TodoColumns;

/**
 * Created by PRAGYA on 8/8/2016.
 */
public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {
    private Cursor widgetCursor = null;
    private Context mContext = null;

    public WidgetDataProvider(Context context, Intent intent) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Log.d("", "");
    }

    public void setWidgetCursor(Cursor widgetCursor) {
        this.widgetCursor = widgetCursor;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (widgetCursor != null) {
            return widgetCursor.getCount();
        }
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews mView = new RemoteViews(mContext.getPackageName(), R.layout.todo_widget_item);
        if (widgetCursor != null) {
            Cursor mCursor = widgetCursor;
            mCursor.moveToPosition(position);
            mView.setTextViewText(R.id.tvWidgetItemTask, mCursor.getString(mCursor.getColumnIndex(TodoColumns.TASK)));
            mView.setTextViewText(R.id.tvWidgetItemDescription, mCursor.getString(mCursor.getColumnIndex(TodoColumns.DESCRIPTION)));
            mView.setTextViewText(R.id.tvWidgetItemLabel, mCursor.getString(mCursor.getColumnIndex(TodoColumns.HASHTAG)));
            mView.setTextViewText(R.id.tvWidgetSetItemDueDate, mCursor.getString(mCursor.getColumnIndex(TodoColumns.DUEDATE)));

            final Intent fillInIntent = new Intent();
            mView.setOnClickFillInIntent(R.id.widgetTodoItem, fillInIntent);
        }
        return mView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
