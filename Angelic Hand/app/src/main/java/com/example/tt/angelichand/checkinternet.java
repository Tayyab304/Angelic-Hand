package com.example.tt.angelichand;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class checkinternet {

    boolean value;
    Context context;
    Activity activity;
    public checkinternet(Context ctx){
        this.context=ctx;
        this.activity=(Activity) ctx;

    }

    public boolean checknet(){

        ConnectivityManager conMgr =  (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo!=null){
            value=true;
        }
        else {

            value=false;
        }
        return value;
    }
}
