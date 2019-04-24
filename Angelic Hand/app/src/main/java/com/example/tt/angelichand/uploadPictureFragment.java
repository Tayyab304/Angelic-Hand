package com.example.tt.angelichand;


import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
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
public class uploadPictureFragment extends Fragment {


    SharedPreferences sharedPreferences;
    Bitmap bitmap;
    File file;
    Uri filuri;
    Button upload_btn,skip_btn;
    CircleImageView user_image_profile;
    String type,user_id,pic_name,pic_encoded;

    final static int Gallery_Pick=1;
    public uploadPictureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_upload_picture, container, false);
        upload_btn=view.findViewById(R.id.fragment_user_pic_upload_button);
        skip_btn=view.findViewById(R.id.fragment_user_pic_skip_button);
        user_image_profile=view.findViewById(R.id.fragment_user_pic_image_imageview);


        Bundle bundle = this.getArguments();
        if(bundle != null){

            user_id=bundle.getString("userid");
            type=bundle.getString("type");
        }

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery=new Intent();
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery,Gallery_Pick);
            }
        });

        skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(),MenuActivity.class);
                intent.putExtra(MenuActivity.FLAG_MENU,"volunteer");
                startActivity(intent);
                getActivity().finish();



            }
        });




        return view;
    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null){

            // Toast.makeText(getActivity(),"5",Toast.LENGTH_LONG).show();
            Uri imageuri=data.getData();

            CropImage.activity(imageuri).setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1).start(getContext(),this);   // for activity wastai use only this in start method

            Toast.makeText(getActivity(),"4 :"+imageuri,Toast.LENGTH_LONG).show();

        }

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {


            // Toast.makeText(getActivity(),"1",Toast.LENGTH_LONG).show();
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                final showbox sb=new showbox(getActivity());
                sb.show("Please Wait...","Connecting to Server...");

                //Toast.makeText(getActivity(),"2",Toast.LENGTH_LONG).show();

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

                if (pic_encoded!=null) {

                    checkinternet chi=new checkinternet(getActivity());
                    if (!(chi.checknet())){

                        Toast.makeText(getActivity(),"Check Your Internet Connenction",Toast.LENGTH_LONG).show();
                        return;
                    }
                    dbhandler_picture dbp = new dbhandler_picture(getActivity(), new listener_gernal() {
                        @Override
                        public void onSuccess(Object result) {

                            if (result.equals("true")){

                                sharedPreferences = getActivity().getSharedPreferences("Angelic_Hand_user_data",MODE_PRIVATE);
                                String pic1 = sharedPreferences.getString("userpicture","tayy");
                                Toast.makeText(getActivity(),"Picture Updated Successfully",Toast.LENGTH_LONG).show();

                                if (pic1!=null) {

                                    String url="http://192.168.43.104:8080/Angelic_Hand/images/";
                                    url=url+pic1;
                                    Toast.makeText(getActivity(),"tayab2  :"+url,Toast.LENGTH_LONG).show();
                                    Picasso.get().load(url).fit().placeholder(R.drawable.icon_user_profile).into(user_image_profile);

                                }

                                sb.cancel();

                                skip_btn.setText("Next");

                            }

                            else if (result.equals("false")){

                                Toast.makeText(getActivity(),"Picture Not Updated",Toast.LENGTH_LONG).show();
                                sb.cancel();
                            }

                        }

                        @Override
                        public void onFailure(Exception e) {

                            Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
                            sb.cancel();
                        }
                    });
                    dbp.upload_pic(type,user_id, pic_name, pic_encoded);

                }

            }
            else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(getActivity(),"Error"+error,Toast.LENGTH_LONG).show();
            }

        }
    }
}
