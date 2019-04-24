package com.example.tt.angelichand;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Toolbar toolbar;
    Button save;
    ArrayAdapter arrayAdapter;
    EditText userfname_profile,userlname_profile,useraddress_profile,uservolunteerno_profile,userorganization_profile;
    Spinner userorganization_profile_spiner;
    String user_id,pic_encoded,user_pic;
    String org_name,org_add;
    String fisrt_name,last_name,address,vole_no,organization,type;
    String org_type[]={"Select Organization","Sundas Foundation","Al-Khidmat Foundation","Shukat Khanam","Others"};
    CircleImageView user_image_profile;
    ImageView edit;
    final static int Gallery_Pick=1;
    Bitmap bitmap;
    File file;
    Uri filuri;
    String pic_name;
    boolean flag_alltrue=false;
    String fname,lname,add,vol,org;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        toolbar=view.findViewById(R.id.app_bar_user_profile);
        edit = view.findViewById(R.id.edit_profile);
        save = view.findViewById(R.id.save_profile);
        userfname_profile = view.findViewById(R.id.fname_profile);
        userlname_profile = view.findViewById(R.id.lname_profile);
        useraddress_profile = view.findViewById(R.id.add_profile);
        uservolunteerno_profile = view.findViewById(R.id.org_vol_num_profile);
        userorganization_profile = view.findViewById(R.id.user_org_profile);
        user_image_profile=view.findViewById(R.id.user_image_profile);
        userorganization_profile_spiner=view.findViewById(R.id.user_org_profile_spr);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");

        receive_data_from_phone();

        if (type.equals("donor")){

            uservolunteerno_profile.setVisibility(View.INVISIBLE);
            userorganization_profile.setVisibility(View.INVISIBLE);
            userorganization_profile_spiner.setVisibility(View.INVISIBLE);
        }
        else if (type.equals("volunteer")){

            uservolunteerno_profile.setVisibility(View.VISIBLE);
            userorganization_profile.setVisibility(View.VISIBLE);
            userorganization_profile_spiner.setVisibility(View.INVISIBLE);
        }

        else if (type.equals("organization")){


        }

        display_data();

        hide_views();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(),MenuActivity.class);
                startActivity(intent);
                getActivity().finish();
                //getActivity().onBackPressed();

                //getActivity().onBackPressed();
            }
        });
        user_image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery=new Intent();
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery,Gallery_Pick);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                show_views();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ch;
                ch=check();
                if (ch==true) {

                    final showbox sb = new showbox(getActivity());
                    sb.show("Please Wait...", "Connecting TO Server...");
                    dbhandler_update_profile db = new dbhandler_update_profile(getActivity(), new listener_gernal() {
                        @Override
                        public void onSuccess(Object object) {
                            if (object.equals("true")) {

                                hide_views();
                                receive_data_from_phone();
                                display_data();
                                Toast.makeText(getActivity(), "Successfully Updated", Toast.LENGTH_LONG).show();
                                sb.cancel();
                            } else if (object.equals("false")) {

                                Toast.makeText(getActivity(), "Updation Failed", Toast.LENGTH_LONG).show();

                                sb.cancel();
                            }

                        }

                        @Override
                        public void onFailure(Exception e) {

                            Toast.makeText(getActivity(), "Server Error" + e, Toast.LENGTH_LONG).show();
                            sb.cancel();
                        }
                    });
                    db.update(user_id, fname, lname, add, vol, org);
                }
            }
        });

//

        return view;
    }

    private void show_views() {

        edit.setEnabled(false);
        edit.setVisibility(View.INVISIBLE);
        save.setEnabled(true);
        save.setVisibility(View.VISIBLE);
        userorganization_profile_spiner.setEnabled(true);
        userorganization_profile_spiner.setVisibility(View.VISIBLE);
        userorganization_profile.setEnabled(false);
        userorganization_profile.setVisibility(View.INVISIBLE);
        userfname_profile.setEnabled(true);
        userlname_profile.setEnabled(true);
        useraddress_profile.setEnabled(true);
        userorganization_profile.setEnabled(true);
        uservolunteerno_profile.setEnabled(true);
    }

    private void hide_views() {
        userorganization_profile_spiner.setEnabled(false);
        userorganization_profile_spiner.setVisibility(View.INVISIBLE);

        if (type.equals("donor")){
            userorganization_profile.setVisibility(View.INVISIBLE);
        }
        else if (type.equals("volunteer")){

            userorganization_profile.setVisibility(View.VISIBLE);
        }

        save.setVisibility(View.INVISIBLE);
        save.setEnabled(false);

        edit.setEnabled(true);
        edit.setVisibility(View.VISIBLE);

        userfname_profile.setEnabled(false);
        userlname_profile.setEnabled(false);
        useraddress_profile.setEnabled(false);
        userorganization_profile.setEnabled(false);
        uservolunteerno_profile.setEnabled(false);
    }

    private void display_data() {

        if (user_pic!=null) {
            String url="http://192.168.43.104:8080/Angelic_Hand/images/";
            url=url+user_pic;
            Toast.makeText(getActivity(),"picture   :"+url,Toast.LENGTH_LONG).show();
            Picasso.get().load(url).fit().placeholder(R.drawable.icon_user_profile).into(user_image_profile);

        }
        if (type.equals("volunteer")){
            userfname_profile.setText(fisrt_name);
            userlname_profile.setText(last_name);
            useraddress_profile.setText(address);
            userorganization_profile.setText(organization);
            uservolunteerno_profile.setText(vole_no);

            arrayAdapter =new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,org_type);
            userorganization_profile_spiner.setAdapter(arrayAdapter);
        }




       else if (type.equals("organization")){
            useraddress_profile.setText(org_add);

        }
    }

    private void receive_data_from_phone() {

        sharedPreferences = getActivity().getSharedPreferences("Angelic_Hand_user_data",MODE_PRIVATE);
        type = sharedPreferences.getString("type","tayy");
        user_id = sharedPreferences.getString("userid","tayy");
        fisrt_name = sharedPreferences.getString("userfname","tayy");
        last_name = sharedPreferences.getString("userlname","tayy");
        user_pic= sharedPreferences.getString("userpicture","tayy");
        address= sharedPreferences.getString("useraddress","tayy");
        if (type.equals("volunteer")){

            organization= sharedPreferences.getString("userorganization","tayy");
            vole_no = sharedPreferences.getString("uservolunteerno","tayy");
        }

        else if (type.equals("organization")){

            org_name= sharedPreferences.getString("organization_name","tayy");
            org_add= sharedPreferences.getString("organization_address","tayy");
            user_pic= sharedPreferences.getString("organization_pic","tayy");
        }




    }

    private boolean check() {
        checkinternet chi=new checkinternet(getActivity());

        fname=userfname_profile.getText().toString();
        lname=userlname_profile.getText().toString();
        add=useraddress_profile.getText().toString();
        vol=uservolunteerno_profile.getText().toString();
        org=userorganization_profile_spiner.getSelectedItem().toString();


        if (fname.isEmpty()){
            userfname_profile.setError("Please Enter First Name");
            userfname_profile.requestFocus();
            flag_alltrue=false;

        }
        else if (lname.isEmpty()){
            userlname_profile.setError("Please Enter Last Name");
            userlname_profile.requestFocus();
            flag_alltrue=false;

        }
        else if (add.isEmpty()){
            useraddress_profile.setError("Please Enter Address");
            useraddress_profile.requestFocus();
            flag_alltrue=false;

        }

        else if (org.equals("Select Organization")){
            TextView error=(TextView) userorganization_profile_spiner.getSelectedView();
            error.setError("select Any Organization");
            flag_alltrue=false;

        }
       else if (!organization.equals("Select Organization")){

            if (organization!="Others" && vol.isEmpty()){
                uservolunteerno_profile.setError("Please Volunteer No ");
                uservolunteerno_profile.requestFocus();
                flag_alltrue=false;
            }

            else {

                flag_alltrue=true;
            }

        }
        else if (!(chi.checknet())){

            Toast.makeText(getActivity(),"Check Your Internet Connenction",Toast.LENGTH_LONG).show();
            flag_alltrue=false;

        }


        else {
            flag_alltrue=true;
        }

        return flag_alltrue;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null) {

            // Toast.makeText(getActivity(),"5",Toast.LENGTH_LONG).show();
            Uri imageuri = data.getData();

            CropImage.activity(imageuri).setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1).start(getContext(), this);   // for activity wastai use only this in start method

            //Toast.makeText(getActivity(),"4 :"+imageuri,Toast.LENGTH_LONG).show();

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            // Toast.makeText(getActivity(),"1",Toast.LENGTH_LONG).show();
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                //Toast.makeText(getActivity(),"2",Toast.LENGTH_LONG).show();

                final showbox sb = new showbox(getActivity());
                sb.show("Please Wait...", "Connecting to Server...");
                filuri = result.getUri();


                Toast.makeText(getActivity(), "2 :" + result.getUri(), Toast.LENGTH_LONG).show();
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss_a ");
                String strDate = mdformat.format(calendar.getTime());
                pic_name = user_id + strDate + ".jpg";
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        + File.separator + pic_name);

                bitmap = BitmapFactory.decodeFile(filuri.getPath());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                byte[] array = stream.toByteArray();
                pic_encoded = Base64.encodeToString(array, 0);

                if (pic_encoded != null) {

                    checkinternet chi = new checkinternet(getActivity());
                    if (!(chi.checknet())) {

                        Toast.makeText(getActivity(), "Check Your Internet Connenction", Toast.LENGTH_LONG).show();
                        return;
                    }
                    dbhandler_picture dbp = new dbhandler_picture(getActivity(), new listener_gernal() {
                        @Override
                        public void onSuccess(Object result) {

                            if (result.equals("true")) {

                                sharedPreferences = getActivity().getSharedPreferences("Angelic_Hand_user_data", MODE_PRIVATE);
                                String pic1 = sharedPreferences.getString("userpicture", "tayy");
                                Toast.makeText(getActivity(), "Picture Updated Successfully", Toast.LENGTH_LONG).show();

                                if (pic1 != null) {

                                    String url = "http://192.168.43.104:8080/Angelic_Hand/images/";
                                    url = url + pic1;
                                    Toast.makeText(getActivity(), "tayab2  :" + url, Toast.LENGTH_LONG).show();
                                    Picasso.get().load(url).fit().placeholder(R.drawable.icon_user_profile).into(user_image_profile);

                                }

                                sb.cancel();


                            } else if (result.equals("false")) {

                                Toast.makeText(getActivity(), "Picture Not Updated", Toast.LENGTH_LONG).show();
                                sb.cancel();
                            }

                        }

                        @Override
                        public void onFailure(Exception e) {

                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                            sb.cancel();
                        }
                    });
                    dbp.upload_pic(type,user_id, pic_name, pic_encoded);

                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(getActivity(), "Error" + error, Toast.LENGTH_LONG).show();
            }

        }

    }
}

