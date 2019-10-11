package com.example.loginpage.Top5cities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.loginpage.AboutUs;
import com.example.loginpage.Feedback;
import com.example.loginpage.Profilepage;
import com.example.loginpage.loginactivity.Login2;
import com.example.loginpage.R;

import java.lang.reflect.Array;

public class Topcitys extends AppCompatActivity {

    private static Bundle b;
    private static int i;
    RecyclerView recyclerView;
    TextView textView;
    ImageView imageView;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topcitys);

        recyclerView = findViewById(R.id.topcitiesrecyclerview);
        textView = findViewById(R.id.topcitiestitle);
        imageView = findViewById(R.id.topcitiesimageview);
        cardView = findViewById(R.id.topcitiescardview);

      //  if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
      //      Toolbar toolbar = new Toolbar(this);
      //      toolbar.setTitle();
      //  }


        b = getIntent().getExtras();
        i = b.getInt("key");

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        Top5Task top5Task = new Top5Task(this, recyclerView);
        top5Task.execute();
    }

    public static int getdistindex() {
        i = b.getInt("key");
        return i;
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

            case R.id.share:
                Uri uri = Uri.parse("http://www.google.com");
                Intent i2 = new Intent(Intent.ACTION_VIEW, uri);

                if (i2.resolveActivity(getPackageManager()) != null) {
                    startActivity(i2);
                } else {
                    Toast.makeText(this, "Sorry can'handle intent", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.profile:
                Intent intent = new Intent(this, Profilepage.class);
                startActivity(intent);
                break;

            case R.id.logout:
                alertDialogue();
                break;

            case R.id.mfeedback:
                Intent i1 = new Intent(Topcitys.this, Feedback.class);
                startActivity(i1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void alertDialogue() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(Topcitys.this);
        builder.setTitle("Logout");
        builder.setMessage("Do you want to Logout?");
        builder.setIcon(R.drawable.ic_exit_to_app_black_24dp);
        builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Topcitys.this, Login2.class);
                startActivity(intent);
                Toast.makeText(Topcitys.this, "Logged Out Successfully!!!", Toast.LENGTH_SHORT).show();
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
}