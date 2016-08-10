package com.binarybricks.mytodolist;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.binarybricks.mytodolist.provider.SQLOpenHelper;
import com.binarybricks.mytodolist.utils.TodoCursorAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompletedTaskActivity extends AppCompatActivity {

    @BindView(R.id.tvNoResultsMessage)
    TextView tvNoResult;

    @BindView(R.id.lvTodoList)
    ListView lvTodoItemList;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private SQLOpenHelper todoDBHelper;
    private TodoCursorAdapter todoCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.complete_task_activity_title);

        lvTodoItemList = (ListView) findViewById(R.id.lvTodoList);
        tvNoResult = (TextView) findViewById(R.id.tvNoResultsMessage);

        todoDBHelper = SQLOpenHelper.getInstance(CompletedTaskActivity.this);
        Cursor cursor = todoDBHelper.getCompletedTodoList();
        if (cursor.getCount() > 0) {
            tvNoResult.setVisibility(View.INVISIBLE);
        } else {
            tvNoResult.setVisibility(View.VISIBLE);
            tvNoResult.setText(R.string.no_task_complete);
        }
        todoCursorAdapter = new TodoCursorAdapter(CompletedTaskActivity.this,cursor);
        lvTodoItemList.setAdapter(todoCursorAdapter);
    }
}
