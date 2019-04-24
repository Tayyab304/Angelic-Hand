package com.example.tt.angelichand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;

public class RegistrationActivity extends AppCompatActivity {

    public static String FLAG_REGISTRATION,FLAG_NUM;
    Toolbar toolbar;
    String number,type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Intent intent=getIntent();
        type=intent.getStringExtra(FLAG_REGISTRATION);

        if (type.equals("organization")){

            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.registration_frame,new OrganizationRegistrationFragment())
                    .addToBackStack(null);
            fragmentTransaction.commit();
        }

        else if (type.equals("volunteer")){
            number=intent.getStringExtra("number");
            Bundle bundle=new Bundle();
            bundle.putString(VolunteerIdFragment.FLAG_ID,type);
            bundle.putString("number",number);
            VolunteerIdFragment vol=new VolunteerIdFragment();
            vol.setArguments(bundle);

            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.registration_frame,vol)
                    .addToBackStack(null);
            fragmentTransaction.commit();

        }

        else if (type.equals("donor")){
            number=intent.getStringExtra("number");
            Bundle bundle=new Bundle();
            bundle.putString(VolunteerIdFragment.FLAG_ID,type);
            bundle.putString("number",number);
            VolunteerIdFragment vol=new VolunteerIdFragment();
            vol.setArguments(bundle);

            android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.registration_frame,vol)
                    .addToBackStack(null);
            fragmentTransaction.commit();

        }

    }
}
