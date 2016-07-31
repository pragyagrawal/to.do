package com.binarybricks.mytodolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ToDoListActivity extends AppCompatActivity {

    private GridView gvTodoItemList;
    private EditText etAddTodoText;
    private TextView tvEmptyListMessage;

    TodoDBHelper todoDBHelper;
    SimpleCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        gvTodoItemList = (GridView) findViewById(R.id.gvTodoList);
        etAddTodoText = (EditText) findViewById(R.id.etAddTodoText);
        tvEmptyListMessage = (TextView) findViewById(R.id.emptyListMessage);

        todoDBHelper = new TodoDBHelper(ToDoListActivity.this);

        fetchTodoList();

        gvTodoItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) gvTodoItemList.getItemAtPosition(position);
                String itemValue = cursor.getString(cursor.getColumnIndexOrThrow(TodoDBHelper.TODO_COLUMN_TASK));
                String itemTitle = cursor.getString(cursor.getColumnIndexOrThrow(TodoDBHelper.TODO_COLUMN_DESCRIPTION));
                Integer itemId = cursor.getInt(cursor.getColumnIndexOrThrow(TodoDBHelper.TODO_COLUMN_ID));

                Intent editTodoIntent = new Intent(ToDoListActivity.this, EditTodoItemActivity.class);
                editTodoIntent.putExtra("edit_item_id", itemId);
                editTodoIntent.putExtra("edit_item_value", itemValue);
                editTodoIntent.putExtra("edit_item_title", itemTitle);
                startActivityForResult(editTodoIntent, 100);
            }
        });

        etAddTodoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emptyIntent = new Intent(ToDoListActivity.this, EditTodoItemActivity.class);
                startActivityForResult(emptyIntent, 100);
            }
        });

        gvTodoItemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                Cursor cursor = (Cursor) gvTodoItemList.getItemAtPosition(pos);
                Integer itemId = cursor.getInt(cursor.getColumnIndexOrThrow(TodoDBHelper.TODO_COLUMN_ID));
                showDeleteDialog(itemId);
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
                todoDBHelper.TODO_COLUMN_TASK, todoDBHelper.TODO_COLUMN_DESCRIPTION
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.tvItemText, R.id.tvItemTitle
        };

        Cursor cursor = todoDBHelper.getTodoList();
        if (cursor.getCount()>0) {
            tvEmptyListMessage.setVisibility(View.INVISIBLE);
        }else {
            tvEmptyListMessage.setVisibility(View.VISIBLE);
        }
        cursorAdapter = new SimpleCursorAdapter(ToDoListActivity.this, R.layout.todo_item_view, cursor, columns, to, 0);
        gvTodoItemList.setAdapter(cursorAdapter);

    }

    private void showDeleteDialog(final Integer itemId) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getString(R.string.alert_dialog_title));
        alertDialogBuilder.setMessage(getString(R.string.alert_message));
        alertDialogBuilder.setPositiveButton(getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                todoDBHelper.deleteTodoItem(itemId);
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
