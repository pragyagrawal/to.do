package com.binarybricks.mytodolist;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.binarybricks.mytodolist.provider.SQLOpenHelper;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditTodoItemActivity extends AppCompatActivity {

    public static final String MMM_DD_YYYY = "MMM dd,yyyy";
    @BindView(R.id.etTask)
    EditText etEditTask;
    @BindView(R.id.etDescription)
    EditText etEditDescription;
    @BindView(R.id.tvSetDueDate)
    TextView tvEditDueDate;
    @BindView(R.id.tvSetPriority)
    TextView tvEditPriority;
    @BindView(R.id.etSetHashTag)
    EditText etEditHashTag;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private SQLOpenHelper todoDBHelper;
    private Long editTaskId;
    private ShareActionProvider mShareActionProvider;

    private Calendar c = Calendar.getInstance();
    private int startYear = c.get(Calendar.YEAR);
    private int startMonth = c.get(Calendar.MONTH);
    private int startDay = c.get(Calendar.DAY_OF_MONTH);

    private FirebaseAnalytics mFireBaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo_item);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFireBaseAnalytics = FirebaseAnalytics.getInstance(EditTodoItemActivity.this);

        editTaskId = getIntent().getLongExtra("task_id", 0);
        String editTask = getIntent().getStringExtra("edit_task");
        String editDescription = getIntent().getStringExtra("edit_description");
        String editDueDate = getIntent().getStringExtra("edit_due_date");
        String editPriority = getIntent().getStringExtra("edit_priority");
        String editHashTag = getIntent().getStringExtra("edit_hash_tag");

        etEditTask.setText(editTask);
        etEditDescription.setText(editDescription);
        tvEditDueDate.setText(editDueDate);
        tvEditPriority.setText(editPriority);
        etEditHashTag.setText(editHashTag);

        todoDBHelper =SQLOpenHelper.getInstance(EditTodoItemActivity.this);
    }

    @OnClick(R.id.fabSave)
    void saveTask(View v){
        String todoTask = etEditTask.getText().toString();
        String todoDescription = etEditDescription.getText().toString();
        String todoDueDate = tvEditDueDate.getText().toString();
        String todoPriority = tvEditPriority.getText().toString();
        String todoHashTag = etEditHashTag.getText().toString();

        if (!TextUtils.isEmpty(todoTask)) {
            if (editTaskId > 0) {
                todoDBHelper.updateTodoList(editTaskId, todoTask, todoDescription, todoDueDate, todoPriority, todoHashTag);
            } else {
                todoDBHelper.insertTodoItem(todoTask, todoDescription, todoDueDate, todoPriority, todoHashTag);
            }
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(EditTodoItemActivity.this, R.string.error_message_empty_text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (editTaskId > 0) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.acitivity_edit_todo_item_menu, menu);

            MenuItem itemShare = menu.findItem(R.id.share_todo_item);
            mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(itemShare);
            if (mShareActionProvider!=null){
                mShareActionProvider.setShareIntent(createShareTaskIntent());
            }
        }
        return true;
    }

    private Intent createShareTaskIntent() {
        Intent shareIntent=new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,"Task: "+etEditTask.getText()+"\n Description: "+etEditDescription.getText());
        return shareIntent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_todo_item:
                todoDBHelper.deleteTodoItem(editTaskId);
                setResult(RESULT_OK);
                mFireBaseAnalytics.logEvent("DELETE_TODO",new Bundle());
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.llDueDate)
    void setDueDate(View v)
    {
        DatePickerDialog dialog = new DatePickerDialog(EditTodoItemActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
             //you will get date here
                GregorianCalendar calendar = new GregorianCalendar(year,monthOfYear,dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat(MMM_DD_YYYY);
                sdf.setCalendar(calendar);
                String formatedDate = sdf.format(calendar.getTime());
                tvEditDueDate.setText(formatedDate);
            }
        }, startYear, startMonth, startDay);

        dialog.show();
    }

    @OnClick(R.id.llPriority)
    void setPriority(View v){
        final CharSequence[] priority = {"High","Medium","Low"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.priority_list_title));
        builder.setItems(priority,new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                tvEditPriority.setText(priority[item]);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
