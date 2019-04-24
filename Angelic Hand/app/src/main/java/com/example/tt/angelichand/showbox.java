package com.example.tt.angelichand;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

public class showbox {
    Context context;
    Activity activity;
    ProgressDialog progressDialog;

    public showbox(Context ctx){
        this.context=ctx;
        this.activity=(Activity) ctx;
        progressDialog=new ProgressDialog(ctx);

    }

    public void show(String title,String message){

        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();



    }

    public void cancel(){

        progressDialog.dismiss();
    }
}
