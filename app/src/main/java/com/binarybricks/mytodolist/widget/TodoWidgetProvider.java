package com.binarybricks.mytodolist.widget;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.binarybricks.mytodolist.R;
import com.binarybricks.mytodolist.ToDoListActivity;

/**
 * Created by PRAGYA on 8/8/2016.
 */
public class TodoWidgetProvider extends AppWidgetProvider {

    public static final String ACTION_TOAST = "widgets.ACTION_TOAST";
    public static final String EXTRA_STRING = "widgets.EXTRA_STRING";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int widgetId : appWidgetIds) {
            RemoteViews mView = initViews(context, appWidgetManager, widgetId);

            // Adding collection list item handler
            final Intent onItemClick = new Intent(context, ToDoListActivity.class);
            PendingIntent clickPI = PendingIntent.getActivity(context, 0, onItemClick, PendingIntent.FLAG_UPDATE_CURRENT);
            mView.setPendingIntentTemplate(R.id.lvTodoWidgetList, clickPI);
            appWidgetManager.updateAppWidget(widgetId, mView);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private RemoteViews initViews(Context context, AppWidgetManager widgetManager, int widgetId) {

        RemoteViews mView = new RemoteViews(context.getPackageName(), R.layout.todo_widget);

        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        mView.setRemoteAdapter(widgetId, R.id.lvTodoWidgetList, intent);

        return mView;
    }

}
