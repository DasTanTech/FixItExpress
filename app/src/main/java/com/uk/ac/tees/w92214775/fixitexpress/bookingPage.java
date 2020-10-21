package com.uk.ac.tees.w92214775.fixitexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.uk.ac.tees.w92214775.fixitexpress.bookingProcess.appointmentDetails;
import com.uk.ac.tees.w92214775.fixitexpress.bookingProcess.serviceDetails;
import com.uk.ac.tees.w92214775.fixitexpress.helpers.COMM;
import com.uk.ac.tees.w92214775.fixitexpress.helpers.firebase;

import java.util.Date;
import java.util.HashMap;

public class bookingPage extends AppCompatActivity implements serviceDetails.onFragmentDataEventListener,appointmentDetails.onAPFragmentDataEventListener{

    //progress dialog===============================================================================
    private ProgressDialog gDialog;
    private static final int TIME_DELAY = 2000;

    private String SERVICE_DETAILS,APPOINTMENT_DATE,APPOINTMENT_TIME;

    private Toolbar toolbar;

    private FrameLayout gFrameLayout;
    private final FragmentManager fm = getSupportFragmentManager();

    private final Fragment serviceDetails = new serviceDetails();
    private final Fragment appointments = new appointmentDetails();

    private DatabaseReference gBookingRef;
    private String BID,SID,SNAME,SPRICE;
    private HashMap<String,String> bookingData = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_page);
        SID = getIntent().getStringExtra("SID");
        SNAME = getIntent().getStringExtra("SNAME");
        SPRICE = getIntent().getStringExtra("SPRICE");
        gBookingRef = firebase.gDatabase.getReference().child(COMM.USERS).child(firebase.UID).child(COMM.BOOOKINGS);

        toolbar = (Toolbar)findViewById(R.id.bookingProcess_mainToolBar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(SNAME);

        gFrameLayout = (FrameLayout)findViewById(R.id.bookingProcess_mainFrameLayout);
        fm.beginTransaction().replace(R.id.bookingProcess_mainFrameLayout,serviceDetails).commit();
    }


    @Override
    public void onEventData(String serviceDetail) {
        SERVICE_DETAILS = serviceDetail;

        fm.beginTransaction().replace(R.id.bookingProcess_mainFrameLayout,appointments).commit();
    }

    @Override
    public void onEventData(String date, String time) {
        APPOINTMENT_DATE = date;
        APPOINTMENT_TIME = time;

        createAppointment();
    }

    private void createAppointment() {
        BID = gBookingRef.push().getKey();
        bookingData.put(COMM.B_SID,SID);
        bookingData.put(COMM.B_SNAME,SNAME);
        bookingData.put(COMM.B_SPRICE,SPRICE);
        bookingData.put(COMM.B_SERVICE,SERVICE_DETAILS);
        bookingData.put(COMM.B_APDATE,APPOINTMENT_DATE);
        bookingData.put(COMM.B_APTIME,APPOINTMENT_TIME);

        gBookingRef.child(BID).setValue(bookingData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    toBookingConform(BID);
                }
                else
                {
                    Toast.makeText(bookingPage.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void toBookingConform(String bid) {
        Intent toBookingConform = new Intent(bookingPage.this,bookingConformPage.class);
        toBookingConform.putExtra("BID",bid);
        startActivity(toBookingConform);
        finish();
    }
}