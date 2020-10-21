package com.uk.ac.tees.w92214775.fixitexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.uk.ac.tees.w92214775.fixitexpress.helpers.COMM;
import com.uk.ac.tees.w92214775.fixitexpress.helpers.firebase;

public class servicePage extends AppCompatActivity {

    private String SID;
    private Toolbar gToolbar;

    private ImageView gPic;
    private TextView gName,gPrice;
    private Button gBook;

    private DatabaseReference gServiceRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_page);
        SID = getIntent().getStringExtra("SID");

        


        gPic = (ImageView)findViewById(R.id.service_imageView);
        gName = (TextView)findViewById(R.id.service_name);
        gPrice = (TextView)findViewById(R.id.service_price);
        gBook = (Button)findViewById(R.id.service_bookAppointmentBtn);

        gBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toBookingProcess(SID);
            }
        });

        gServiceRef = firebase.gDatabase.getReference().child(COMM.SERVICES).child(SID);
        gServiceRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gName.setText(String.valueOf(snapshot.child(COMM.S_NAME).getValue()));
                gPrice.setText("£ "+String.valueOf(snapshot.child(COMM.S_PRICE).getValue()));
                Glide.with(servicePage.this).load(String.valueOf(snapshot.child(COMM.S_PICURL).getValue())).into(gPic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void toBookingProcess(String sid) {
        Intent toBooking = new Intent(servicePage.this,bookingPage.class);
        toBooking.putExtra("SID",sid);
        toBooking.putExtra("SNAME",gName.getText());
        toBooking.putExtra("SPRICE",gPrice.getText());
        startActivity(toBooking);
    }
}