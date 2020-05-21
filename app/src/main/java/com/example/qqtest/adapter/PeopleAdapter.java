package com.example.qqtest.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qqtest.R;
import com.example.qqtest.activity.PeopleActivity;
import com.example.qqtest.model.People;

import java.io.Serializable;
import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {

    private List<People> list;
    private Context context;
    public PeopleAdapter(Context context,List<People> list){
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.people, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        People people = list.get(position);
        holder.setPeople(people);
        holder.name.setText(people.getName());
        String str = people.getMessage();
        if(str.length()<25){
            holder.content.setText(str);
        }
        else {
            holder.content.setText(str.substring(0,23)+"...");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView content;
        LinearLayout linearLayout;
        People people;

        public void setPeople(People people){
            this.people = people;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            content = itemView.findViewById(R.id.message);
            linearLayout = itemView.findViewById(R.id.one_people);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PeopleActivity.class);
                    Bundle data =new Bundle();
                    data.putSerializable("people",(Serializable)people);
                    intent.putExtras(data);
                    context.startActivity(intent);
                }
            });
        }
    }
}
