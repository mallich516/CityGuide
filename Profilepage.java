package com.example.loginpage;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.loginpage.loginactivity.Login2;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profilepage extends AppCompatActivity {
    FloatingActionButton fab;
    Button lout;
    CircleImageView civ;

    FirebaseAuth auth;
    DatabaseReference databaseReference;
    DatabaseReference userIdRef;
    DatabaseReference uploadsRef;

    FirebaseStorage storage;
    StorageReference storageReference;

    TextView email, usernametv, phone, gender;

    DatabaseReference mDataBaseRef_imgs;
    private int PICK_IMAGE_REQUEST = 16;
    private Uri filePath;
    StorageTask uploadStorageTask;

    List<Upload> muploads;
    private String imageurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepage);

        lout = findViewById(R.id.logout);
        fab = findViewById(R.id.addprofile);
        civ = findViewById(R.id.user_profile_photo);
        email = findViewById(R.id.profile_email);
        usernametv = findViewById(R.id.profile_usernaame);
        phone = findViewById(R.id.profile_phoneno);
        gender = findViewById(R.id.profile_gender);

        muploads = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mDataBaseRef_imgs = FirebaseDatabase.getInstance().getReference().child("uploads");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            uploadsRef = mDataBaseRef_imgs.child(Objects.requireNonNull(auth.getCurrentUser()).getUid());
        }

        uploadsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    imageurl = dataSnapshot.child("mImageUrl").getValue().toString();
                    //    Toast.makeText(Profilepage.this, imageurl, Toast.LENGTH_SHORT).show();
                    Upload image = dataSnapshot.getValue(Upload.class);
                    muploads.add(image);
                    Glide.with(getBaseContext())
                            .load(imageurl)
                            .into(civ);
                } else {
                    civ.setImageResource(R.drawable.logo);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Profilepage.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            userIdRef = databaseReference.child(Objects.requireNonNull(auth.getCurrentUser()).getUid());
        }

        userIdRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    name = Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();
                }
                String phoneno = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    phoneno = Objects.requireNonNull(dataSnapshot.child("phoneno").getValue()).toString();
                }
                String emailid = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    emailid = Objects.requireNonNull(dataSnapshot.child("email").getValue()).toString();
                }
                String pgender = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    pgender = Objects.requireNonNull(dataSnapshot.child("gender").getValue()).toString();
                }

                usernametv.setText(name);
                phone.setText(phoneno);
                email.setText(emailid);
                gender.setText(pgender);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        lout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(Profilepage.this);
                builder.setTitle("Logout");
                builder.setMessage("Do you want to Logout?");
                builder.setIcon(R.drawable.ic_exit_to_app_black_24dp);
                builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        auth.signOut();
                        Intent intent = new Intent(Profilepage.this, Login2.class);
                        startActivity(intent);
                        Toast.makeText(Profilepage.this, "Logged Out Successfully!!!", Toast.LENGTH_SHORT).show();
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
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                civ.setImageBitmap(bitmap);
                uploadimage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private String getFileExtension(Uri uri) {

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadimage() {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            StorageReference ref = storageReference.child("images").child(auth.getCurrentUser().getUid());
            final StorageReference fileReference = ref.child(System.currentTimeMillis() +
                    "." + getFileExtension(filePath));

            uploadStorageTask = fileReference.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    progressDialog.dismiss();
                                    Toast.makeText(Profilepage.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                    String uploadimageurl = uri.toString();
                                    Upload upload = new Upload(uploadimageurl);

                                    String uploadid = auth.getCurrentUser().getUid();
                                    mDataBaseRef_imgs.child(uploadid).setValue(upload);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Profilepage.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setProgress((int) progress);
                        }
                    });
        }
    }
}

