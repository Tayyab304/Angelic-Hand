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

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class requested_volunteer extends BaseAdapter {

    Context context;
    Activity activity;
    public CircleImageView userimg_req;
    ImageView verfied_volunteer;
    TextView user_name_req;
    Button accept,delete;
    public  String[] names_req;
    public String[] userid_req;
    public String[] user_image_req;
    public String[] user_org_no_req;
    public String[] user_address_req;
    public String[] user_organization_req;
    public boolean[] status;

    public requested_volunteer(Context context){

        this.context=context;
        activity=(Activity)context;

    }


    @Override
    public int getCount() {
        return userid_req.length;
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

        convertView = mInflater.inflate(R.layout.custom_request_list,
                null);


        user_name_req= convertView.findViewById(R.id.volunteer_request_list_user_name_textview);
        userimg_req=convertView.findViewById(R.id.volunteer_request_list_user_image_img);
        accept=convertView.findViewById(R.id.volunteer_request_list_accept_button);
        delete=convertView.findViewById(R.id.volunteer_request_list_delete_button);

        user_name_req.setText(names_req[position]);

        user_name_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(activity,UserProfileActivity.class);
                // intent.putExtra("userid",userid[position]);
                intent.putExtra("type","organization");
                intent.putExtra("user_name",names_req[position]);
                intent.putExtra("user_image",user_image_req[position]);
                intent.putExtra("user_address",user_address_req[position]);
                intent.putExtra("user_organization",user_organization_req[position]);
                intent.putExtra("user_vol_no",user_org_no_req[position]);
//                intent.putExtra("user_status",status[position]);
                activity.startActivity(intent);
                //Toast.makeText(activity,userid[position]+user_address[position]+user_organization[position],Toast.LENGTH_LONG).show();
            }
        });


        String url="http://192.168.43.104:8080/Angelic_Hand/images/";
        url=url+user_image_req[position];
        //Toast.makeText(activity,url,Toast.LENGTH_LONG).show();
        Picasso.get().load(url).fit().centerCrop().placeholder(R.drawable.icon_user_profile).into(userimg_req);
        return convertView;
    }
}
