package com.example.tt.angelichand;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class post_receiver extends BaseAdapter {

    Context context;
    Activity activity;
    public  String[] names;
    public  String[] loc;
    public int[] post_id;
    public String[] time;
    public String[] pic;
    public String[] post_data;
    TextView textView_name,textView_time,textView_post,textView_loc;
    //Button button_like,button_comment,button_message;
    ImageView img_profile,img_comment,img_message,img_menu;

    public post_receiver(Context context){

        this.context=context;
        activity=(Activity)context;

    }

    @Override
    public int getCount() {
        return names.length;
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

        convertView = mInflater.inflate(R.layout.custom_post_layout,
                null);

        textView_name= convertView.findViewById(R.id.textView_username);
        textView_time= convertView.findViewById(R.id.textView_time);
        textView_post = convertView.findViewById(R.id.textView_post);
        textView_loc = convertView.findViewById(R.id.textView_postlocation);

        img_profile = convertView.findViewById(R.id.imageview_userpic_post);
        img_comment = convertView.findViewById(R.id.imageview_comment);
        img_message = convertView.findViewById(R.id.imageview_message);
        img_menu = convertView.findViewById(R.id.imageview_menu);

//        button_like= convertView.findViewById(R.id.button_like);
//        button_comment= convertView.findViewById(R.id.button_comments);
//        button_message= convertView.findViewById(R.id.button_message);


        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity,"avha",Toast.LENGTH_LONG).show();
//                PopupMenu popupMenu=new PopupMenu(activity,img_menu);
//                popupMenu.getMenuInflater().inflate(R.menu.dots_menu,popupMenu.getMenu());
//                popupMenu.show();

                PopupMenu menu = new PopupMenu(activity, v);
                menu.getMenu().add(1,1,1,"Report");

                menu.show();


            }
        });


        img_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=Integer.toString(post_id[position]);
                Intent i=new Intent(activity,CommentActivity.class);
                i.putExtra(CommentActivity.post,id);
                activity.startActivity(i);
            }
        });



        textView_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(activity,UserProfileActivity.class);
                // intent.putExtra("userid",userid[position]);
                intent.putExtra("user_name",names[position]);
                activity.startActivity(intent);
            }
        });




        textView_name.setText(names[position]);
        textView_loc.setText(loc[position]);
        textView_time.setText(time[position]);
        textView_post.setText(post_data[position]);
        return convertView;


    }
}
