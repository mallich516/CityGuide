package com.example.loginpage.loginactivity;


import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.loginpage.Homepage;
import com.example.loginpage.Profilepage;
import com.example.loginpage.R;
import com.example.loginpage.Upload;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login2RegisterFragment extends Fragment implements View.OnClickListener {
    EditText fn, em, ph, pwd;
    Button register;
    private String email;
    private String password;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    DatabaseReference userIDref;
    ProgressDialog progressDialog;
    private String fullname;
    private String phoneno;
    RadioButton rb, rb2;
    private String maler;
    private String femaler;
    RadioGroup radioGroup;
    RadioButton radioButton;
    private String gender;
    CircleImageView uploadpic;
    FloatingActionButton addpic;
    StorageReference storageReference;
    private int PICK_IMAGE_REQUEST = 16;
    private Uri filePath;
    private StorageTask uploadStorageTask;
    private DatabaseReference mDataBaseRef_imgs;
    private StorageReference ref;
    FirebaseStorage storage;


    public Login2RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_register, container, false);


        register = v.findViewById(R.id.register);
        fn = v.findViewById(R.id.fullname);
        em = v.findViewById(R.id.remail);
        ph = v.findViewById(R.id.phno);
        pwd = v.findViewById(R.id.rpwd);
        rb = v.findViewById(R.id.rbtnmale);
        rb2 = v.findViewById(R.id.rbtnfemale);
        radioGroup = v.findViewById(R.id.rbtng);
        uploadpic = v.findViewById(R.id.upload_profile_photo);

        final int selectedgender = radioGroup.getCheckedRadioButtonId();

        radioButton = v.findViewById(selectedgender);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.rbtnmale:
                        gender = rb.getText().toString();
                        break;

                    case R.id.rbtnfemale:
                        gender = rb2.getText().toString();
                        break;

                }
            }
        });
        auth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(getContext());
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");


        register.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {

        if (view == register) {

            getregistered();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                uploadpic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getregistered() {

        email = em.getText().toString().trim();
        password = pwd.getText().toString().trim();
        fullname = fn.getText().toString();
        phoneno = ph.getText().toString();
        maler = rb.getText().toString();
        femaler = rb2.getText().toString();

        if (TextUtils.isEmpty(email)) {
            em.setError("Enter Email");
            em.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            pwd.setError("Enter Password");
            pwd.requestFocus();
        } else if (TextUtils.isEmpty(fullname)) {
            fn.setError("Enter Name");
            fn.requestFocus();
        } else if (TextUtils.isEmpty(phoneno)) {
            ph.setError("Enter Phone No");
            ph.requestFocus();
        } else if (password.length() < 6) {
            pwd.setError("Enter 6 Digit Password");
            pwd.requestFocus();
        } else {
            progressDialog.setMessage("Registering....");
            progressDialog.setCancelable(false);
            progressDialog.show();

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                userIDref = databaseReference.child(auth.getCurrentUser().getUid());
                                userIDref.child("name").setValue(fullname);
                                userIDref.child("email").setValue(email);
                                userIDref.child("phoneno").setValue(phoneno);
                                userIDref.child("password").setValue(password);
                                userIDref.child("gender").setValue(gender);
                                em.setText(null);
                                pwd.setText(null);
                                fn.setText(null);
                                ph.setText(null);
                                Intent i = new Intent(getContext(), Homepage.class);
                                startActivity(i);
                            }
                        }
                    });
        }
    }

    private String getFileExtension(Uri uri) {

        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
}
