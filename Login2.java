package com.example.loginpage.loginactivity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.loginpage.Homepage;
import com.example.loginpage.R;
import com.google.firebase.auth.FirebaseAuth;

public class Login2 extends AppCompatActivity {

    TabLayout tl;
    ViewPager vp;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        tl = findViewById(R.id.login2tablayout);
        vp = findViewById(R.id.login2viewpage);

        PageAdaptar pageAdaptar = new PageAdaptar(getSupportFragmentManager());
        vp.setAdapter(pageAdaptar);
        tl.setupWithViewPager(vp);

        auth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (auth.getCurrentUser() != null) {
            Intent i = new Intent(this, Homepage.class);
            startActivity(i);
        }
    }

    public class PageAdaptar extends FragmentPagerAdapter {

        public PageAdaptar(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            switch (i) {
                case 0:
                    return new Login2loginFragment();

                case 1:
                    return new Login2RegisterFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Login";

                case 1:
                    return "Register";
            }
            return super.getPageTitle(position);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }
}

