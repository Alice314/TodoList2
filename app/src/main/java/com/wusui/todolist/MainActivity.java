package com.wusui.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * 你现在是要干嘛啊……把EditActivity/里面的数据传到Main
 * Created by fg on 2016/2/26.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private List<String>mDatas = new ArrayList<>();//而且之前这里没有实例化，是null没有，、、、WHAT！
    private DataAdapter mAdapter;
    private EditDatabaseHelper dbHelper;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initToolBar();
        initView();
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        dbHelper = new EditDatabaseHelper(this,"Usb.db",null,1);
        db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("select * from user",null);
            if (cursor.moveToFirst()){
                do {
                    String content = cursor.getString(cursor.getColumnIndex("content"));
                    mDatas.add(content);
                    String now = cursor.getString(cursor.getColumnIndex("date"));
                    Log.d("MainActivity.class", "感谢上帝，这里没有bug!!!!!!!!");
                    mDatas.add(now);

                }
                while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void initToolBar(){
        mToolbar = (Toolbar)findViewById(R.id.my_toolbar);

        mToolbar.setTitle("今天");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        NavigationView LeftNavigationView = (NavigationView)findViewById(R.id.nav_view_left);
        LeftNavigationView.setNavigationItemSelectedListener(this);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.action_notify) {
                    NavigationView RightView = (NavigationView) findViewById(R.id.nav_view_right);
                }
                return true;
            }
        });
    }
    private void initView(){
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view_main);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new DataAdapter(MainActivity.this, mDatas));//但你在这个时候就把data给了adapter
        // 你妄想在这里找一遍数据
        mAdapter.notifyDataSetChanged();

    }
    @Override
    public void onBackPressed(){
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        getMenuInflater().inflate(R.menu.menu_layout_main,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_notify){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.nav_shoujianxiang:
                LetterFragment letterFragment = new LetterFragment();
                mToolbar.setTitle("收件箱");
                FragmentManager letterFragmentManager = getSupportFragmentManager();
                FragmentTransaction letterTransaction = letterFragmentManager.beginTransaction();
                letterTransaction.replace(R.id.id_content, letterFragment);
                letterTransaction.commit();

              break;
            case R.id.nav_jin:
                TodayFragment todayFragment = new TodayFragment();
                mToolbar.setTitle("今天");
                FragmentManager todayFragmentManager = getSupportFragmentManager();
                FragmentTransaction todayTransaction = todayFragmentManager.beginTransaction();
                todayTransaction.replace(R.id.id_content,todayFragment);
                todayTransaction.commit();
                break;
            case R.id.nav_seven:
                SevenDaysFragment sevenDaysFragment = new SevenDaysFragment();
                mToolbar.setTitle("今后7天");
                FragmentManager sevenDaysFragmentManger = getSupportFragmentManager();
                FragmentTransaction sevenDaysFragmentTransaction = sevenDaysFragmentManger.beginTransaction();
                sevenDaysFragmentTransaction.replace(R.id.id_content,sevenDaysFragment);
                sevenDaysFragmentTransaction.commit();
                break;
            default:
                break;
        }
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
