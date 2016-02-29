package com.wusui.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by fg on 2016/2/26.
 */
public class EditDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_USER = "create table user (" +
            "id integer primary key autoincrement," +
            "content text," +
            "date text," +
            "priority text," +
            "deadline text )";

    private Context mContext;

    public EditDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);

        Toast.makeText(mContext,"添加成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists User");
        onCreate(db);
    }
}
