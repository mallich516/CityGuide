package com.example.loginpage;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.loginpage.Home.Homefragment;
import com.example.loginpage.ChatFiles.Chatfragment;
import com.example.loginpage.loginactivity.Login2;
import com.google.firebase.auth.FirebaseAuth;

public class Homepage extends AppCompatActivity {

    private static final int nid = 16;
    private static int otp = 0;
    NotificationManager notify;
    FirebaseAuth auth;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        bottomNavigationView = findViewById(R.id.bottomnvtbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(listener);
        auth = FirebaseAuth.getInstance();

        Mynavigation mynavigation = new Mynavigation(getSupportFragmentManager());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Homefragment()).commit();

        notify = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        isOnline();
    }


    BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch (menuItem.getItemId()) {
                case R.id.nav_chat:
                    selectedFragment = new Chatfragment();
                    break;

                case R.id.nav_home:
                    selectedFragment = new Homefragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menuitems, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.aboutus:
                Intent intent = new Intent(this, AboutUs.class);
                startActivity(intent);
                break;

            case R.id.share:
                Toast.makeText(this, "Share Clicked", Toast.LENGTH_SHORT).show();

                Uri uri = Uri.parse("https://drive.google.com/open?id=1-okrysggXpna1iItNxpJtHIYYO0BrfNv");
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
                Intent i1 = new Intent(Homepage.this, Feedback.class);
                startActivity(i1);
                break;

            case R.id.profile:
                Intent intent7 = new Intent(this, Profilepage.class);
                startActivity(intent7);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    public void isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null & datac != null)
                && (wifi.isConnected() | datac.isConnected())) {
            Toast.makeText(this, "Internet Connected", Toast.LENGTH_SHORT).show();
        } else {
            //no connection
            Toast.makeText(Homepage.this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }


    public void alertDialogue() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(Homepage.this);
        builder.setTitle("Logout");
        builder.setMessage("Do you want to Logout?");
        builder.setIcon(R.drawable.ic_exit_to_app_black_24dp);
        builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                auth.signOut();
                Intent intent = new Intent(Homepage.this, Login2.class);
                startActivity(intent);
                Toast.makeText(Homepage.this, "Logged Out Successfully!!!", Toast.LENGTH_SHORT).show();
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

    public class Mynavigation extends FragmentPagerAdapter {

        public Mynavigation(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }
}
