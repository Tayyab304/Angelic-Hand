package com.example.tt.angelichand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MobileVerificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);
        //getSupportActionBar().setTitle("Mobile Verification");

        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mobileverification_frame,new TypeOfUserFragment()).addToBackStack(null);
        fragmentTransaction.commit();

    }
}
