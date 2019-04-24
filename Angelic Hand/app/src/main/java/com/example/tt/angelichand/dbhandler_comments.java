package com.example.tt.angelichand;

import android.app.Activity;
import android.content.Context;
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

public class dbhandler_comments {

    Context context;
    Activity activity;
    private listener_for_comments_receiver mCallBack;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public dbhandler_comments(Context ctx,listener_for_comments_receiver callback) {
        this.context = ctx;
        this.activity = (Activity) ctx;
        this.mCallBack = callback;
    }

    public void receive_comments(final String postid){
        String url="http://192.168.43.104:8080/Angelic_Hand/receive_comment.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response" );
                    JSONObject JO1 = jsonArray.getJSONObject(0);
                    String code = JO1.getString("code");
                    String message = JO1.getString("message");

                    comment_receiver cr=new comment_receiver(context);
                    if(code.equals("true")){

                        cr.user_id=new String[jsonArray.length()];
                        cr.comment_time=new String[jsonArray.length()];
                        cr.comment_data=new String[jsonArray.length()];

                        for (int i=0;i<jsonArray.length();i++) {
//
                            JSONObject JO = jsonArray.getJSONObject(i);

                            String user_id = JO.getString("user_id");
                            String comment_time = JO.getString("comment_time");
                            String comment_data = JO.getString("comment_data");



                            cr.user_id[i]=user_id;
                            cr.comment_time[i]=comment_time;
                            cr.comment_data[i]=comment_data;
                        }
                        mCallBack.onSuccess(code,cr);
                    }
                    else if (code.equals("false")){

                        //Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
                        mCallBack.onSuccess(code,cr);

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
                hashMap.put("post_id", postid);

                return hashMap;
            }


        };

        Volley.newRequestQueue(activity).add(stringRequest);


    }
}
