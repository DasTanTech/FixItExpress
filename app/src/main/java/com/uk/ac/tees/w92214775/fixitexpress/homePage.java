package com.uk.ac.tees.w92214775.fixitexpress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;


public class homePage extends AppCompatActivity{

    private MeowBottomNavigation gBNV;
    private FrameLayout gFrameLayout;

    private static final int ID_HOME = 1;
    private static final int ID_BOOKINGS = 2;
    private static final int ID_PROFILE = 3;

    private Fragment homeFrag = new home();
    private Fragment bookingsFrag = new appointments();
    private Fragment profileFrag = new settings();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        gBNV = (MeowBottomNavigation)findViewById(R.id.home_BNV);
        gFrameLayout = (FrameLayout)findViewById(R.id.home_frameLayout);

        gBNV.add(new MeowBottomNavigation.Model(ID_HOME,R.drawable.ic_home));
        gBNV.add(new MeowBottomNavigation.Model(ID_BOOKINGS,R.drawable.ic_calendar));
        gBNV.add(new MeowBottomNavigation.Model(ID_PROFILE,R.drawable.ic_user));

        getSupportFragmentManager().beginTransaction().replace(R.id.home_frameLayout,homeFrag).commit();

        gBNV.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });

        gBNV.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment selected_fragment = null;
                switch (item.getId())
                {
                    case ID_HOME:
                        selected_fragment = homeFrag;
                        break;
                    case ID_BOOKINGS:
                        selected_fragment = bookingsFrag;
                        break;
                    case ID_PROFILE:
                        selected_fragment = profileFrag;
                        break;


                }
                getSupportFragmentManager().beginTransaction().replace(R.id.home_frameLayout, Objects.requireNonNull(selected_fragment)).commit();
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        gBNV.show(ID_HOME,true);
    }
}