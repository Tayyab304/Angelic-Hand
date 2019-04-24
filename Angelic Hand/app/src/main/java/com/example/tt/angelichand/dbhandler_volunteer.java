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

public class dbhandler_volunteer {

    Context context;
    Activity activity;
    private listener_for_volunteers mCallBack;
    requested_volunteer rv;


    public dbhandler_volunteer(Context ctx,listener_for_volunteers callback){
        this.context=ctx;
        this.activity=(Activity) ctx;
        this.mCallBack=callback;
        rv=new requested_volunteer(context);

    }

    public  void  receive_volunteer(final String check, final String user, final String request){

        String url="http://192.168.43.104:8080/Angelic_Hand/volunteer.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    //Toast.makeText(activity,"vol"+response,Toast.LENGTH_LONG).show();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response" );
                    JSONObject JO1 =  jsonArray.getJSONObject(0);

                    String code = JO1.getString("code");
                    String message = JO1.getString("message");

                    volunteer v=new volunteer(context);
                    if (code.equals("true")){

                        if (request.equals("req")){

                            rv.names_req=new String[jsonArray.length()];
                            rv.userid_req=new String[jsonArray.length()];
                            rv.user_image_req=new String[jsonArray.length()];
                            rv.user_address_req = new String[jsonArray.length()];
                            rv.user_organization_req = new String[jsonArray.length()];
                            rv.user_org_no_req = new String[jsonArray.length()];

                        }

                        else {
                            v.names = new String[jsonArray.length()];
                            v.userid = new String[jsonArray.length()];
                            v.user_image = new String[jsonArray.length()];
                            v.user_address = new String[jsonArray.length()];
                            v.user_organization = new String[jsonArray.length()];
                            v.status = new boolean[jsonArray.length()];

                        }
                        for (int i=0;i<jsonArray.length();i++) {
//
                            JSONObject JO = jsonArray.getJSONObject(i);

                            String user_id = JO.getString("user_id");
                            String f_name = JO.getString("first_name");
                            String l_name = JO.getString("last_name");
                            String user_img = JO.getString("user_picture");
                            String user_proviance = JO.getString("proviance");
                            String user_city = JO.getString("city");
                            String user_address = JO.getString("address");
                            String user_organization = JO.getString("organization");
                            String user_org_no=JO.getString("volunteer_number");
                            boolean verify = JO.getBoolean("user_status");

                            String addres=user_proviance+", "+user_city+", "+user_address;


                            String name=f_name+" "+l_name;

                            if (request.equals("req")){

                                rv.userid_req[i]=user_id;
                                rv.names_req[i]=name;
                                rv.user_image_req[i]=user_img;
                                rv.user_organization_req[i] = user_organization;
                                rv.user_address_req[i] = addres;
                                rv.user_org_no_req[i] = user_org_no;
                            }
                            else {
                                v.userid[i] = user_id;
                                v.names[i] = name;
                                v.user_address[i] = addres;
                                v.user_organization[i] = user_organization;
                                v.user_image[i] = user_img;
                                v.status[i] = verify;

                            }

                        }
                        if (request.equals("req")){
                            mCallBack.onSuccess(code,rv);

                        }
                        else {
                            mCallBack.onSuccess(code, v);
                        }

                    }

                    else if (code.equals("false")) {
                        mCallBack.onSuccess(code,v);
                        Toast.makeText(activity,message,Toast.LENGTH_LONG).show();

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
        }) {


            protected Map<String,String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("check", check);
                hashMap.put("tt", user);
                hashMap.put("request",request);
                return hashMap;
            }


        };

        Volley.newRequestQueue(activity).add(stringRequest);


    }
}
