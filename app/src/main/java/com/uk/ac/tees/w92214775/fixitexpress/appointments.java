package com.uk.ac.tees.w92214775.fixitexpress;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.service.autofill.Dataset;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.uk.ac.tees.w92214775.fixitexpress.helpers.COMM;
import com.uk.ac.tees.w92214775.fixitexpress.helpers.bookingAdapter;
import com.uk.ac.tees.w92214775.fixitexpress.helpers.bookingModel;
import com.uk.ac.tees.w92214775.fixitexpress.helpers.firebase;

import java.util.ArrayList;
import java.util.List;


public class appointments extends Fragment {

    //RCV
    private RecyclerView gRCV;
    private LinearLayoutManager gLLM;
    private bookingAdapter adapter;
    private List<bookingModel> bookingModelList = new ArrayList<>();

    private DatabaseReference gBookingRef;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointments, container, false);

        gRCV = (RecyclerView)view.findViewById(R.id.bookingsFrag_RCV);
        gLLM = new LinearLayoutManager(getActivity());
        gLLM.setOrientation(LinearLayoutManager.VERTICAL);
        gRCV.setLayoutManager(gLLM);
        gRCV.setHasFixedSize(true);
        adapter = new bookingAdapter(bookingModelList,getContext());
        gRCV.setAdapter(adapter);

        gBookingRef= firebase.gDatabase.getReference().child(COMM.USERS).child(firebase.UID).child(COMM.BOOOKINGS);
        gBookingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    bookingModelList.clear();
                    for (DataSnapshot booking : snapshot.getChildren() )
                    {
                        bookingModelList.add(new bookingModel(
                           String.valueOf(booking.getKey()),
                           String.valueOf(booking.child(COMM.B_SNAME).getValue()),
                                booking.child(COMM.B_APDATE).getValue()+" - "+booking.child(COMM.B_APTIME).getValue()
                        ));
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}