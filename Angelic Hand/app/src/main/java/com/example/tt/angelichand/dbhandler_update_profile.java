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

import static android.content.Context.MODE_PRIVATE;

public class dbhandler_update_profile {
    Context context;
    Activity activity;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private listener_gernal<String> mCallBack;


    public dbhandler_update_profile(Context ctx,listener_gernal callback){
        context=ctx;
        activity=(Activity)ctx;
        mCallBack=callback;
    }

    public void update(final String user_id,final String first_name, final String last_name,
                       final String address, final String volunteer_no,
                       final String org){

        String url="http://192.168.43.104:8080/Angelic_Hand/updating_profile.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response" );
                    JSONObject JO1 = jsonArray.getJSONObject(0);
                    String code = JO1.getString("code");
                    String message = JO1.getString("message");
                    if (code.equals("true")){

                        save_data_in_phone(first_name,last_name,address,org,volunteer_no);
                        mCallBack.onSuccess(code);
                    }
                    else if (code.equals("false")){

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

            }
        })
        {

            protected Map<String,String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("user_id", user_id);
                hashMap.put("first_name", first_name);
                hashMap.put("last_name", last_name);
                hashMap.put("address", address);
                hashMap.put("volunteer_number", volunteer_no);
                hashMap.put("organization", org);

                return hashMap;
            }

        };
        Volley.newRequestQueue(activity).add(stringRequest);
    }

    private void save_data_in_phone(String first_name,String last_name,
                                    String street_address,String org,String volunteer_no) {

        sharedPreferences =activity.getSharedPreferences("Angelic_Hand_user_data",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString("userfname",first_name);
        editor.putString("userlname",last_name);
        editor.putString("useraddress",street_address);
        editor.putString("userorganization",org);
        editor.putString("uservolunteerno",volunteer_no);

        editor.apply();
    }
}
