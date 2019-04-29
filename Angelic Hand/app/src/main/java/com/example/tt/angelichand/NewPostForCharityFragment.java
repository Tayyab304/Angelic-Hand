package com.example.tt.angelichand;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewPostForCharityFragment extends Fragment {


    private static final int Gallery_Pick_new_post_for_charity =1 ;

    Bitmap bitmap;
    File file;
    Uri filuri;
    String user_id,pic_name="",pic_encoded="";

    EditText charity_amount,charity_desc;
    RadioGroup charity_distribution;
    RadioButton charity_distribution_radio;
    Button add_image_charity;
    ImageView image_charity;
    ListView request_charity_lstview;
    String radio;
    String desc,pro,cty,loc,amount;
    Spinner proviance_food_spr,city_food_spr;
    ArrayAdapter proviance_arrayAdapter,city_arrayAdapter;
    String proviances[],city[];


    public NewPostForCharityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_new_post_for_charity, container, false);



        charity_amount=view.findViewById(R.id.new_post_amount_charity_fragment_et);
        charity_desc=view.findViewById(R.id.new_post_description_charity_fragment_et);
        charity_distribution=view.findViewById(R.id.new_post_distribution_charity_fragment_radio);
        add_image_charity=view.findViewById(R.id.new_post_add_pic_charity_fragment_button);
        image_charity=view.findViewById(R.id.new_post_pic_charity_fragment_imageview);



        proviance_food_spr=view.findViewById(R.id.new_post_proviance_charity_fragment_spr);
        city_food_spr=view.findViewById(R.id.new_post_city_charity_fragment_spr);
        request_charity_lstview=view.findViewById(R.id.new_post_requested_volunteers_location_charity_fragment_lstview);


        charity_distribution=view.findViewById(R.id.new_post_distribution_charity_fragment_radio);
        charity_distribution_radio=view.findViewById(charity_distribution.getCheckedRadioButtonId());
        radio=charity_distribution_radio.getText().toString();
        proviances=getResources().getStringArray(R.array.proviance);
        proviance_arrayAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,proviances);
        proviance_food_spr.setAdapter(proviance_arrayAdapter);


        charity_distribution.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                charity_distribution_radio=view.findViewById(checkedId);
                radio=charity_distribution_radio.getText().toString();


                if (radio.equals("Donate To nearby Volunteers")) {

//                    proviance_food_spr.setEnabled(true);
//                    city_food_spr.setEnabled(true);
//                    request_food_lstview.setEnabled(false);
                    proviance_food_spr.setVisibility(View.VISIBLE);
                    city_food_spr.setVisibility(View.VISIBLE);
                    request_charity_lstview.setVisibility(View.INVISIBLE);

                    proviances=getResources().getStringArray(R.array.proviance);
                    proviance_arrayAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,proviances);
                    proviance_food_spr.setAdapter(proviance_arrayAdapter);

                }

                else if (radio.equals("Donate To requested Volunteers")){

//                    proviance_food_spr.setEnabled(false);
//                    city_food_spr.setEnabled(false);
//                    request_food_lstview.setEnabled(true);
                    proviance_food_spr.setVisibility(View.INVISIBLE);
                    city_food_spr.setVisibility(View.INVISIBLE);

                    request_charity_lstview.setVisibility(View.VISIBLE);


                }
                //Toast.makeText(getActivity(),"radio :"+food_distribution_radio.getText().toString(),Toast.LENGTH_LONG).show();

            }
        });

        proviance_food_spr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String pro=proviance_food_spr.getSelectedItem().toString();

                if (pro.equals("Punjab")) {
                    city=getResources().getStringArray(R.array.Punjab);
                    city_arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, city);
                    city_food_spr.setAdapter(city_arrayAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add_image_charity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_charity.setVisibility(View.VISIBLE);
                Intent gallery=new Intent();
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                //gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(gallery,Gallery_Pick_new_post_for_charity);
            }
        });

        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==Gallery_Pick_new_post_for_charity && resultCode==RESULT_OK && data!=null){
            Uri imageuri=data.getData();

            CropImage.activity(imageuri).setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1).start(getContext(),this);

        }

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                final showbox sb=new showbox(getActivity());
                sb.show("Please Wait...","Uploading Picture...");

                //Toast.makeText(getActivity(),"2",Toast.LENGTH_LONG).show();

                filuri = result.getUri();


                //Toast.makeText(getActivity(), "2 :" + result.getUri(), Toast.LENGTH_LONG).show();
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

                if (pic_encoded!=null) {

                    checkinternet chi=new checkinternet(getActivity());
                    if (!(chi.checknet())){

                        Toast.makeText(getActivity(),"Check Your Internet Connenction",Toast.LENGTH_LONG).show();
                        return;
                    }
                    Picasso.get().load(filuri).fit().centerInside().placeholder(R.drawable.icon_user_profile).into(image_charity);
                    sb.cancel();

                }

            }

            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(getActivity(),"Error"+error.toString(),Toast.LENGTH_LONG).show();
            }
        }
    }


    public void receive(String userid,String type){

        user_id=userid;
        amount=charity_amount.getText().toString();
        desc=charity_desc.getText().toString();

        checkinternet chi=new checkinternet(getActivity());

        if (amount.isEmpty()){
            charity_amount.setError("Feild Is Requires");
            charity_amount.requestFocus();
            return;
        }

        if (desc.isEmpty()){
            charity_desc.setError("Feild Is Requires");
            charity_desc.requestFocus();
            return;
        }

        if (!charity_distribution_radio.isChecked()){

            charity_distribution_radio.setError("Feild is Required");
            charity_distribution_radio.requestFocus();
            return;
        }

        if (radio.equals("Donate To nearby Volunteers")){
            pro=proviance_food_spr.getSelectedItem().toString();
            if (pro.isEmpty() || pro.equals("Select Proviance---")) {
                TextView error = (TextView) proviance_food_spr.getSelectedView();
                error.setError("Feild is Required");
                error.requestFocus();
                return;
            }

            if (!pro.isEmpty()){
                cty=city_food_spr.getSelectedItem().toString();
                if (cty.isEmpty() || cty.equals("Select City---")) {

                    TextView error = (TextView) city_food_spr.getSelectedView();
                    error.setError("Feild is Required");
                    error.requestFocus();
                    return;
                }
                else {

                }


            }
        }

        if (!(chi.checknet())){

            Toast.makeText(getActivity(),"Check Your Internet Connenction",Toast.LENGTH_LONG).show();
            return;
        }


        else {

            loc=pro+","+cty;
            if (pic_name==null || pic_name.isEmpty()   || pic_encoded.isEmpty() || pic_encoded==null){

                pic_name="awe";
                pic_encoded="awe";
            }
            Toast.makeText(getActivity(),"awe"+desc+radio+pro+cty+user_id+type+pic_name,Toast.LENGTH_LONG).show();
            final showbox sb=new showbox(getActivity());
            sb.show("Please Wait...","Connecting TO Server...");
            dbhandler_upload_donation db=new dbhandler_upload_donation(getActivity(), new listener_gernal() {
                @Override
                public void onSuccess(Object object) {
                    if (object.equals("true")){
                        Intent intent=new Intent(getActivity(),MenuActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        sb.cancel();

                    }

                    if(object.equals("false")){
                        Toast.makeText(getActivity(),"Server Error Occured",Toast.LENGTH_LONG).show();
                        sb.cancel();
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getActivity(),"Server Error Occured"+e.toString(),Toast.LENGTH_LONG).show();
                    sb.cancel();

                }
            });
            db.upload(user_id,type,loc,desc,"","","","","","",amount,pic_name,pic_encoded);
        }


    }

}
