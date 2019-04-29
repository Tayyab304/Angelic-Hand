package com.example.tt.angelichand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ForgetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.forgetpass_frame,new ForgetPassword_take_id_Fragment()).addToBackStack(null);
        fragmentTransaction.commit();
    }
}
