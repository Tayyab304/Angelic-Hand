package com.example.tt.angelichand;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public static Activity log;
    EditText loginactivity_userid_et,loginactivity_password_et;
    Button loginactivity_signin_bt,loginactivity_signup_bt;

    SharedPreferences sharedPreferences;
    //TextView loginactivity_signup_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        log=this;
        loginactivity_userid_et=findViewById(R.id.loginactivity_userid_edittext);
        loginactivity_password_et=findViewById(R.id.loginactivity_password_edittext);
        loginactivity_signin_bt=findViewById(R.id.loginactivity_signin_button);
        loginactivity_signup_bt=findViewById(R.id.loginactivity_signup_button);



        loginactivity_signin_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        loginactivity_signup_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

    }

    private void signup() {

        Intent i=new Intent(LoginActivity.this,MobileVerificationActivity.class);
        startActivity(i);

//        Intent i=new Intent(LoginActivity.this,MenuActivity.class);
//        startActivity(i);

    }

    private void login() {

        String userid,password;
        // get selected radio button from radioGroup


        userid=loginactivity_userid_et.getText().toString();
        password=loginactivity_password_et.getText().toString();

        checkinternet chi=new checkinternet(LoginActivity.this);


        if (userid.isEmpty()){

            loginactivity_userid_et.setError("Enter UserID");
            loginactivity_userid_et.requestFocus();
            return;
        }

        if (password.isEmpty()){
            loginactivity_password_et.setError("Enter Password");
            loginactivity_password_et.requestFocus();
            return;

        }

        if (!(chi.checknet())){

            Toast.makeText(LoginActivity.this,"Check Your Internet Connenction",Toast.LENGTH_LONG).show();
            return;
        }
        else {


            final showbox sb=new showbox(LoginActivity.this);
            sb.show("Please Wait...","Connecting to Server...");
            dbhandler_login db=new dbhandler_login(LoginActivity.this, new listener_gernal() {
                @Override
                public void onSuccess(Object object) {
                    if (object.equals("true")){

                        //Toast.makeText(LoginActivity.this,"Login Sucessfully",Toast.LENGTH_LONG).show();
                        Intent i=new Intent(LoginActivity.this,MenuActivity.class);
                        startActivity(i);
                        finish();

                    }
                    if (object.equals("false")){

                        loginactivity_userid_et.setText("");
                        loginactivity_password_et.setText("");
                        loginactivity_userid_et.setError("Incorrect UserId");
                        loginactivity_password_et.setError("Incorrect Password");
                        Toast.makeText(LoginActivity.this,"Incorrect UserId OR Password",Toast.LENGTH_LONG).show();

                    }

                    sb.cancel();
                }

                @Override
                public void onFailure(Exception e) {

                    Toast.makeText(LoginActivity.this,"Server Problem"+e,Toast.LENGTH_LONG).show();
                    sb.cancel();
                }
            });
            db.checklogin(userid,password);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        islogin();
    }

    private void islogin() {

        String type;
        sharedPreferences = getSharedPreferences("Angelic_Hand_user_data",MODE_PRIVATE);
        boolean value = sharedPreferences.getBoolean("islogin",false);
        type=sharedPreferences.getString("type","awe");
        //value=false;
        if(value==true){
            Intent intent=new Intent(LoginActivity.this,MenuActivity.class);
            intent.putExtra(MenuActivity.FLAG_MENU,type);
            finish();
            startActivity(intent);
        }
    }
}
