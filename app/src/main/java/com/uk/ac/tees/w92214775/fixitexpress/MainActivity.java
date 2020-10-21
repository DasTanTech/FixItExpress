package com.uk.ac.tees.w92214775.fixitexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.uk.ac.tees.w92214775.fixitexpress.helpers.COMM;
import com.uk.ac.tees.w92214775.fixitexpress.helpers.firebase;

public class MainActivity extends AppCompatActivity {

    private static final long TIME_DELAY = 2000;
    private Handler gHandler;

    private DatabaseReference gCheckRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gHandler = new Handler();
        firebase.gUser = firebase.gAuth.getCurrentUser();
        if (firebase.gUser == null)
        {
            gHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toClientLogin();
                }
            },TIME_DELAY);
        }
        else
        {
            firebase.UID = firebase.gUser.getUid();
            gHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toHome();
                }
            },TIME_DELAY);

        }
    }



    private void toClientLogin() {
        Intent toClientLogin = new Intent(MainActivity.this,loginPage.class);
        startActivity(toClientLogin);
        finish();
    }

    private void toHome() {
        Intent toHome = new Intent(MainActivity.this,homePage.class);
        startActivity(toHome);
        finish();
    }
}