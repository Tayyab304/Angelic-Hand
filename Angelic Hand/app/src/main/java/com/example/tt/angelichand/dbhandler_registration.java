package com.example.tt.angelichand;

import android.app.Activity;
import android.app.AlertDialog;
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

public class dbhandler_registration {
    Context context;
    Activity activity;
    private listener_gernal mCallBack;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public dbhandler_registration(Context context,listener_gernal callback){
        this.context=context;
        activity=(Activity) context;
        this.mCallBack=callback;
    }

    public void register(final String type, final String user_id, final String mobile_number, final String password, final String first_name, final String last_name
            , final String proviance, final String city, final String street_address, final String org, final String volunteer_no){
        String url="";

        if (type.equals("volunteer")){
            url="http://192.168.43.104:8080/Angelic_Hand/volunteer_registration.php";
        }
        else if (type.equals("donor")){


            url="http://192.168.43.104:8080/Angelic_Hand/donor_registration.php";
        }



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



                            save_data_in_phone(type,user_id,first_name,last_name,mobile_number,proviance,city,street_address,org,volunteer_no);


                        //else if (type.equals("donor"))

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
                hashMap.put("first_name", first_name);
                hashMap.put("last_name", last_name);
                hashMap.put("mobile_number", mobile_number);
                hashMap.put("proviance", proviance);
                hashMap.put("city", city);
                hashMap.put("address", street_address);
                hashMap.put("password", password);
                if (type.equals("volunteer") || !org.isEmpty() || !volunteer_no.isEmpty()) {
                    hashMap.put("volunteer_number", volunteer_no);
                    hashMap.put("organization", org);
                }

                return hashMap;
            }


        };

        Volley.newRequestQueue(activity).add(stringRequest);

    }

    private void save_data_in_phone(String type,String user_id, String first_name, String last_name, String mobile_number, String proviance, String city, String street_address, String org, String volunteer_no) {


        sharedPreferences =activity.getSharedPreferences("Angelic_Hand_user_data",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putBoolean("islogin",true);
        editor.putString("type",type);
        editor.putString("userid",user_id);
        editor.putString("userfname",first_name);
        editor.putString("userlname",last_name);
        editor.putString("usermobile",mobile_number);
        editor.putString("userproviance",proviance);
        editor.putString("usercity",city);
        editor.putString("useraddress",street_address);
        if (type.equals("volunteer") || !org.isEmpty() || !volunteer_no.isEmpty()) {
            editor.putString("userorganization", org);
            editor.putString("uservolunteerno", volunteer_no);

        }
        editor.apply();
    }

}
