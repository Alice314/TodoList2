package com.wusui.todolist;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fg on 2016/2/26.
 */
public class FunctionActivity extends AppCompatActivity {
    private List<Function>funList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);

        Toolbar fun_toolbar = (Toolbar)findViewById(R.id.fun_toolbar);
        setSupportActionBar(fun_toolbar);
        fun_toolbar.setNavigationIcon(R.mipmap.ic_jiantou);
        fun_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initFun();
        FunctionAdapter adapter = new FunctionAdapter(FunctionActivity.this,R.layout.item_function,funList);
        final ListView listView =(ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Function fun = funList.get(position);
                switch (fun.getName()){
                    case "到期日期":
                        Toast.makeText(FunctionActivity.this,"暂不开放此功能",Toast.LENGTH_SHORT).show();
                        break;
                    case "优先级":
                        Dialog dialog = new PriorityDialog(FunctionActivity.this);
                        dialog.setContentView(R.layout.item_dialog);
                        dialog.show();
                        break;
                    case "标签":
                        Toast.makeText(FunctionActivity.this,"暂不开放此功能",Toast.LENGTH_SHORT).show();
                        break;
                    case "次级任务":
                        Toast.makeText(FunctionActivity.this,"暂不开放此功能",Toast.LENGTH_SHORT).show();
                        break;
                    case "评论":
                        Toast.makeText(FunctionActivity.this,"暂不开放此功能",Toast.LENGTH_SHORT).show();
                        break;
                    case "提醒":
                        Toast.makeText(FunctionActivity.this,"暂不开放此功能",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }
        public void initFun(){
            Function date = new Function("到期日期",R.mipmap.ic_rili);
            funList.add(date);
            Function priority = new Function("优先级",R.mipmap.ic_hongqi);
            funList.add(priority);
            Function note = new Function("标签",R.mipmap.ic_biaoqian);
            funList.add(note);
            Function cheif = new Function("次级任务",R.mipmap.ic_santiaoxian1);
            funList.add(cheif);
            Function pinglun = new Function("评论",R.mipmap.ic_pinglun);
            funList.add(pinglun);
            Function tixing = new Function("提醒",R.mipmap.ic_tixing);
            funList.add(tixing);
        }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.example) { //noinspection SimplifiableIfStatement
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_function, menu);
        return true;
    }



}
