package com.example.qqtest.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qqtest.model.User;

public class DBManager {
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private static DBManager dbManager;

    private final int Sucess = 1;
    private final int Fail = 2;
    private final String selectUser = " qq_number = ? and qq_pwd = ?";
    private final String insertUser = "insert into User values(?,?,?,?,?)";
    private final String[] updata ={""};

    private DBManager(){
    }
    public static DBManager get(Context context){
        if (dbManager == null){
            synchronized (DBManager.class){
                if (dbManager == null){
                    dbManager = new DBManager();
                }
            }
        }
        dbManager.createDB(context);
        return dbManager;
    }

    private void createDB(Context context){
        dbHelper = new MyDatabaseHelper(context,"User.db",null,1);
        db = dbHelper.getReadableDatabase();
    }


    public Cursor query(String[] columns,String[] args){
        return db.query("User",columns, selectUser, args,null,null,null);
    }
    public boolean insert(User user){
        ContentValues cv = new ContentValues();
        cv.put("qq_number",user.getQq_number());
        cv.put("qq_pwd",user.getQq_pwd());
        cv.put("name",user.getName());
        cv.put("head_image",user.getHead_image());
        cv.put("message",user.getMessage());
        long num =db.insert("User",null,cv);
        return num!=-1;
    }
    public boolean update(String id,String content,int number){
        ContentValues cv = new ContentValues();
        switch (number){
            case 1:
                return false;
            case 2:
                cv.put("qq_pwd",content);
                break;
            case 3:
                cv.put("name",content);
                break;
            case 4:
                cv.put("head_image",content);
                break;
            case 5:
                cv.put("message",content);
                break;
            default:
                return false;
        }
        long num = db.update("User",cv,"qq_number=?",new String[]{id});
        return num!=-1;
    }
    public boolean delete(){

        return true;
    }
}
