package com.wusui.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by fg on 2016/2/26.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected DrawerLayout mDrawerLayout;
    protected ActionBarDrawerToggle mDrawerToggle;
    public static Toolbar mToolbar;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar();

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                startActivity(intent);
            }
        });
    }
    public void initToolBar(){
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
                FragmentManager letterfragmentManager = getSupportFragmentManager();
                FragmentTransaction lettertransaction = letterfragmentManager.beginTransaction();
                lettertransaction.replace(R.id.id_content, letterFragment);
                lettertransaction.commit();

              break;
            case R.id.nav_jin:
                TodayFragment todayFragment = new TodayFragment();
                mToolbar.setTitle("今天");
                FragmentManager todayfragmentManager = getSupportFragmentManager();
                FragmentTransaction todaytransaction = todayfragmentManager.beginTransaction();
                todaytransaction.replace(R.id.id_content,todayFragment);
                todaytransaction.commit();
                break;
            case R.id.nav_seven:
                SevenDaysFragment sevenDaysFragment = new SevenDaysFragment();
                mToolbar.setTitle("今后7天");
                FragmentManager sevendaysfragmentManger = getSupportFragmentManager();
                FragmentTransaction sevendaysfragmentTransaction = sevendaysfragmentManger.beginTransaction();
                sevendaysfragmentTransaction.replace(R.id.id_content,sevenDaysFragment);
                sevendaysfragmentTransaction.commit();
                break;
            default:
                break;
        }
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
