package com.example.qqtest.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.qqtest.R;
import com.example.qqtest.activity.LoginActivity;
import com.example.qqtest.model.User;
import com.example.qqtest.utils.DBManager;
import com.example.qqtest.utils.TaskRunner;
import com.example.qqtest.utils.UserUtil;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyFragment extends Fragment implements View.OnClickListener  {

    private Context context;

    private ImageView select_message;
    private RelativeLayout relative_name;
    private RelativeLayout relative_image;
    private RelativeLayout relative_message;
    private RelativeLayout relative_pwd;
    private TextView logout;
    private TextView name;
    private TextView message;
    private CircleImageView imageView;

    private View dialog_view;

    private AlertDialog.Builder builder;
    private UserUtil userUtil;
    private DBManager dbManager;

    public MyFragment(Context context){
        this.context = context;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                changeImage((String)msg.obj);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment, container, false);
        select_message = view.findViewById(R.id.select_message);
        relative_name = view.findViewById(R.id.change_name);
        relative_image = view.findViewById(R.id.change_image);
        relative_message = view.findViewById(R.id.change_message);
        relative_pwd = view.findViewById(R.id.change_pwd);
        logout = view.findViewById(R.id.logout);
        select_message.setOnClickListener(this);
        relative_name.setOnClickListener(this);
        relative_image.setOnClickListener(this);
        relative_message.setOnClickListener(this);
        relative_pwd.setOnClickListener(this);
        logout.setOnClickListener(this);
        name = view.findViewById(R.id.name);
        message = view.findViewById(R.id.message);
        imageView = view.findViewById(R.id.avator);
        userUtil = new UserUtil(context);
        name.setText(userUtil.getString(userUtil.KEY_NAME));
        if(userUtil.getString(userUtil.KEY_MESSAGE).length()<23){
            message.setText(userUtil.getString(userUtil.KEY_MESSAGE));
        }
        else {
            message.setText(userUtil.getString(userUtil.KEY_MESSAGE).substring(0,20)+"...");
        }
        if(!userUtil.getString(userUtil.KEY_IMAGE).equals("")){
            loadImage(userUtil.getString(userUtil.KEY_IMAGE));
        }
        else {
            imageView.setImageResource(R.drawable.avatar);
        }
        dbManager = DBManager.get(context);

        return view;
    }

    @Override
    public void onClick(View v) {
        EditText editText1;
        switch (v.getId()){
            case R.id.select_message:
                String message =  userUtil.getString(userUtil.KEY_MESSAGE);
                builder =null;
                builder = new AlertDialog.Builder(context)
                        .setIcon(R.drawable.pen)
                        .setTitle("个性签名")
                        .setMessage(message);
                setButton(builder)
                        .create()
                        .show();
                break;
            case R.id.change_name:
                dialog_view = getLayoutInflater().inflate(R.layout.dialog,null);
                editText1 = dialog_view.findViewById(R.id.dialog_editText1);
                editText1.setVisibility(View.INVISIBLE);
                builder = new AlertDialog.Builder(context)
                        .setIcon(R.drawable.pen)
                        .setTitle("修改昵称")
                        .setView(dialog_view);
                setPositiveButton(builder,dialog_view,userUtil.KEY_NAME,3)
                        .create()
                        .show();
                break;
            case R.id.change_image:
                Intent intent0=new Intent(Intent.ACTION_PICK,null);
                intent0.setType("image/*");
                startActivityForResult(intent0,1);
                break;
            case R.id.change_message:
                dialog_view = getLayoutInflater().inflate(R.layout.dialog,null);
                editText1 = dialog_view.findViewById(R.id.dialog_editText1);
                editText1.setVisibility(View.INVISIBLE);
                builder = new AlertDialog.Builder(context)
                        .setIcon(R.drawable.pen)
                        .setTitle("修改个性签名")
                        .setView(dialog_view);
                setPositiveButton(builder,dialog_view,userUtil.KEY_MESSAGE,5)
                        .create()
                        .show();
                break;
            case R.id.change_pwd:
                dialog_view = getLayoutInflater().inflate(R.layout.dialog,null);
                EditText editText = dialog_view.findViewById(R.id.dialog_editText);
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                editText1= dialog_view.findViewById(R.id.dialog_editText1);
                editText1.setVisibility(View.VISIBLE);
                editText1.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                builder = new AlertDialog.Builder(context)
                        .setIcon(R.drawable.pen)
                        .setTitle("修改密码")
                        .setView(dialog_view);
                setPositiveButton(builder,dialog_view,null,2)
                        .create()
                        .show();
                break;
            case R.id.logout:
                builder =null;
                builder = new AlertDialog.Builder(context)
                        .setIcon(R.drawable.pen)
                        .setTitle("退出应用")
                        .setMessage("是否退出登录");
                Logout(builder)
                        .create()
                        .show();
                break;
            default:
                break;
        }
    }
    private AlertDialog.Builder setButton(AlertDialog.Builder builder){

        return builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
    }
    private AlertDialog.Builder setPositiveButton(AlertDialog.Builder builder,final View now_view, final String user_util_num, final int number){

        return builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText edit = now_view.findViewById(R.id.dialog_editText);
                final String s = edit.getText().toString().trim();
                if(number ==2){
                    EditText edit2 = now_view.findViewById(R.id.dialog_editText1);
                    String ss = edit2.getText().toString().trim();
                    if(!s.equals(ss)){
                        Toast.makeText(context,"修改失败",Toast.LENGTH_SHORT).show();
                        ((ViewGroup) dialog_view.getParent()).removeView(dialog_view);
                        return;
                    }
                }
                if(user_util_num!=null){
                    userUtil.putString(user_util_num,s);
                }
                try {
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            Runnable ui1 = new Runnable() {
                                @Override
                                public void run() {
                                    switch (number){
                                        case 3:
                                            name.setText(s);
                                            break;
                                        case 5:
                                            if(s.length()<23){
                                                message.setText(s);
                                            }
                                            else {
                                                message.setText(s.substring(0,20)+"...");
                                            }
                                            break;
                                        default:
                                            break;
                                    }
                                    Toast.makeText(context,"修改成功",Toast.LENGTH_SHORT).show();
                                    ((ViewGroup) dialog_view.getParent()).removeView(dialog_view);
                                }
                            };
                            Runnable ui2 = new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context,"修改失败",Toast.LENGTH_SHORT).show();
                                    ((ViewGroup) dialog_view.getParent()).removeView(dialog_view);
                                }
                            };
                            if(dbManager.update(userUtil.getString(userUtil.KEY_QQ),s,number)){
                                getActivity().runOnUiThread(ui1);
                            }
                            else {
                                getActivity().runOnUiThread(ui2);
                            }
                        }
                    };
                    TaskRunner.execute(runnable);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).setNegativeButton("取消", new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }
    private AlertDialog.Builder Logout(AlertDialog.Builder builder){

        return builder.setPositiveButton("确定", new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(context,LoginActivity.class));
                getActivity().finish();
            }
        }).setNegativeButton("取消", new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final Intent mdata = data;
        if(requestCode==1){
            if(data!=null){
                Runnable runnable =  new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ContentResolver resolver=getActivity().getContentResolver();
                            Uri uri=mdata.getData();
                            Log.d("Android的Uri：",uri.toString());
                            String[] arr = {MediaStore.Images.Media.DATA};
                            Cursor cursor = resolver.query(uri, arr, null, null, null);
                            Log.d("Cursor:",cursor.toString());
                            String img_path="";
                            if(cursor.moveToFirst()){
                                int img_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                                Log.d("img_index:",String.valueOf(img_index));
                                img_path = cursor.getString(img_index);
                            }
//                        cursor.moveToFirst();
                            Log.d("img_path:",img_path);
                            cursor.close();
                            File file=new File(img_path);
                            Bitmap bitmap= MediaStore.Images.Media.getBitmap(resolver,uri);
                            Log.d("Bitmap:",bitmap.toString());
                            Message msg=new Message();
                            msg.what=1;
                            msg.obj = img_path;
                            handler.sendMessage(msg);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                            Log.d("获取图片:","失败");
                        }
                    }
                };
                TaskRunner.execute(runnable);
            }
        }
    }

    private void changeImage(final String obj) {
        userUtil.putString(userUtil.KEY_IMAGE,obj);
        Log.d("路径:",obj);
        try {
            Runnable runnable =new Runnable() {
                @Override
                public void run() {
                    Runnable ui1 = new Runnable() {
                        @Override
                        public void run() {
                            loadImage(obj);
                            Toast.makeText(context,"修改成功",Toast.LENGTH_SHORT).show();
                        }
                    };
                    Runnable ui2 = new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,"修改失败",Toast.LENGTH_SHORT).show();
                        }
                    };
                    if(dbManager.update(userUtil.getString(userUtil.KEY_QQ),obj,4)){
                        getActivity().runOnUiThread(ui1);
                    }
                    else {
                        getActivity().runOnUiThread(ui2);
                    }
                }
            };
            TaskRunner.execute(runnable);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void loadImage(String path){
        if(imageView!=null && path!=null){
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inJustDecodeBounds=true;
            BitmapFactory.decodeFile(path,options);
            int width=options.outWidth;
            options.inSampleSize=width/200;
            options.inPreferredConfig=Bitmap.Config.RGB_565;
            options.inJustDecodeBounds=false;
            Bitmap bitmap2= BitmapFactory.decodeFile(path,options);
            imageView.setImageBitmap(bitmap2);
            Log.e("MyFragment loadImage","加载成功");
        }
    }
}
