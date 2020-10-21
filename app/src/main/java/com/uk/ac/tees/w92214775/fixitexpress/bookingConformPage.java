package com.uk.ac.tees.w92214775.fixitexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.uk.ac.tees.w92214775.fixitexpress.bookingProcess.appointmentDetails;
import com.uk.ac.tees.w92214775.fixitexpress.bookingProcess.serviceDetails;
import com.uk.ac.tees.w92214775.fixitexpress.helpers.COMM;
import com.uk.ac.tees.w92214775.fixitexpress.helpers.firebase;

import java.util.Date;

public class bookingConformPage extends AppCompatActivity{

    private TextView gDate,gBID,gSNAME,gPRICE,gUEMAIL;

    private String BID;

    private DatabaseReference gBookingRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_conform_page);
        BID = getIntent().getStringExtra("BID");

        gBookingRef = firebase.gDatabase.getReference().child(COMM.USERS).child(firebase.UID);

        gDate = (TextView)findViewById(R.id.bookingConform_date);
        gBID = (TextView)findViewById(R.id.bookingConform_bookingId);
        gSNAME = (TextView)findViewById(R.id.bookingConform_name);
        gPRICE = (TextView)findViewById(R.id.bookingConform_price);
        gUEMAIL = (TextView)findViewById(R.id.bookingConform_userEmail);

        gBookingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gDate.setText(snapshot.child(COMM.BOOOKINGS).child(BID).child(COMM.B_APDATE).getValue()+" - "+snapshot.child(COMM.BOOOKINGS).child(BID).child(COMM.B_APTIME).getValue());
                gBID.setText(BID);
                gSNAME.setText(String.valueOf(snapshot.child(COMM.BOOOKINGS).child(BID).child(COMM.B_SNAME).getValue()));
                gPRICE.setText(String.valueOf(snapshot.child(COMM.BOOOKINGS).child(BID).child(COMM.B_SPRICE).getValue()));
                gUEMAIL.setText(String.valueOf(snapshot.child(COMM.U_EMAIL).getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


}