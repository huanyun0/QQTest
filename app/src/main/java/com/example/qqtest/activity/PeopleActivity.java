package com.example.qqtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qqtest.R;
import com.example.qqtest.model.People;

public class PeopleActivity extends AppCompatActivity {
    TextView name;
    TextView qq_num;
    TextView message;
    TextView back;

    private final String head_qq = "QQ： ";
    private final String head_message ="个性签名:\n        ";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_activity);
        name = findViewById(R.id.name);
        qq_num = findViewById(R.id.other_qq);
        message = findViewById(R.id.other_message);
        back = findViewById(R.id.back);
        Intent intent = getIntent();
        Bundle bundle =intent.getExtras();
        People people = (People) bundle.getSerializable("people");
        name.setText(people.getName());
        qq_num.setText(head_qq + people.getQq_number());
        message.setText(head_message + people.getMessage());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
