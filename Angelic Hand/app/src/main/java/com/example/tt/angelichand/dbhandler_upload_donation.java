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

public class dbhandler_upload_donation {

    Context context;
    Activity activity;
    private listener_gernal mCallBack;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public dbhandler_upload_donation(Context ctx,listener_gernal callback){
        this.context=ctx;
        this.activity=(Activity) ctx;
        this.mCallBack=callback;

    }


    public  void upload(final String user_id, final String don_type, final String don_loc, final String don_desc, final String don_item,
                        final String don_qty, final String don_exp, final String don_cloth_for, final String don_cloth_type, final String don_cloth_cond, final String don_amt,
                        final String don_img, final String pic_encode){

        String url="http://192.168.43.104:8080/Angelic_Hand/donation_post.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {

                    Toast.makeText(activity,"mess"+response,Toast.LENGTH_LONG).show();
                    JSONObject jsonObject = new JSONObject(response);
//                    //Toast.makeText(activity,"1.1",Toast.LENGTH_LONG).show();
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response" );
                    JSONObject JO =  jsonArray.getJSONObject(0);
                    String code = JO.getString("code");
                    String message = JO.getString("message");

                    if (code.equals("true")){

                        Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
//
                        mCallBack.onSuccess(code);
//
                    }

                    else if (code.equals("false")){

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
                hashMap.put("donation_type", don_type);
                hashMap.put("donation_location", don_loc);
                hashMap.put("donation_description", don_desc);

                if (don_type.equals("Food") || don_type.equals("Medicine")){

                    hashMap.put("donation_item", don_item);
                    hashMap.put("donation_quantity", don_qty);
                    hashMap.put("donation_expiry", don_exp);

                    if (!don_img.equals("awe")){
                        hashMap.put("donation_image", don_img);
                        hashMap.put("donation_image_data", pic_encode);
                    }

                }



                else if (don_type.equals("Cloth") ){

                    hashMap.put("donation_item", don_item);
                    hashMap.put("donation_quantity", don_qty);
                    hashMap.put("cloth_for", don_cloth_for);
                    hashMap.put("cloth_type", don_cloth_type);
                    hashMap.put("cloth_condition", don_cloth_cond);


                    if (!don_img.equals("awe")){
                        hashMap.put("donation_image", don_img);
                        hashMap.put("donation_image_data", pic_encode);
                    }

                }

                else if (don_type.equals("Charity")){

                    hashMap.put("donation_amount", don_amt);

                    if (!don_img.equals("awe")){
                        hashMap.put("donation_image", don_img);
                        hashMap.put("donation_image_data", pic_encode);
                    }

                }

                else if (don_type.equals("Living Stuff")){

                    hashMap.put("donation_item", don_item);
                    hashMap.put("donation_quantity", don_qty);

                    if (!don_img.equals("awe")){
                        hashMap.put("donation_image", don_img);
                        hashMap.put("donation_image_data", pic_encode);
                    }

                }





                return hashMap;
            }


        };

        Volley.newRequestQueue(activity).add(stringRequest);



    }
        }
