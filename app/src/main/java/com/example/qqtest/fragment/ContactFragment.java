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
import com.example.qqtest.adapter.PeopleAdapter;
import com.example.qqtest.model.Message;
import com.example.qqtest.model.People;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_fragment, container, false);
        recyclerView = view.findViewById(R.id.contact_view);
        People people1= new People();
        people1.setName("张三");
        people1.setQq_number("1242495203");
        people1.setMessage("生活就像海洋，只有意志坚定的人才能到达彼岸。");
        People people2= new People();
        people2.setName("李四");
        people2.setQq_number("7415801536");
        people2.setMessage("天道酬勤，大道至简。");
        List<People> list = new ArrayList<People>();
        for(int i=0;i<10;i++){
            list.add(people1);
            list.add(people2);
        }
        Context context = getContext();
        if (context != null) {
            PeopleAdapter adapter = new PeopleAdapter(context,list);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
            recyclerView.setAdapter(adapter);
        }
        return view;
    }
}
