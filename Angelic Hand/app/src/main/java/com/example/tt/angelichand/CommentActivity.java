package com.example.tt.angelichand;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CommentActivity extends AppCompatActivity {

    public static String post=null;
    ListView listView;
    String user_id,postid,pt;
    EditText et_comment_data;
    TextView no_comment_tv;
    ImageView send_comment_data;
    SwipeRefreshLayout swipeRefreshLayout_co;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Intent i=getIntent();
        postid=i.getStringExtra(post);
        pt=postid;
        receive_data_from_phone();

        et_comment_data=findViewById(R.id.editText_comment_data);
        send_comment_data=findViewById(R.id.send_comment);
        listView=findViewById(R.id.listview_comments);
        no_comment_tv=findViewById(R.id.no_comments_textview);
        swipeRefreshLayout_co=findViewById(R.id.swipeToRefresh_comment);

        no_comment_tv.setVisibility(View.INVISIBLE);
        send_comment_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendcomment();
            }
        });

        displaycomments();
        swipeRefreshLayout_co.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                displaycomments();
                swipeRefreshLayout_co.setRefreshing(false);
            }
        });


    }

    private void displaycomments() {

        dbhandler_comments db=new dbhandler_comments(CommentActivity.this, new listener_for_comments_receiver() {
            @Override
            public void onSuccess(String code, comment_receiver object) {

                if (code.equals("true")){
                    no_comment_tv.setVisibility(View.INVISIBLE);
                    listView.setAdapter(object);

                }

                else if (code.equals("false")){
                    no_comment_tv.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Exception e) {

                Toast.makeText(CommentActivity.this,"Server Error"+e,Toast.LENGTH_LONG).show();

            }
        });
        db.receive_comments(postid);
    }

    private void sendcomment() {
        String data=et_comment_data.getText().toString();
        checkinternet chi=new checkinternet(CommentActivity.this);
        if (data.isEmpty()){
            et_comment_data.setError("Please Enter Comment");
            et_comment_data.requestFocus();
            return;

        }

        if (!(chi.checknet())){

            Toast.makeText(CommentActivity.this,"Check Your Internet Connenction",Toast.LENGTH_LONG).show();
            return;
        }

        else {

            final showbox sb=new showbox(CommentActivity.this);
            sb.show("Please Wait...","Connecting TO Server...");
            dbhandler_comment_upload db=new dbhandler_comment_upload(CommentActivity.this, new listener_gernal() {
                @Override
                public void onSuccess(Object object) {

                    if (object.equals("true")){
                        displaycomments();
                        listView.setVisibility(View.VISIBLE);
                        no_comment_tv.setVisibility(View.INVISIBLE);
                        et_comment_data.setText("");
                        Toast.makeText(CommentActivity.this,"Successfully Updated",Toast.LENGTH_LONG).show();
                        sb.cancel();
                    }
                    else if (object.equals("false")){
                        Toast.makeText(CommentActivity.this,"server Problem Occured",Toast.LENGTH_LONG).show();
                        sb.cancel();
                    }

                }

                @Override
                public void onFailure(Exception e) {

                    Toast.makeText(CommentActivity.this,"Server Error"+e,Toast.LENGTH_LONG).show();
                    sb.cancel();
                }
            });
            db.upload_comment(user_id,postid,data);
        }
    }

    private void receive_data_from_phone() {
        sharedPreferences = getSharedPreferences("Angelic_Hand_user_data",MODE_PRIVATE);
        user_id = sharedPreferences.getString("userid","tayy");
    }
}
