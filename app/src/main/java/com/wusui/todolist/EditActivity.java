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
    private static RecyclerView mRecyclerView;
    private List<String>mDatas;
    private DataAdapter mAapter;
    private EditText editText;
    private TextView textView;
    private EditDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private GregorianCalendar date;
    private String priority;
    public ItemTouchHelper.Callback mCallback;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        initData();
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
        mRecyclerView.setAdapter(mAapter = new DataAdapter(EditActivity.this, mDatas));
        ItemTouchHelper.Callback mCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition){
                    for (int i = fromPosition;i < toPosition;i++){
                        Collections.swap(mDatas,i,i+1);
                    }
                }else {
                    for (int i =fromPosition;i > toPosition;i--){
                        Collections.swap(mDatas,i,i-1);
                    }
                }
                mAapter.notifyItemChanged(fromPosition,toPosition);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                mDatas.remove(position);
                mAapter.notifyItemRemoved(position);
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
                db.execSQL("INSERT INTO User(date) VALUES(?)", new Object[]{priority});

                mDatas.add(editText.getText().toString());
                mDatas.add(now);

                mAapter.notifyDataSetChanged();
            }
        });
        try {
            Cursor cursor = db.rawQuery("select * from user",null);
            if (cursor.moveToFirst()){
                do {
                    String content = cursor.getString(cursor.getColumnIndex("content"));
                    mDatas.add(content);
                    String now = cursor.getString(cursor.getColumnIndex("date"));
                    mDatas.add(now);
                }
                while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        mAapter.notifyDataSetChanged();
    }

    public void initToolBar(){
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

    protected void initData(){
        mDatas = new ArrayList<>();
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_edit,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();
        if (id == R.id.action_delete){
            db.execSQL("DELETE FROM User where content = ?",new Object[]{});
        }
        if (id == R.id.action_more){
            Intent intent = new Intent(EditActivity.this,FunctionActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(menuItem);
    }



}
