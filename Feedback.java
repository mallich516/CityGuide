package com.example.loginpage;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Feedback extends AppCompatActivity {
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    DatabaseReference userIdref;
    DatabaseReference getUserIdref;
    FirebaseAuth auth;
    EditText fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        fb = findViewById(R.id.feedback);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Feedback");
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("users");
        auth = FirebaseAuth.getInstance();
    }

    public void submitfeedback(View view) {

        userIdref = databaseReference.child(auth.getCurrentUser().getUid());
        getUserIdref = databaseReference1.child(auth.getCurrentUser().getUid());
        getUserIdref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                userIdref.child("Name").setValue(name);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userIdref.child("Feedback").setValue(fb.getText().toString());
        Toast.makeText(this,"Feedback Submitted ThankU",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,Homepage.class);
        startActivity(intent);
    }
}
