package com.uk.ac.tees.w92214775.fixitexpress.bookingProcess;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.uk.ac.tees.w92214775.fixitexpress.R;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;


public class appointmentDetails extends Fragment {



    private String DATE,TIME;

    private CardView cdt1,cdt2,cdt3,cdt4;
    private TextView t1,t2,t3,t4,date;
    private Button gNext;

    private ProgressDialog gDialog;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    onAPFragmentDataEventListener listener;

    public interface onAPFragmentDataEventListener{
        void onEventData(String date,String time);
    }

    public appointmentDetails() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment_details, container, false);

        //progress dialog
        gDialog = new ProgressDialog(getActivity());
        gDialog.setTitle("Please wait");
        gDialog.setMessage("While we are loading....");

        date = (TextView)view.findViewById(R.id.bp6_date);
        cdt1 = (CardView) view.findViewById(R.id.bp6_timeMRG1);
        cdt2 = (CardView) view.findViewById(R.id.bp6_timeAFT1);
        cdt3 = (CardView) view.findViewById(R.id.bp6_timeAFT2);
        cdt4 = (CardView) view.findViewById(R.id.bp6_timeAFT3);
        t1 = (TextView)view.findViewById(R.id.bp6_time1);
        t2 = (TextView)view.findViewById(R.id.bp6_time2);
        t3 = (TextView)view.findViewById(R.id.bp6_time3);
        t4 = (TextView)view.findViewById(R.id.bp6_time4);
        gNext = (Button)view.findViewById(R.id.appointmentDetails_mainBtn);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TIME = null;
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year,month,day
                );
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                date.setText(dayOfMonth+"/"+month+"/"+year);
                unSelectAllTime();
            }
        };

        cdt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unSelectAllTime();
                selectedTime(cdt1,t1);
                DATE = date.getText().toString();
                TIME = t1.getText().toString();
            }
        });
        cdt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unSelectAllTime();
                selectedTime(cdt2,t2);
                DATE = date.getText().toString();
                TIME = t2.getText().toString();
            }
        });
        cdt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unSelectAllTime();
                selectedTime(cdt3,t3);
                DATE = date.getText().toString();
                TIME = t3.getText().toString();
            }
        });
        cdt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unSelectAllTime();
                selectedTime(cdt4,t4);
                DATE = date.getText().toString();
                TIME = t4.getText().toString();
            }
        });

        gNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TIME == null || DATE == null)
                    Toast.makeText(getContext(), "Select any date and time.", Toast.LENGTH_SHORT).show();
                else {
                    gDialog.show();
                    listener.onEventData(DATE, TIME);
                }
            }
        });


        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        gDialog.dismiss();
    }

    private void unSelectAllTime(){
        cdt1.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
        cdt2.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
        cdt3.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
        cdt4.setCardBackgroundColor(getResources().getColor(R.color.colorWhite));
        t1.setTextColor(getResources().getColor(R.color.colorBlack));
        t2.setTextColor(getResources().getColor(R.color.colorBlack));
        t3.setTextColor(getResources().getColor(R.color.colorBlack));
        t4.setTextColor(getResources().getColor(R.color.colorBlack));
    }

    private void selectedTime(CardView cd, TextView timeSlot){
        cd.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
        timeSlot.setTextColor(getResources().getColor(R.color.colorWhite));
        TIME = timeSlot.getText().toString();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;

        try {
            listener = (onAPFragmentDataEventListener) activity;
        }catch (RuntimeException e){
            throw new RuntimeException(activity.toString()+"Must Implement Method");
        }
    }
}