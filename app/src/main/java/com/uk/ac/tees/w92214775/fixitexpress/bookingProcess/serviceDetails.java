package com.uk.ac.tees.w92214775.fixitexpress.bookingProcess;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.uk.ac.tees.w92214775.fixitexpress.R;

import java.util.HashMap;


public class serviceDetails extends Fragment {

    private RadioGroup radioGroup;
    private Button gNext;

    private ProgressDialog gDialog;
    private static final int TIME_DELAY = 2000;

    onFragmentDataEventListener listener;

    public interface onFragmentDataEventListener{
        void onEventData(String serviceDetail);
    }

    public serviceDetails() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_details, container, false);

        //progress dialog
        gDialog = new ProgressDialog(getActivity());
        gDialog.setTitle("Please wait");
        gDialog.setMessage("While we are loading....");

        radioGroup = (RadioGroup)view.findViewById(R.id.bp2_radioGroup);
        gNext = (Button)view.findViewById(R.id.serviceDetails_mainBtn);

        gNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gDialog.show();
                int id = radioGroup.getCheckedRadioButtonId();
                switch (id)
                {
                    case R.id.RD2_1:
                        listener.onEventData("Not Starting");
                        break;
                    case R.id.RD2_2:
                        listener.onEventData("Any Leakage");
                        break;
                    case R.id.RD2_3:
                        listener.onEventData("Power Supply");
                        break;
                    case R.id.RD2_4:
                        listener.onEventData("Other Issue");
                        break;
                    default:
                        listener.onEventData("Not Starting");
                }

            }
        });



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        gDialog.dismiss();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;

        try {
            listener = (onFragmentDataEventListener) activity;
        }catch (RuntimeException e){
            throw new RuntimeException(activity.toString()+"Must Implement Method");
        }
    }
}