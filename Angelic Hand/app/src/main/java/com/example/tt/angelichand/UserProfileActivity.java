package com.example.tt.angelichand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {



    String name="",img="",add="",org="",type,vol_no;
    boolean sta;
    ImageView status;
    TextView username,useraddress,userorganization,uservol_no;
    CircleImageView userimage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        status=findViewById(R.id.user_status_userprofile);
        username=findViewById(R.id.user_username_userprofile);
        useraddress=findViewById(R.id.user_useraddress_userprofile);
        userorganization=findViewById(R.id.user_userorganization_userprofile);
        uservol_no=findViewById(R.id.user_org_no_userprofile);
        userimage=findViewById(R.id.user_image_profile_user_profile);

        Intent i=getIntent();
        type=i.getStringExtra("type");
        name=i.getStringExtra("user_name");
        img=i.getStringExtra("user_image");
        add=i.getStringExtra("user_address");
        org=i.getStringExtra("user_organization");

        if (type.isEmpty() || type==null || type.equals("volunteer")){
            uservol_no.setVisibility(View.INVISIBLE);
        }
        else if (type.equals("organization")){
            vol_no=i.getStringExtra("user_vol_no");
            uservol_no.setVisibility(View.VISIBLE);

        }


        sta=i.getBooleanExtra("user_status",false);

        //Toast.makeText(UserProfileActivity.this,"user"+vol_no,Toast.LENGTH_LONG).show();

        username.setText(name);
        useraddress.setText(add);
        userorganization.setText(org);
        uservol_no.setText(vol_no);
        if(img!=null){
            String url="http://192.168.43.104:8080/Angelic_Hand/images/";
            url=url+img;
            //Toast.makeText(UserProfileActivity.this,url,Toast.LENGTH_LONG).show();
            Picasso.get().load(url).fit().centerCrop().placeholder(R.drawable.icon_user_profile).into(userimage);
        }


        if (sta==true){
            status.setVisibility(View.VISIBLE);
        }
        else {

            status.setVisibility(View.INVISIBLE);
        }



    }
}
