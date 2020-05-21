package com.example.qqtest.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qqtest.R;
import com.example.qqtest.activity.MessageActivity;
import com.example.qqtest.model.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> list;
    private Context context;

    public MessageAdapter(Context context,List<Message> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.message, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = list.get(position);
        holder.setMessage(message);
        holder.name.setText(message.getName());
        String str = message.getMessage();
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

        Message message;

        public void setMessage(Message message){
            this.message = message;
        }

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            content = itemView.findViewById(R.id.message);
            linearLayout = itemView.findViewById(R.id.one_message);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MessageActivity.class);
                    Bundle data =new Bundle();
                    data.putString("name",message.getName());
                    data.putString("message",message.getMessage());
                    intent.putExtras(data);
                    context.startActivity(intent);
                }
            });
        }
    }
}
