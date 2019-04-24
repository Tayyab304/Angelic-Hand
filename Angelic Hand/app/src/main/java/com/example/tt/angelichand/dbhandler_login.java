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

public class dbhandler_login {
    Context context;
    Activity activity;
    private listener_gernal mCallBack;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public dbhandler_login(Context ctx,listener_gernal callback){
        this.context=ctx;
        this.activity=(Activity) ctx;
        this.mCallBack=callback;

    }

    public void checklogin(final String userid,final String password) {

        String url="http://192.168.43.104:8080/Angelic_Hand/islogin.php";;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response" );
                    JSONObject JO =  jsonArray.getJSONObject(0);

                    String code = JO.getString("code");
                    String message = JO.getString("message");

                    if (code.equals("login_true")) {

                        String type = JO.getString("type");


                        if (type.equals("volunteer") || type.equals("donor")){
                            String user_id = JO.getString("user_id");
                            String first_name = JO.getString("first_name");
                            String last_name = JO.getString("last_name");
                            String mobile_number = JO.getString("mobile_number");
                            String proviance = JO.getString("proviance");
                            String city = JO.getString("city");
                            String street_address = JO.getString("address");
                            String org="",volunteer_no="";
                            if (type.equals("volunteer")){
                                 org = JO.getString("organization");
                                 volunteer_no = JO.getString("volunteer_number");
                            }

                            String user_pic = JO.getString("user_picture");
                            save_data_in_phone(user_id,type,first_name,last_name,mobile_number,proviance,city,street_address,org,volunteer_no,user_pic);
                        }
                        else if (type.equals("organization")){
                            String user_id = JO.getString("user_id");
                            String org_name = JO.getString("org_name");
                            String org_no = JO.getString("org_no");
                            String org_add = JO.getString("org_add");
                            String org_num = JO.getString("org_num");
                            String org_link = JO.getString("org_link");
                            String org_pic = JO.getString("org_picture");

                            save_data_for_org(user_id,type,org_name,org_no,org_add,org_num,org_link,org_pic);
                        }


                        mCallBack.onSuccess("true");
                    }
                    else if (code.equals("login_false")){
                        mCallBack.onSuccess("false");
                    }

                }catch (Exception e){


                    mCallBack.onFailure(e);
                    Toast.makeText(activity,e.toString(),Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                mCallBack.onFailure(error);
                Toast.makeText(activity,error.toString(),Toast.LENGTH_LONG).show();
            }
        })
        {


            protected Map<String,String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("user_id", userid);
                hashMap.put("password", password);

                return hashMap;
            }


        };

        Volley.newRequestQueue(activity).add(stringRequest);
    }

    private void save_data_for_org(String user_id,String type, String org_name, String org_no, String org_add, String org_num,
                                   String org_link, String org_pic) {
        sharedPreferences =activity.getSharedPreferences("Angelic_Hand_user_data",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putBoolean("islogin",true);
        editor.putString("type",type);
        editor.putString("userid",user_id);
        editor.putString("organization_name",org_name);
        editor.putString("organization_no",org_no);
        editor.putString("organization_address",org_add);
        editor.putString("organization_phone",org_num);
        editor.putString("organization_link",org_link);
        editor.putString("organization_pic",org_pic);

        editor.apply();

    }

    private void save_data_in_phone(String user_id,String type,String first_name,String last_name,String mobile,String proviance,String city,
                                    String street_address,String org,String volunteer_no,String user_pic) {

        sharedPreferences =activity.getSharedPreferences("Angelic_Hand_user_data",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putBoolean("islogin",true);
        editor.putString("type",type);
        editor.putString("userid",user_id);
        editor.putString("userfname",first_name);
        editor.putString("userlname",last_name);
        editor.putString("usermobile",mobile);
        editor.putString("userproviance",proviance);
        editor.putString("usercity",city);
        editor.putString("useraddress",street_address);
        if (type.equals("volunteer")){
            editor.putString("userorganization",org);
            editor.putString("uservolunteerno",volunteer_no);
        }

        editor.putString("userpicture",user_pic);

        editor.apply();
    }

}
