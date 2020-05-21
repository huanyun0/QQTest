package com.example.qqtest.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qqtest.R;
import com.example.qqtest.model.User;
import com.example.qqtest.utils.DBManager;
import com.example.qqtest.utils.TaskRunner;
import com.example.qqtest.utils.UserUtil;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText qq_number;
    private EditText qq_pwd;
    private Button login;
    private Button register;

    private DBManager dbManager;
    private UserUtil userUtil;
    private Context context;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

    }

    private void init() {
        qq_number = findViewById(R.id.qq_number);
        qq_pwd = findViewById(R.id.qq_pwd);
        qq_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        dbManager = DBManager.get(this);
        userUtil = new UserUtil(this);
        context =this;
        intent = new Intent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                login();
                break;
            case R.id.register:
                register();
                break;
            default:
                break;
        }
    }

    private void register() {
        final String PASSWORD = "^[a-z0-9A-Z]+";
        final String number = qq_number.getText().toString().trim();
        final String password =qq_pwd.getText().toString();
        if(password.length()<6){
            Toast.makeText(context,"注册失败：输入的密码过短",Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length()>20){
            Toast.makeText(context,"注册失败：输入的密码过长",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.matches(PASSWORD)){
            Toast.makeText(context,"注册失败：输入的密码中含有非法字符",Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Runnable ui1 = new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,"注册成功",Toast.LENGTH_SHORT).show();
                        }
                    };
                    Runnable ui2 = new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,"注册失败或该账号已存在",Toast.LENGTH_SHORT).show();
                        }
                    };
                    if(isRegister(number,password)){
                        runOnUiThread(ui1);
                    }
                    else {
                        runOnUiThread(ui2);
                    }
                }
            };
            TaskRunner.execute(runnable);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void login() {
        final String number = qq_number.getText().toString().trim();
        final String password =qq_pwd.getText().toString();
        try {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Runnable ui1 = new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,"登录成功",Toast.LENGTH_SHORT).show();
                            intent.setClass(context,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    };
                    Runnable ui2 = new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,"帐号或密码错误",Toast.LENGTH_SHORT).show();
                        }
                    };
                    if(isLogin(number,password)){
                        runOnUiThread(ui1);
                    }
                    else {
                        runOnUiThread(ui2);
                    }
                }
            };
            TaskRunner.execute(runnable);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


    public boolean isLogin(String number,String pwd){
        User user = new User.Builder().build();
        Cursor cursor =dbManager.query(new String[]{"qq_number","name","head_image","message"},new String[]{String.valueOf(number),pwd});
        if(cursor==null)
            return false;
        cursor.moveToFirst();
        try {
            user.setQq_number(number);
            user.setQq_pwd(pwd);
            user.setName(cursor.getString(cursor.getColumnIndex("name")));
            user.setHead_image(cursor.getString(cursor.getColumnIndex("head_image")));
            user.setMessage(cursor.getString(cursor.getColumnIndex("message")));
        }
        catch (Exception e){
            return false;
        }
        if (!userUtil.getString(userUtil.KEY_QQ, "").equals(user.getQq_number())) {
            userUtil.clearAll();
        }
        userUtil.putString(userUtil.KEY_QQ,user.getQq_number());
        userUtil.putString(userUtil.KEY_NAME,user.getName());
        userUtil.putString(userUtil.KEY_IMAGE,user.getHead_image());
        userUtil.putString(userUtil.KEY_MESSAGE,user.getMessage());
        return true;
    }

    public boolean isRegister(String number,String pwd){
        if(isLogin(number,pwd)){
            Log.e("LoginActivity isRegister","该账户已存在");
            return false;
        }
        User user = new User.Builder()
                .setQq_number(number)
                .setQq_pwd(pwd)
                .setName("未命名")
                .setMessage("无")
                .build();
        Log.e("LoginActivity isRegister User","qq:"+user.getQq_number()+";pwd:"+
                user.getQq_pwd()+";"+user.getName()+";\nimage:"+user.getHead_image()+";\nmessage:"+user.getMessage());
        return dbManager.insert(user);
    }

}
