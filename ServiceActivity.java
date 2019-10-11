package com.example.loginpage.service;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.loginpage.AboutUs;
import com.example.loginpage.Feedback;
import com.example.loginpage.Profilepage;
import com.example.loginpage.loginactivity.Login2;
import com.example.loginpage.R;

public class ServiceActivity extends AppCompatActivity {

    RecyclerView rv;
    private static Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        rv = findViewById(R.id.servicesrecyclerview);
        rv.setLayoutManager(new GridLayoutManager(this,2));

        b = getIntent().getExtras();
        int cityindex = b.getInt("hotel");
        HotelTask hotelTask = new HotelTask(this,rv);
        hotelTask.execute();

    }
public static int getcityindex()
{
    return b.getInt("hotel");
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
                Intent i2 = new Intent(Intent.ACTION_VIEW,uri);

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
                Intent i1 = new Intent(ServiceActivity.this, Feedback.class);
                startActivity(i1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void alertDialogue() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(ServiceActivity.this);
        builder.setTitle("Logout");
        builder.setMessage("Do you want to Logout?");
        builder.setIcon(R.drawable.ic_exit_to_app_black_24dp);
        builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(ServiceActivity.this, Login2.class);
                startActivity(intent);
                Toast.makeText(ServiceActivity.this, "Logged Out Successfully!!!", Toast.LENGTH_SHORT).show();
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
