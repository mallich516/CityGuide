package com.example.loginpage.loginactivity;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginpage.Forgotpassword;
import com.example.loginpage.Homepage;
import com.example.loginpage.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login2loginFragment extends Fragment implements View.OnClickListener {

    EditText et1, et2;
    Button login;
    TextView fgtpwd;

    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
    private String username;
    private String password;

    ProgressDialog progressDialog;
    private Context context;

    public Login2loginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_loginpage, container, false);

        login = v.findViewById(R.id.loginbtn);
        et1 = v.findViewById(R.id.username);
        et2 = v.findViewById(R.id.password);
        fgtpwd = v.findViewById(R.id.forgotpwd);

        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(getContext(), Homepage.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }
            }
        };

        if (auth.getCurrentUser() != null) {
            Intent i1 = new Intent(getContext(), Homepage.class);
            startActivity(i1);
        }

        progressDialog = new ProgressDialog(getContext());

        login.setOnClickListener(this);
        fgtpwd.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {

        if (view == login) {

            userlogin();

        }

        if (view == fgtpwd) {
            Intent i1 = new Intent(getContext(), Forgotpassword.class);
            startActivity(i1);
        }
    }

    private void userlogin() {

        username = et1.getText().toString().trim();
        password = et2.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getContext(), "Please Enter Email ID", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            et1.setError("Enter Valid Email");
            et1.requestFocus();
            return;
        }

        if (username.length() < 6) {
            et2.setError("Morethan 6 digit");
            et2.requestFocus();
            return;
        }

        progressDialog.setMessage("Signing Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        auth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Intent i = new Intent(getContext(), Homepage.class);
                            startActivity(i);
                        } else {
                            progressDialog.dismiss();
                            final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                            alert.setIcon(R.drawable.ic_exit_to_app_black_24dp);
                            alert.setTitle("Warning");
                            alert.setMessage("Please Enter Valid Details");
                            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    ;
                                }
                            });
                        }
                    }
                });
    }
}
