package com.example.loginpage.ChatFiles;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.loginpage.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChatRecyclerView extends RecyclerView.Adapter<ChatRecyclerView.Chat> {
    private Context context;
    List<ChatModel> chatlist;

    public ChatRecyclerView(Context context, List<ChatModel> chatlist) {
        this.context = context;
        this.chatlist = chatlist;
    }

    @NonNull
    @Override
    public Chat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.chatitem,viewGroup,false);
        return new Chat(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Chat chat, int i) {
        ChatModel model = chatlist.get(i);

       // Date currenttime = Calendar.getInstance().getTime();


        chat.name.setText(model.getName());
        chat.message.setText(model.getMessage());
      //  chat.timeText.setText(currenttime.toString());


    }

    @Override
    public int getItemCount() {
        return chatlist.size();
    }

    public class Chat extends RecyclerView.ViewHolder {
        TextView name,message,timeText;
        public Chat(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.chatname);
            message = itemView.findViewById(R.id.chatmsg);
            timeText = itemView.findViewById(R.id.text_message_time);

        }
    }
}
