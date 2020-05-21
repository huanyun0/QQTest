package com.example.qqtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qqtest.R;
import com.example.qqtest.adapter.MessageAdapter;
import com.example.qqtest.adapter.MessageDetailAdapter;
import com.example.qqtest.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    private TextView other_view;
    private RecyclerView recyclerView;
    private TextView back_view;

    private Message message;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        message = new Message();
        message.setName(bundle.getString("name"));
        message.setMessage(bundle.getString("message"));
        other_view = findViewById(R.id.other_user);
        other_view.setText(message.getName());
        recyclerView = findViewById(R.id.message_detail_view);
        List<Message> list = new ArrayList<Message>();
        for(int i=0;i<20;i++){
            list.add(message);
        }
        MessageDetailAdapter adapter = new MessageDetailAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        back_view = findViewById(R.id.back);
        back_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
