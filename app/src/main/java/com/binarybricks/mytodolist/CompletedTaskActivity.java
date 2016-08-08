package com.binarybricks.mytodolist;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.binarybricks.mytodolist.utils.TodoCursorAdapter;

public class CompletedTaskActivity extends AppCompatActivity {

    private TextView tvNoResult;
    private ListView lvTodoItemList;
    private TodoDBHelper todoDBHelper;
    private TodoCursorAdapter todoCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        lvTodoItemList = (ListView) findViewById(R.id.lvTodoList);
        tvNoResult = (TextView) findViewById(R.id.tvNoResultsMessage);

        todoDBHelper = new TodoDBHelper(CompletedTaskActivity.this);
        Cursor cursor = todoDBHelper.getCompletedTodoList();
        if (cursor.getCount() > 0) {
            tvNoResult.setVisibility(View.INVISIBLE);
        } else {
            tvNoResult.setVisibility(View.VISIBLE);
            tvNoResult.setText("No task completed.");
        }
        todoCursorAdapter = new TodoCursorAdapter(CompletedTaskActivity.this,cursor);
        lvTodoItemList.setAdapter(todoCursorAdapter);
    }
}
