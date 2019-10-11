package com.example.loginpage.ChatFiles;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginpage.ChatFiles.ChatModel;
import com.example.loginpage.ChatFiles.ChatRecyclerView;
import com.example.loginpage.R;
import com.example.loginpage.Upload;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class Chatfragment extends Fragment {

    RecyclerView rv;
    EditText msg;
    FloatingActionButton send;
    DatabaseReference databaseReference;
    DatabaseReference userIdref;
    FirebaseAuth auth;

    List<ChatModel> chatModels;

    public Chatfragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chatfragment, container, false);

        rv = v.findViewById(R.id.chatrecyclerview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        chatModels = new ArrayList<>();

        msg = v.findViewById(R.id.sendmsg);
        send = v.findViewById(R.id.sendbtn);

        auth = FirebaseAuth.getInstance();
        userIdref = FirebaseDatabase.getInstance().getReference("users").child(auth.getCurrentUser().getUid());
        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    ChatModel model = data.getValue(ChatModel.class);
                    chatModels.add(model);
                }

                ChatRecyclerView myAdaptar = new ChatRecyclerView(getContext(), chatModels);
                myAdaptar.notifyDataSetChanged();

                rv.scrollToPosition(chatModels.size() - 1);
                rv.setAdapter(myAdaptar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String message = msg.getText().toString();

                if (message.isEmpty()) {
                    Toast.makeText(getContext(), "Enter Message", Toast.LENGTH_SHORT).show();
                } else {

                    userIdref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String name = dataSnapshot.child("name").getValue().toString();
                            ChatModel model = new ChatModel(name, message);
                            String modelid = databaseReference.push().getKey();
                            databaseReference.child(modelid).setValue(model);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    msg.setText(null);
                }
            }
        });

        return v;
    }

}
