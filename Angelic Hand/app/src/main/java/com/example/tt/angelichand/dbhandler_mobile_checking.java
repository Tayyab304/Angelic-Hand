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

public class dbhandler_mobile_checking {
    Context context;
    Activity activity;
    private listener_gernal mCallBack;

    public dbhandler_mobile_checking(Context ctx,listener_gernal callback){
        this.context=ctx;
        this.activity=(Activity) ctx;
        this.mCallBack=callback;

    }


    public void isnumberExist(final String number){
        String url = "http://192.168.43.104:8080/Angelic_Hand/numberchecking.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response");

                    JSONObject JO = jsonArray.getJSONObject(0);
                    String code = JO.getString("code");
                    String message = JO.getString("message");

                    if (code.equals("true")) {

                        mCallBack.onSuccess(code);


                    }
                    else if (code.equals("false")) {

                        mCallBack.onSuccess(code);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mCallBack.onFailure(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mCallBack.onFailure(error);

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();

                hashMap.put("mobile_number", number);


                return hashMap;
            }
        };
        Volley.newRequestQueue(activity).add(stringRequest);


    }

}
