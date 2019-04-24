package com.example.tt.angelichand;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

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

public class dbhandler_registration_organization {

    Context context;
    Activity activity;
    private listener_gernal mCallBack;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public dbhandler_registration_organization(Context context,listener_gernal callback){
        this.context=context;
        activity=(Activity) context;
        this.mCallBack=callback;
    }

    public void  register(final String user_id,final String org_name,final String org_address,final String org_no,
                          final String org_phone,final String org_link,final String org_password){


        String url="http://192.168.43.104:8080/Angelic_Hand/organization_registration.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response" );
//
                    JSONObject JO =  jsonArray.getJSONObject(0);
                    String code = JO.getString("code");
                    String message = JO.getString("message");
                    if (code.equals("true")){

                        mCallBack.onSuccess(code);

                    }
                    else if (code.equals("false")){
                        mCallBack.onSuccess(code);
                    }
                    else if (code.equals("already")){
                        mCallBack.onSuccess(code);
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
                //Toast.makeText(activity,error.toString(),Toast.LENGTH_LONG).show();
            }
        })
        {


            protected Map<String,String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("user_id", user_id);
                hashMap.put("organization_name", org_name);
                hashMap.put("organization_address", org_address);
                hashMap.put("organization_phone", org_phone);
                hashMap.put("organization_password", org_password);
                hashMap.put("organization_link", org_link);
                hashMap.put("organization_no", org_no);

                return hashMap;
            }


        };

        Volley.newRequestQueue(activity).add(stringRequest);


    }

}
