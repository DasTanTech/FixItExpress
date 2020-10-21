package com.uk.ac.tees.w92214775.fixitexpress;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.uk.ac.tees.w92214775.fixitexpress.helpers.COMM;
import com.uk.ac.tees.w92214775.fixitexpress.helpers.firebase;
import com.uk.ac.tees.w92214775.fixitexpress.helpers.homeAdapter;
import com.uk.ac.tees.w92214775.fixitexpress.helpers.homeModel;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;


public class home extends Fragment {

    //section1
    private DiscreteScrollView gDSV;
    private homeAdapter adapter;
    private InfiniteScrollAdapter gDSVadapter;
    private List<homeModel> homeModelList = new ArrayList<>();

    private CardView S_1,S_2,S_3,S_4,S_5,S_6,S_7,S_8;


    private DatabaseReference gServicesRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        S_1 = (CardView)view.findViewById(R.id.s_1);
        S_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toService("S_1");
            }
        });

        S_2 = (CardView)view.findViewById(R.id.s_2);
        S_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toService("S_2");
            }
        });

        S_3 = (CardView)view.findViewById(R.id.s_3);
        S_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toService("S_3");
            }
        });

        S_4 = (CardView)view.findViewById(R.id.s_4);
        S_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toService("S_4");
            }
        });

        S_5 = (CardView)view.findViewById(R.id.s_5);
        S_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toService("S_5");
            }
        });

        S_6 = (CardView)view.findViewById(R.id.s_6);
        S_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toService("S_6");
            }
        });

        S_7 = (CardView)view.findViewById(R.id.s_7);
        S_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toService("S_7");
            }
        });

        S_8 = (CardView)view.findViewById(R.id.s_8);
        S_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toService("S_8");
            }
        });

        gDSV = (DiscreteScrollView)view.findViewById(R.id.homeFrag_discreteScrollView);
        adapter = new homeAdapter(homeModelList,getContext());
        gDSVadapter = InfiniteScrollAdapter.wrap(adapter);
        gDSV.setOrientation(DSVOrientation.HORIZONTAL);
        gDSV.setAdapter(gDSVadapter);
        gDSV.setItemTransitionTimeMillis(170);
        gDSV.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());



        gServicesRef = firebase.gDatabase.getReference().child(COMM.HOME);
        gServicesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot service : snapshot.getChildren())
                {
                    homeModelList.add(new homeModel(
                            String.valueOf(service.child(COMM.S_ID).getValue()),
                            String.valueOf(service.child(COMM.S_PICURL).getValue())
                    ));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    private void toService(String service) {
        Intent toService = new Intent(getActivity(),servicePage.class);
        toService.putExtra("SID",service);
        getActivity().startActivity(toService);
    }
}