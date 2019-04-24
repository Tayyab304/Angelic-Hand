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

public class dbhandler_picture {

    Context context;
    Activity activity;
    private listener_gernal mCallBack;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public dbhandler_picture(Context ctx,listener_gernal callback){

        this.context=ctx;
        this.activity=(Activity) ctx;
        this.mCallBack=callback;
    }

    public void upload_pic(final String type, final String user_id, final String pic_name, final String pic_encoded){


        String url="http://192.168.43.104:8080/Angelic_Hand/pic_uploading.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response" );
                    JSONObject JO =  jsonArray.getJSONObject(0);
                    String code = JO.getString("code");
                    String message = JO.getString("message");

                    if (code.equals("true")){

                        String pic = JO.getString("piccode");
                        sharedPreferences =activity.getSharedPreferences("Angelic_Hand_user_data",MODE_PRIVATE);
                        editor=sharedPreferences.edit();
                        editor.putString("userpicture",pic);
                        editor.apply();
                        //Toast.makeText(activity,"thek a"+message,Toast.LENGTH_LONG).show();
                        mCallBack.onSuccess(code);
                    }
                    else if (code.equals("false")){

                        mCallBack.onSuccess(code);
                        //Toast.makeText(activity,"ghalt happening"+message,Toast.LENGTH_LONG).show();


                    }

                }
                catch (Exception e){
                    mCallBack.onFailure(e);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(activity,error.toString(),Toast.LENGTH_LONG).show();
                mCallBack.onFailure(error);
            }
        })
        {


            protected Map<String,String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("type", type);
                hashMap.put("user_id", user_id);
                hashMap.put("image_name", pic_name);
                hashMap.put("encoded_string", pic_encoded);


                return hashMap;
            }


        };

        Volley.newRequestQueue(activity).add(stringRequest);

    }
}
