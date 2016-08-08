package com.binarybricks.mytodolist.utils;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.binarybricks.mytodolist.R;
import com.binarybricks.mytodolist.TodoDBHelper;

/**
 * Created by PRAGYA on 8/1/2016.
 */
public class TodoCursorAdapter extends CursorAdapter {
    public TodoCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.todo_item_view, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Find fields to populate in inflated template
        TextView tvTask = (TextView) view.findViewById(R.id.tvItemTask);
        TextView tvDescription = (TextView) view.findViewById(R.id.tvItemDescription);
        TextView tvDueDate = (TextView) view.findViewById(R.id.tvSetItemDueDate);
        TextView tvPriority = (TextView) view.findViewById(R.id.tvSetPriorityColor);
        TextView tvLabel = (TextView) view.findViewById(R.id.tvItemLabel);

        // Extract properties from cursor
        String task = cursor.getString(cursor.getColumnIndexOrThrow(TodoDBHelper.TODO_COLUMN_TASK));
        String description = cursor.getString(cursor.getColumnIndexOrThrow(TodoDBHelper.TODO_COLUMN_DESCRIPTION));
        String dueDate = cursor.getString(cursor.getColumnIndexOrThrow(TodoDBHelper.TODO_COLUMN_DUE_DATE));
        String priority = cursor.getString(cursor.getColumnIndexOrThrow(TodoDBHelper.TODO_COLUMN_PRIORITY));
        String label = cursor.getString(cursor.getColumnIndexOrThrow(TodoDBHelper.TODO_COLUMN_HASHTAG));

        // Populate fields with extracted properties
        tvTask.setText(task);
        tvDescription.setText(description);
        tvDueDate.setText(dueDate);
        tvLabel.setText(label);
        switch (priority) {
            case "High":
                tvPriority.setBackgroundColor(context.getResources().getColor(R.color.colorPriorityHigh));
                break;
            case "Medium":
                tvPriority.setBackgroundColor(context.getResources().getColor(R.color.colorPriorityMedium));
                break;
            case "Low":
                tvPriority.setBackgroundColor(context.getResources().getColor(R.color.colorPriorityLow));
                break;
            default:
                tvPriority.setBackgroundColor(context.getResources().getColor(R.color.colorTextIcons));
                break;
        }
    }
}
