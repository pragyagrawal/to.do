package com.binarybricks.mytodolist;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.binarybricks.mytodolist.provider.SQLOpenHelper;
import com.binarybricks.mytodolist.provider.todo.TodoColumns;
import com.binarybricks.mytodolist.utils.TodoCursorAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirection;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ToDoListActivity extends AppCompatActivity {

    @BindView(R.id.lvTodoList)
    ListView lvTodoItemList;

    @BindView(R.id.emptyListMessage)
    TextView tvEmptyListMessage;

    @BindView(R.id.ivAddTask)
    ImageView ivAddTask;

    @BindView(R.id.tvNoResultsMessage)
    TextView tvNoResult;

    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private SQLOpenHelper todoDBHelper;
    private SwipeActionAdapter swipeAdapter;
    private TodoCursorAdapter todoCursorAdapter;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        todoDBHelper = SQLOpenHelper.getInstance(ToDoListActivity.this);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        fetchTodoList();
        handleIntent(getIntent());

        lvTodoItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) lvTodoItemList.getItemAtPosition(position);
                String task = cursor.getString(cursor.getColumnIndexOrThrow(TodoColumns.TASK));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(TodoColumns.DESCRIPTION));
                String dueDate = cursor.getString(cursor.getColumnIndexOrThrow(TodoColumns.DUEDATE));
                String priority = cursor.getString(cursor.getColumnIndexOrThrow(TodoColumns.PRIORITY));
                String hashTag = cursor.getString(cursor.getColumnIndexOrThrow(TodoColumns.HASHTAG));
                Long taskId = cursor.getLong(cursor.getColumnIndexOrThrow(TodoColumns._ID));

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
                mFirebaseAnalytics.logEvent("ADD_TODO",new Bundle());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.activity_todo_item_menu, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search_completed_todo_item).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                refreshTaskList();
                swipeAdapter.notifyDataSetChanged();
                return false;
            }
        });
        searchView.setIconifiedByDefault(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sort_by_completed_task) {
            Intent completedTaskIntent = new Intent(ToDoListActivity.this,CompletedTaskActivity.class);
            startActivity(completedTaskIntent);
        }
        return super.onOptionsItemSelected(item);
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

        Cursor cursor = todoDBHelper.getTodoList();
        if (cursor.getCount() > 0) {
            ivAddTask.setVisibility(View.INVISIBLE);
            tvEmptyListMessage.setVisibility(View.INVISIBLE);
        } else {
            ivAddTask.setVisibility(View.VISIBLE);
            tvEmptyListMessage.setVisibility(View.VISIBLE);
        }
        todoCursorAdapter = new TodoCursorAdapter(ToDoListActivity.this, cursor);
        swipeAdapter = new SwipeActionAdapter(todoCursorAdapter);
        swipeAdapter.setListView(lvTodoItemList);
        lvTodoItemList.setAdapter(swipeAdapter);

        // Set backgrounds for the swipe directions
        swipeAdapter.addBackground(SwipeDirection.DIRECTION_NORMAL_LEFT, R.layout.swipe_left)
                .addBackground(SwipeDirection.DIRECTION_NORMAL_RIGHT, R.layout.swipe_right);

        //Listen to Swipes
        swipeAdapter.setSwipeActionListener(new SwipeActionAdapter.SwipeActionListener() {
            @Override
            public boolean hasActions(int position, SwipeDirection direction) {
                if (direction.isLeft())
                    return true;            // Change this to false to disable left swipes
                if (direction.isRight()) return true;
                return false;
            }

            @Override
            public boolean shouldDismiss(int position, SwipeDirection direction) {
                return true;
            }

            @Override
            public void onSwipe(int[] positionList, SwipeDirection[] directionList) {
                for (int i = 0; i < positionList.length; i++) {
                    SwipeDirection direction = directionList[i];
                    int position = positionList[i];
                    Cursor cursor = (Cursor) lvTodoItemList.getItemAtPosition(position);
                    Long taskId = cursor.getLong(cursor.getColumnIndexOrThrow(TodoColumns._ID));
                    if (direction == SwipeDirection.DIRECTION_NORMAL_LEFT || direction == SwipeDirection.DIRECTION_FAR_LEFT) {
                        todoDBHelper.deleteTodoItem(taskId);
                        mFirebaseAnalytics.logEvent("DELETE_TODO",new Bundle());
                    } else if (direction == SwipeDirection.DIRECTION_NORMAL_RIGHT || direction == SwipeDirection.DIRECTION_FAR_RIGHT) {
                        todoDBHelper.updateStatus(taskId);
                        mFirebaseAnalytics.logEvent("COMPLETE_TODO",new Bundle());
                    }
                    refreshTaskList();
                    swipeAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void refreshTaskList() {
        Cursor cursor = todoDBHelper.getTodoList();
        todoCursorAdapter.getCursor().close();
        todoCursorAdapter.swapCursor(cursor);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            fetchSearchResult(query);
        }
    }

    private void fetchSearchResult(String searchText) {
        Cursor cursor = todoDBHelper.getSearchResult(searchText);
        if (cursor.getCount() > 0) {
            tvNoResult.setVisibility(View.INVISIBLE);
            ivAddTask.setVisibility(View.INVISIBLE);
        } else {
            ivAddTask.setVisibility(View.VISIBLE);
            tvNoResult.setVisibility(View.VISIBLE);
        }
        todoCursorAdapter.getCursor().close();
        todoCursorAdapter.swapCursor(cursor);
        swipeAdapter.notifyDataSetChanged();
    }
//    private void showDeleteDialog(final Integer taskId) {
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//        alertDialogBuilder.setTitle(getString(R.string.alert_dialog_title));
//        alertDialogBuilder.setMessage(getString(R.string.alert_message));
//        alertDialogBuilder.setPositiveButton(getString(R.string.alert_positive), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                todoDBHelper.deleteTodoItem(taskId);
//                fetchTodoList();
//            }
//        });
//        alertDialogBuilder.setNegativeButton(getString(R.string.alert_negative), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        alertDialogBuilder.show();
//    }
}
