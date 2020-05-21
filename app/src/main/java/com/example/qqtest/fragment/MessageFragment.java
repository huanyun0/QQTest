package com.example.qqtest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qqtest.R;
import com.example.qqtest.adapter.MessageAdapter;
import com.example.qqtest.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {

    private RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_fragment, container, false);
        recyclerView = view.findViewById(R.id.message_view);

        Message message1 = new Message();
        message1.setName("张三");
        message1.setMessage("关于阅卷老师的年龄，有不少人告诉我他们城市是研究生批高考作文。请恕我和孤陋寡闻。因为我参与过，了解过的情况，魔都的语文作文阅卷一直都是：普通评卷.");
        Message message2 = new Message();
        message2.setName("李四");
        message2.setMessage("2020年2月17日上午，十三届全国人大常委会第四十七次委员长会议在北京人民大会堂举行，栗战书委员长主持。会议决定，十三届全国人大常委会第十六次会议2月24日在北京举行。委员长会议建议，关于提请审议全国人大常委会关于推迟召开第十三届全国人民代表大会第三次会议的决定草案的议案。");

        List<Message> list = new ArrayList<Message>();
        for(int i=0;i<10;i++){
            list.add(message1);
            list.add(message2);
        }
        Context context = getContext();
        if (context != null) {
            MessageAdapter adapter = new MessageAdapter(context, list);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(adapter);
        }
        return view;
    }
}
