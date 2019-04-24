package com.example.tt.angelichand;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewPostFragment extends Fragment {


    Toolbar toolbar;
    Button post_btn;
    TextView user_name_tv;
    String post_type_array[] = {"Select Post Type", "Food", "Cloth", "Charity"};
    ArrayAdapter<String> arrayAdapter;
    Spinner post_type_spr;
    ImageView location;
    EditText post_location_et, post_data_et;
    CircleImageView user_image;
    SharedPreferences sharedPreferences;
    String user_id, user_pic, user_name, post_type, post_loc, post_data;

    LocationManager locationmanager;
    LocationListener locationListener;

    public NewPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_post, container, false);


        toolbar = view.findViewById(R.id.app_bar_new_post);
        location = view.findViewById(R.id.imageview_location_newpost);
        post_btn = view.findViewById(R.id.new_post_fragment_post_button);
        user_name_tv = view.findViewById(R.id.new_post_fragment_user_name_textview);
        user_image = view.findViewById(R.id.new_post_fragment_user_image_img);
        post_location_et = view.findViewById(R.id.new_post_fragment_post_location_edittext);
        post_data_et = view.findViewById(R.id.new_post_fragment_post_data_edittext);
        post_type_spr = view.findViewById(R.id.new_post_fragment_post_type_spiner);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("New Post");

        arrayAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, post_type_array);
        post_type_spr.setAdapter(arrayAdapter);

        receive_data_from_phone();
        display_data();

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getlocation();
            }
        });




        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                post_data();

            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(),MenuActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });



        return view;
    }

    private void getlocation() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        } else {


            try {
                Toast.makeText(getActivity(),"Permission  grunted",Toast.LENGTH_SHORT).show();

                LocationManager locationmanager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                Location location = locationmanager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                double latitude=location.getLatitude();
                double longitude=location.getLongitude();
                String city = hereLocation(latitude, longitude);
                post_location_et.setText(city);



            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Not found"+e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String hereLocation(double latitude, double longitude) {



        String str="";


        List<Address> addressList = null;

        try {
            Geocoder geocoder =new Geocoder(getActivity().getApplicationContext());

            addressList = geocoder.getFromLocation(latitude, longitude, 1);
             str = addressList.get(0).getLocality();
             String sub_str;
            sub_str= addressList.get(0).getSubLocality();
            if (sub_str!=null){
                str=str+","+sub_str;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        return str;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1000: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                        // TODO: Consider calling

                        return;
                    }


                    try {
                        Toast.makeText(getActivity(),"Permission  grunted",Toast.LENGTH_SHORT).show();

                        LocationManager locationmanager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                        Location location = locationmanager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        double latitude=location.getLatitude();
                        double longitude=location.getLongitude();
                        String city = hereLocation(latitude, longitude);
                        post_location_et.setText(city);

//
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(getActivity(),"Not found1",Toast.LENGTH_SHORT).show();
                    }
                }else
                {
                    Toast.makeText(getActivity(),"Permission not grunted",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void post_data() {
        //String type,loc,data;
        post_type=post_type_spr.getSelectedItem().toString();
        post_loc=post_location_et.getText().toString();
        post_data=post_data_et.getText().toString();

        checkinternet chi=new checkinternet(getActivity());

        if (post_type.equals("Select Post Type")){
            TextView error=(TextView) post_type_spr.getSelectedView();
            error.setError("Select Any Post Type");
            return;
        }

        if (post_loc.isEmpty()) {

            post_location_et.setError("Enter Post location");
            post_location_et.requestFocus();
            return;
        }
        if (post_data.isEmpty()) {

            post_data_et.setError("Field Cann't be Empty");
            post_data_et.requestFocus();
            return;
        }

        if (!(chi.checknet())){

            Toast.makeText(getActivity(),"Check Your Internet Connenction",Toast.LENGTH_LONG).show();
            return;
        }


        else {

            final showbox sb=new showbox(getActivity());
            sb.show("Please Wait...","Connecting TO Server...");
            dbhandler_post_upload db=new dbhandler_post_upload(getActivity(), new listener_gernal() {
                @Override
                public void onSuccess(Object object) {
                    if(object.equals("true")){
                       // Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(getActivity(),MenuActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        sb.cancel();
                    }
                    if(object.equals("false")){


                        sb.cancel();
                    }
                }

                @Override
                public void onFailure(Exception e) {

                    Toast.makeText(getActivity(),"Server Error Occured",Toast.LENGTH_LONG).show();
                    sb.cancel();
                }
            });
            db.upload(user_id,post_type,post_loc,post_data);

        }

    }



    private void receive_data_from_phone() {

        sharedPreferences = getActivity().getSharedPreferences("Angelic_Hand_user_data",MODE_PRIVATE);
        String fname = sharedPreferences.getString("userfname","tayy");
        String lname = sharedPreferences.getString("userlname","tayy");
        user_id = sharedPreferences.getString("userid","tayy");
        user_pic= sharedPreferences.getString("userpicture","tayy");

        user_name=fname+" "+lname;


    }

    private void display_data() {

        if (!(user_name.isEmpty() || user_name==null)){
            user_name_tv.setText(user_name);
        }

        if (!(user_pic.isEmpty() || user_pic==null)){

        }String url="http://192.168.43.104:8080/Angelic_Hand/images/";
        url=url+user_pic;
        Picasso.get().load(url).fit().centerCrop().placeholder(R.drawable.icon_user_profile).into(user_image);
    }
}
