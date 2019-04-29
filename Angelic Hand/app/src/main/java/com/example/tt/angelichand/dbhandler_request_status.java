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

public class dbhandler_request_status {

    Context context;
    Activity activity;



    public dbhandler_request_status(Context ctx) {
        this.context = ctx;
        this.activity = (Activity) ctx;


    }



    public  void request(final String status, final String user_id) {

        String url = "http://192.168.43.104:8080/Angelic_Hand/volunteer_status.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {


                try {

                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response" );
                    JSONObject JO1 =  jsonArray.getJSONObject(0);

                    String code = JO1.getString("code");
                    String message = JO1.getString("message");

                    if (code.equals("true")){

                        Toast.makeText(activity,"vol"+message,Toast.LENGTH_LONG).show();

                    }
                    else if (code.equals("false")){


                        Toast.makeText(activity,"vol"+message,Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e){

                    Toast.makeText(activity,"Error"+e.toString(),Toast.LENGTH_LONG).show();

                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(activity,"Error"+error.toString(),Toast.LENGTH_LONG).show();
            }
        }) {


            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("status", status);
                hashMap.put("userid", user_id);

                return hashMap;
            }
        };

        Volley.newRequestQueue(activity).add(stringRequest);

    }

}
