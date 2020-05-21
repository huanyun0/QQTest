package com.example.qqtest.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_User = "create table User ("
            +"qq_number varchar(11) primary key ,"
            +"qq_pwd varchar(15),"
            +"name varchar(10),"
            +"head_image varchar(100),"
            +"message varchar(50))";

    private Context mContext;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        mContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_User);
        Log.e("MyDatabaseHelper","创建成功");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("drop table if exists User");
        onCreate(db);
    }


}
