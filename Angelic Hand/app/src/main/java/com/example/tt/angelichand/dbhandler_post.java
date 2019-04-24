package com.example.tt.angelichand;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class dbhandler_post {

    Context context;
    Activity activity;
    private listener_for_post_receiver mCallBack;

    public dbhandler_post(Context ctx,listener_for_post_receiver callback){
        this.context=ctx;
        this.activity=(Activity) ctx;
        this.mCallBack=callback;

    }

    public void receive_post(final String show,final String cata){

        String url="http://192.168.43.104:8080/Angelic_Hand/receivepost.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    // Toast.makeText(activity,response,Toast.LENGTH_LONG).show();

                    JSONArray jsonArray = jsonObject.getJSONArray("server_response" );
                    JSONObject JO1 = jsonArray.getJSONObject(0);
                    String code = JO1.getString("code");
                    String message = JO1.getString("message");

                    post_receiver p=new post_receiver(context);
                    //ListView listView= activity.findViewById(R.id.listview);
                    if (code.equals("true")) {

                        //Toast.makeText(activity,message,Toast.LENGTH_LONG).show();

                        p.post_id=new int[jsonArray.length()];
                        p.names=new String[jsonArray.length()];
                        p.loc=new String[jsonArray.length()];
                        p.time=new String[jsonArray.length()];
                        p.post_data=new String[jsonArray.length()];
                        p.pic=new String[jsonArray.length()];

                        for (int i=0;i<jsonArray.length();i++) {
//
                            JSONObject JO = jsonArray.getJSONObject(i);
                            int post_id = JO.getInt("post_id");
                            String user_id = JO.getString("user_id");
                            String post_loc = JO.getString("post_location");
                            String post_data = JO.getString("post_data");
                            String assigned_to = JO.getString("assigned_to");
                            String post_cata = JO.getString("post_cata");
                            String post_time = JO.getString("post_time");
                            //String picture = JO.getString("picture");



                            p.post_id[i]=post_id;
                            p.names[i]=user_id;
                            p.loc[i]=post_loc;
                            p.time[i]=post_time;
                            p.post_data[i]=post_data;

                        }
                        mCallBack.onSuccess(code,p);
                        //listView.setAdapter(p);
//

                    } else if (code.equals("false")) {
                        mCallBack.onSuccess(code,p);
                        Toast.makeText(activity,message,Toast.LENGTH_LONG).show();

                    }


                } catch (JSONException e) {
                    mCallBack.onFailure(e);
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                mCallBack.onFailure(error);
            }
        }) {


            protected Map<String,String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("check", show);
                hashMap.put("catagory", cata);
                return hashMap;
            }


        };

        Volley.newRequestQueue(activity).add(stringRequest);


    }
}

