package com.example.tt.angelichand;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class dbhandler_post_upload {
    Context context;
    Activity activity;
    private listener_gernal mCallBack;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public dbhandler_post_upload(Context ctx,listener_gernal callback){
        this.context=ctx;
        this.activity=(Activity) ctx;
        this.mCallBack=callback;

    }

    public  void upload(final String user_id, final String post_cat, final String post_loc,final String post_data){

        String url="http://192.168.43.104:8080/Angelic_Hand/newpost.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
//                    //Toast.makeText(activity,"1.1",Toast.LENGTH_LONG).show();
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response" );
                    JSONObject JO =  jsonArray.getJSONObject(0);
                    String code = JO.getString("code");
                    String message = JO.getString("message");


                    if (code.equals("true")){

//                        Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
//                        Intent intent=new Intent(activity,MenuActivity.class);
//                        activity.startActivity(intent);
//                        activity.finish();
//                      activity.onBackPressed();
                        mCallBack.onSuccess(code);
//
                    }

                    else if (code.equals("false")){
                        //showDialog("Posted Failed",message,code);
                        mCallBack.onSuccess(code);
                        //Toast.makeText(activity,message,Toast.LENGTH_LONG).show();

                    }
                }
                catch (Exception e){
                    mCallBack.onFailure(e);
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                mCallBack.onFailure(error);
            }
        })
        {


            protected Map<String,String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("user_id", user_id);
                hashMap.put("post_category", post_cat);
                hashMap.put("post_location", post_loc);
                hashMap.put("post_data", post_data);

                return hashMap;
            }


        };

        Volley.newRequestQueue(activity).add(stringRequest);

    }
}
