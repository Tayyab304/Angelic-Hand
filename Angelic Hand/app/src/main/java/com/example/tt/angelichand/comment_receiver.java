package com.example.tt.angelichand;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class comment_receiver extends BaseAdapter {

    Context context;
    Activity activity;
    //public int [] comment_id;
    public String[] user_id;
    //public int[] post_id;
    public String[] comment_time;
    public String[] comment_data;
    TextView textView_comment_userid,textView_comment_time,textView_comment_postdata;

    public comment_receiver(Context ctx) {
        context=ctx;
        activity=(Activity)ctx;

    }
    @Override
    public int getCount() {
        return user_id.length;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        convertView = mInflater.inflate(R.layout.custom_comment_layout,
                null);
        textView_comment_userid= convertView.findViewById(R.id.textView_userid_comment);
        textView_comment_time= convertView.findViewById(R.id.textView_time_comment);
        textView_comment_postdata = convertView.findViewById(R.id.textView_post_comment);


        textView_comment_userid.setText(user_id[position]);
        textView_comment_time.setText(comment_time[position]);
        textView_comment_postdata.setText(comment_data[position]);

        return convertView;
    }
}
