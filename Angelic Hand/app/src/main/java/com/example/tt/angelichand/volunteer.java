package com.example.tt.angelichand;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class volunteer extends BaseAdapter {


    Context context;
    Activity activity;
    public CircleImageView userimg;
    ImageView verfied_volunteer;
    TextView user_name;
    public  String[] names;
    public String[] userid;
    public String[] user_image;
    public String[] user_address;
    public String[] user_organization;
    public boolean[] status;

    public volunteer(Context context){

        this.context=context;
        activity=(Activity)context;

    }

    @Override
    public int getCount() {
        return userid.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        convertView = mInflater.inflate(R.layout.custom_volunteer_list,
                null);

        user_name= convertView.findViewById(R.id.volunteer_list_user_name_textview);
        userimg=convertView.findViewById(R.id.volunteer_list_user_image_img);
        verfied_volunteer= convertView.findViewById(R.id.volunteer_list_verified_volunteer_imageview);

        user_name.setText(names[position]);
        if (status[position]==true){

            verfied_volunteer.setVisibility(View.VISIBLE);
        }
        else {

            verfied_volunteer.setVisibility(View.INVISIBLE);
        }
        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(activity,UserProfileActivity.class);
               // intent.putExtra("userid",userid[position]);
                intent.putExtra("type","volunteer");
                intent.putExtra("user_name",names[position]);
                intent.putExtra("user_image",user_image[position]);
                intent.putExtra("user_address",user_address[position]);
                intent.putExtra("user_organization",user_organization[position]);
                intent.putExtra("user_status",status[position]);
                activity.startActivity(intent);
                //Toast.makeText(activity,userid[position]+user_address[position]+user_organization[position],Toast.LENGTH_LONG).show();
            }
        });

        String url="http://192.168.43.104:8080/Angelic_Hand/images/";
        url=url+user_image[position];
        //Toast.makeText(activity,url,Toast.LENGTH_LONG).show();
        Picasso.get().load(url).fit().centerCrop().placeholder(R.drawable.icon_user_profile).into(userimg);

        return convertView;

    }
}
