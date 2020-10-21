package com.uk.ac.tees.w92214775.fixitexpress;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.uk.ac.tees.w92214775.fixitexpress.helpers.COMM;
import com.uk.ac.tees.w92214775.fixitexpress.helpers.firebase;

import de.hdodenhof.circleimageview.CircleImageView;


public class settings extends Fragment {

    private TextInputLayout gName,gEmail,gPhone;
    private CircleImageView gPropic;
    private Button gEditProfile,gLogout;

    private DatabaseReference gUserRef;

    //progress dialog
    private ProgressDialog gDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        gName = (TextInputLayout)view.findViewById(R.id.profileFrag_name);
        gEmail = (TextInputLayout)view.findViewById(R.id.profileFrag_email);
        gPhone = (TextInputLayout)view.findViewById(R.id.profileFrag_phone);
        gPropic = (CircleImageView)view.findViewById(R.id.profileFrag_imageView);
        gEditProfile = (Button) view.findViewById(R.id.profileFrag_editProfileBtn);
        gLogout = (Button) view.findViewById(R.id.profileFrag_logoutBtn);

        //progress dialog
        gDialog = new ProgressDialog(getActivity());
        gDialog.setTitle("Please wait");
        gDialog.setMessage("While we are loading....");
        gDialog.show();

        gUserRef = firebase.gDatabase.getReference().child(COMM.USERS).child(firebase.UID);
        gUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gName.getEditText().setText(String.valueOf(snapshot.child(COMM.U_NAME).getValue()));
                gEmail.getEditText().setText(String.valueOf(snapshot.child(COMM.U_EMAIL).getValue()));
                gPhone.getEditText().setText(String.valueOf(snapshot.child(COMM.U_PHONE).getValue()));
                Glide.with(getContext()).load(String.valueOf(snapshot.child(COMM.U_PROPICURL).getValue())).into(gPropic);
                gDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        gEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toEditProfile();
            }
        });

        gLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebase.logoutUser(getActivity());
            }
        });

        return view;
    }

    private void toEditProfile() {
        Intent toEditProfile = new Intent(getActivity(),profileRegisterPage.class);
        startActivity(toEditProfile);

    }
}