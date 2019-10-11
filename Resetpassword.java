package com.example.loginpage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.loginpage.loginactivity.Login2;

public class Resetpassword extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

    }

    public void submitnewpwd(View view) {

        Intent i = new Intent(this, Login2.class);
        startActivity(i);
        Toast.makeText(this,"Password Updated Successfully...!!!",Toast.LENGTH_LONG).show();
    }
}