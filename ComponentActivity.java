package com.example.loginpage.components;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginpage.AboutUs;
import com.example.loginpage.Feedback;
import com.example.loginpage.Profilepage;
import com.example.loginpage.loginactivity.Login2;
import com.example.loginpage.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ComponentActivity extends AppCompatActivity {
    private static int ind;
    TextView name, rate, des;
    ImageView iv;
    RecyclerView rv;
    ArrayList<ComponentModel> arrayList = new ArrayList<ComponentModel>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component);
        rv = findViewById(R.id.compview);

        ind = this.getIntent().getIntExtra("index", 0);

        rv.setLayoutManager(new LinearLayoutManager(this));

        ComponentAsynkTask task = new ComponentAsynkTask(getApplicationContext(), rv);
        task.execute();
    }

    public static int getIndex() {
        return ind;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuitems, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.aboutus:
                Intent intent7 = new Intent(this, AboutUs.class);
                startActivity(intent7);
                break;

            case R.id.profile:
                Intent intent = new Intent(this, Profilepage.class);
                startActivity(intent);
                break;

            case R.id.share:
                Uri uri = Uri.parse("http://www.google.com");
                Intent i2 = new Intent(Intent.ACTION_VIEW, uri);

                if (i2.resolveActivity(getPackageManager()) != null) {
                    startActivity(i2);
                } else {
                    Toast.makeText(this, "Sorry can'handle intent", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.logout:
                alertDialogue();
                break;

            case R.id.mfeedback:
                Intent i1 = new Intent(ComponentActivity.this, Feedback.class);
                startActivity(i1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void alertDialogue() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(ComponentActivity.this);
        builder.setTitle("Logout");
        builder.setMessage("Do you want to Logout?");
        builder.setIcon(R.drawable.ic_exit_to_app_black_24dp);
        builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(ComponentActivity.this, Login2.class);
                startActivity(intent);
                Toast.makeText(ComponentActivity.this, "Logged Out Successfully!!!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("deneid", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void reviewbtn(View view) {
        Toast.makeText(this, "Thanks For Review", Toast.LENGTH_SHORT).show();
    }
}
