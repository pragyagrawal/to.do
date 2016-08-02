package com.binarybricks.mytodolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ToDoListActivity extends AppCompatActivity {

    @BindView(R.id.lvTodoList)
    ListView lvTodoItemList;

    @BindView(R.id.emptyListMessage)
    TextView tvEmptyListMessage;

    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    TodoDBHelper todoDBHelper;
    SimpleCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        todoDBHelper = new TodoDBHelper(ToDoListActivity.this);

        fetchTodoList();

        lvTodoItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) lvTodoItemList.getItemAtPosition(position);
                String task = cursor.getString(cursor.getColumnIndexOrThrow(TodoDBHelper.TODO_COLUMN_TASK));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(TodoDBHelper.TODO_COLUMN_DESCRIPTION));
                String dueDate = cursor.getString(cursor.getColumnIndexOrThrow(TodoDBHelper.TODO_COLUMN_DUE_DATE));
                String priority = cursor.getString(cursor.getColumnIndexOrThrow(TodoDBHelper.TODO_COLUMN_PRIORITY));
                String hashTag = cursor.getString(cursor.getColumnIndexOrThrow(TodoDBHelper.TODO_COLUMN_HASHTAG));
                Integer taskId = cursor.getInt(cursor.getColumnIndexOrThrow(TodoDBHelper.TODO_COLUMN_ID));

                Intent editTodoIntent = new Intent(ToDoListActivity.this, EditTodoItemActivity.class);
                editTodoIntent.putExtra("task_id", taskId);
                editTodoIntent.putExtra("edit_task", task);
                editTodoIntent.putExtra("edit_description", description);
                editTodoIntent.putExtra("edit_due_date", dueDate);
                editTodoIntent.putExtra("edit_priority", priority);
                editTodoIntent.putExtra("edit_hash_tag", hashTag);
                startActivityForResult(editTodoIntent, 100);
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emptyIntent = new Intent(ToDoListActivity.this, EditTodoItemActivity.class);
                startActivityForResult(emptyIntent, 100);
            }
        });

        lvTodoItemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) lvTodoItemList.getItemAtPosition(position);
                Integer taskId = cursor.getInt(cursor.getColumnIndexOrThrow(TodoDBHelper.TODO_COLUMN_ID));
                showDeleteDialog(taskId);
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 100
        if (requestCode == 100 && resultCode == RESULT_OK) {
            fetchTodoList();
        }
    }

    private void fetchTodoList() {
        // The desired columns to be bound
        String[] columns = new String[]{
                todoDBHelper.TODO_COLUMN_TASK, todoDBHelper.TODO_COLUMN_DESCRIPTION, todoDBHelper.TODO_COLUMN_DUE_DATE, todoDBHelper.TODO_COLUMN_PRIORITY
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.tvItemTask, R.id.tvItemDescription, R.id.tvSetItemDueDate, R.id.tvSetPriorityColor
        };

        Cursor cursor = todoDBHelper.getTodoList();
        if (cursor.getCount() > 0) {
            tvEmptyListMessage.setVisibility(View.INVISIBLE);
        } else {
            tvEmptyListMessage.setVisibility(View.VISIBLE);
        }
        cursorAdapter = new SimpleCursorAdapter(ToDoListActivity.this, R.layout.todo_item_view, cursor, columns, to, 0);
        lvTodoItemList.setAdapter(cursorAdapter);
    }

    private void showDeleteDialog(final Integer taskId) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getString(R.string.alert_dialog_title));
        alertDialogBuilder.setMessage(getString(R.string.alert_message));
        alertDialogBuilder.setPositiveButton(getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                todoDBHelper.deleteTodoItem(taskId);
                fetchTodoList();
            }
        });
        alertDialogBuilder.setNegativeButton(getString(R.string.alert_negative), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialogBuilder.show();
    }
}
