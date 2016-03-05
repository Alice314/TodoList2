package com.wusui.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * * Created by fg on 2016/2/26.
 */
public class EditActivity extends AppCompatActivity  {
    private RecyclerView mRecyclerView;
    private List<String>mDatas = new ArrayList<>();
    private DataAdapter mAdapter;
    private EditText editText;
    private TextView textView;
    private EditDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private GregorianCalendar date;
    private String priority;
    private ItemTouchHelper.Callback mCallback;
    public static int fromPosition;
    public static int toPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editText = (EditText)findViewById(R.id.edit_view);
        textView = (TextView)findViewById(R.id.text_view);

        dbHelper = new EditDatabaseHelper(this,"Usb.db",null,1);
        db = dbHelper.getWritableDatabase();

        initToolBar();
        initView();
    }
    private void initView(){
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter = new DataAdapter(EditActivity.this, mDatas));
        ItemTouchHelper.Callback mCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                fromPosition= viewHolder.getAdapterPosition();
                toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition){
                    for (int i = fromPosition;i < toPosition;i++){
                        Collections.swap(mDatas,i,i+1);
                    }
                }else {
                    for (int i =fromPosition;i > toPosition;i--){
                        Collections.swap(mDatas,i,i-1);
                    }
                }
                mAdapter.notifyItemChanged(fromPosition, toPosition);

                return true;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                db.execSQL("DELETE FROM User where content = ?", new Object[]{mDatas.get(position)});
                db.execSQL("DELETE FROM User where date = ?", new Object[]{mDatas.get(position)});
                mDatas.remove(position);
                mAdapter.notifyItemRemoved(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        ImageButton button = (ImageButton)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = dbHelper.getWritableDatabase();
                date = new GregorianCalendar();

                int year = date.get(Calendar.YEAR);
                int month = date.get(Calendar.MONTH);
                int today = date.get(Calendar.DAY_OF_MONTH);

                String now = year + "年" + month + "月" + today;


                db.execSQL("INSERT INTO User(content) VALUES(?)", new Object[]{editText.getText().toString()});
                db.execSQL("INSERT INTO User(date) VALUES(?)", new Object[]{now});

                mDatas.add(now);
                mDatas.add(editText.getText().toString());

                mAdapter.notifyDataSetChanged();

            }
        });
        try {
            Cursor cursor = db.rawQuery("select * from user",null);
            if (cursor.moveToFirst()){
                do {
                    String now = cursor.getString(cursor.getColumnIndex("date"));
                    mDatas.add(now);
                    String content = cursor.getString(cursor.getColumnIndex("content"));
                    mDatas.add(content);

                }
                while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        mAdapter.notifyDataSetChanged();

    }


    private void initToolBar(){
        Toolbar editToolBar = (Toolbar)findViewById(R.id.edit_toolbar);

        setSupportActionBar(editToolBar);
        editToolBar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_jiantou));

        editToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_edit,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();

        if (id == R.id.action_more){
            Intent intent = new Intent(EditActivity.this,FunctionActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(menuItem);
    }

}
