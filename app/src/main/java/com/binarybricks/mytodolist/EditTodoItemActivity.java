package com.binarybricks.mytodolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import butterknife.ButterKnife;

public class EditTodoItemActivity extends AppCompatActivity {

    private EditText etEditTodoText;
    private EditText etEditTodoTitle;
    private Button btnSaveTodoItem;
    private TodoDBHelper todoDBHelper;
    private Integer editItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo_item);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String editItemValue = getIntent().getStringExtra("edit_item_value");
        String editItemTitle = getIntent().getStringExtra("edit_item_title");
        editItemId = getIntent().getIntExtra("edit_item_id", 0);
//
//        etEditTodoText = (EditText) findViewById(R.id.etEditTodoItem);
//        etEditTodoTitle = (EditText) findViewById(R.id.etEditTodoItemTitle);
//        btnSaveTodoItem = (Button) findViewById(R.id.btnSave);
//
//        etEditTodoText.setText(editItemValue);
//        etEditTodoTitle.setText(editItemTitle);
//
//        todoDBHelper = new TodoDBHelper(EditTodoItemActivity.this);
//
//        btnSaveTodoItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String todoItemTitle = etEditTodoTitle.getText().toString();
//                String todoItemText = etEditTodoText.getText().toString();
//                if (!TextUtils.isEmpty(todoItemText)) {
//                    if (editItemId > 0) {
//                        todoDBHelper.updateTodoList(editItemId,todoItemText,todoItemTitle);
//                    } else {
//                        todoDBHelper.insertTodoItem(todoItemText, todoItemTitle);
//                    }
//                    setResult(RESULT_OK);
//                    finish();
//                } else {
//                    Toast.makeText(EditTodoItemActivity.this, R.string.error_message_empty_text, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (editItemId > 0) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.acitivity_edit_todo_item_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_todo_item:
                todoDBHelper.deleteTodoItem(editItemId);
                setResult(RESULT_OK);
                finish();
                return true;
            case R.id.share_todo_item:

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
