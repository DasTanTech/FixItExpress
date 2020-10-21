package com.uk.ac.tees.w92214775.fixitexpress;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.uk.ac.tees.w92214775.fixitexpress.helpers.COMM;
import com.uk.ac.tees.w92214775.fixitexpress.helpers.firebase;

import java.util.HashMap;

public class profileRegisterPage extends AppCompatActivity {

    //init
    private TextInputLayout gName,gEmail,gPhone;
    private ImageView gProPic;
    private Button gUpdate;
    private String NAME,EMAIL,PHONE,PROPICURL;
    private static final int GALLERY_PICK = 1;
    private Uri IMAGE_URL = null;
    private int check = 0;

    //firebase
    private DatabaseReference gUserRef;
    private HashMap<String,Object> dataMap = new HashMap<>();
    private StorageReference gProPicRef;
    private UploadTask uploadTask;

    //progress dialog
    private ProgressDialog gDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_register_page);

        gUserRef = firebase.gDatabase.getReference().child(COMM.USERS);

        gName = (TextInputLayout) findViewById(R.id.profileRegister_name);
        gEmail = (TextInputLayout)findViewById(R.id.profileRegister_email);
        gPhone = (TextInputLayout)findViewById(R.id.profileRegister_phone);
        gProPic = (ImageView)findViewById(R.id.profileRegister_imageView);
        gUpdate = (Button)findViewById(R.id.profileRegister_updateBtn);

        //progress dialog
        gDialog = new ProgressDialog(this);
        gDialog.setTitle("Please wait");
        gDialog.setMessage("While we are loading....");

        loadClientData();

        gProPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGalleryIntent();
            }
        });

        gUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NAME = gName.getEditText().getText().toString();
                EMAIL = gEmail.getEditText().getText().toString();
                PHONE = gPhone.getEditText().getText().toString();
                if (NAME.isEmpty() || EMAIL.isEmpty() || PHONE.isEmpty())
                {
                    Toast.makeText(profileRegisterPage.this, "Fill all the details.", Toast.LENGTH_SHORT).show();
                }
                else if (IMAGE_URL == null)
                {
                    if (PROPICURL == null)
                        Toast.makeText(profileRegisterPage.this, "Select any Image.", Toast.LENGTH_SHORT).show();
                    else {
                        gDialog.show();
                        updateData(NAME, EMAIL, PHONE, PROPICURL);
                    }
                }
                else
                {
                    storeImage(IMAGE_URL);
                }
            }
        });

    }

    private void openGalleryIntent() {
        Intent GalleryIntent = new Intent();
        GalleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        GalleryIntent.setType("image/*");
        startActivityForResult(GalleryIntent, GALLERY_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK && data != null) {
            IMAGE_URL = data.getData();
            gProPic.setImageURI(IMAGE_URL);
        }
    }

    private void storeImage(Uri image_url) {
        gDialog.show();
        gProPicRef = firebase.gStorage.getReference().child("US_S" + image_url.getLastPathSegment() + ".jpg");
        uploadTask = gProPicRef.putFile(image_url);
        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return gProPicRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    PROPICURL = String.valueOf(task.getResult());
                    updateData(NAME,EMAIL,PHONE,PROPICURL);
                }
            }
        });
    }

    private void updateData(String name, String email, String phone, String PROPICURL) {

        dataMap.put(COMM.U_NAME,name);
        dataMap.put(COMM.U_EMAIL,email);
        dataMap.put(COMM.U_PHONE,phone);
        dataMap.put(COMM.U_PROPICURL,PROPICURL);

        gUserRef.child(firebase.UID).updateChildren(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    gDialog.dismiss();
                    toHome();
                }
                else
                {
                    gDialog.dismiss();
                    Toast.makeText(profileRegisterPage.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void toHome() {
        Intent toHome = new Intent(profileRegisterPage.this,homePage.class);
        startActivity(toHome);
        finish();
    }

    private void loadClientData(){
        gDialog.show();
        gUserRef.child(firebase.UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(COMM.U_PROPICURL))
                {
                    gName.getEditText().setText(String.valueOf(snapshot.child(COMM.U_NAME).getValue()));
                    gEmail.getEditText().setText(String.valueOf(snapshot.child(COMM.U_EMAIL).getValue()));
                    gPhone.getEditText().setText(String.valueOf(snapshot.child(COMM.U_PHONE).getValue()));
                    PROPICURL = String.valueOf(snapshot.child(COMM.U_PROPICURL).getValue());
                    Glide.with(profileRegisterPage.this).load(String.valueOf(snapshot.child(COMM.U_PROPICURL).getValue())).into(gProPic);
                }
                gDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}