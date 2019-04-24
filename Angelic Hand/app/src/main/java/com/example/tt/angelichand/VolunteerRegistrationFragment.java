package com.example.tt.angelichand;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class VolunteerRegistrationFragment extends Fragment {

    EditText first_name_et,last_name_et,address_et,volunteerno_et;
    Spinner organization_spr,proviance_spr,city_spr;
    String number,userid,password,type;
    Button registration_btn;
    String proviance[]={"Select Proviance---","Punjab"};
    String punjab_city[]={"Select City---","Attock","Bahawalnagar","Bahawalpur","Bhakkar","Chakwal","Chiniot","Dera Ghazi Khan","Faisalabad","Gujranwala","Hafizabad",
            "Jhang","Jhelum","Kasur","Khanewal","Khushab","Lahore","Lodhran","Mandi Bahauddin","Mianwali","Multan","Murree","Muzaffargarh", "Nankana Sahib",
            "Narowal","Okara","Pakpatan","Rahimyar Khan","Rajanpur", "Rawalpindi", "Sahiwal","Sargodha", "Sheikhupura", "Sialkot", "Toba Tek Singh", "Vehari"};
    String org_type[]={"Select Organization---","Sundas Foundation","Al-Khidmat Foundation","Shukat Khanam","Others"};

    ArrayAdapter proviance_arrayAdapter,punjab_arrayAdapter,organization_arrayAdapter;

    public VolunteerRegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_volunteer_registration, container, false);
        registration_btn=view.findViewById(R.id.volunteerreg_fragment_registration_button);
        first_name_et=view.findViewById(R.id.volunteerreg_fragment_firstname_edittext);
        last_name_et=view.findViewById(R.id.volunteerreg_fragment_lastname_edittext);
        address_et=view.findViewById(R.id.volunteerreg_fragment_street_address_edittext);
        volunteerno_et=view.findViewById(R.id.volunteerreg_fragment_volunteerno_edittext);

        proviance_spr=view.findViewById(R.id.volunteerreg_fragment_proviance_spiner);
        city_spr=view.findViewById(R.id.volunteerreg_fragment_city_spiner);
        organization_spr=view.findViewById(R.id.volunteerreg_fragment_organization_spiner);



        proviance_arrayAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,proviance);
        proviance_spr.setAdapter(proviance_arrayAdapter);

        organization_arrayAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,org_type);
        organization_spr.setAdapter(organization_arrayAdapter);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            Toast.makeText(getActivity(),"reg"+number,Toast.LENGTH_LONG).show();
            number=bundle.getString("number");
            userid=bundle.getString("userid");
            password=bundle.getString("password");
            type=bundle.getString("type");

            if (type.equals("donor")){
                organization_spr.setEnabled(false);
                organization_spr.setVisibility(View.INVISIBLE);
                volunteerno_et.setEnabled(false);
                volunteerno_et.setVisibility(View.INVISIBLE);

            }

            else if (type.equals("volunteer")){
                organization_spr.setEnabled(true);
                organization_spr.setVisibility(View.VISIBLE);
                volunteerno_et.setEnabled(true);
                volunteerno_et.setVisibility(View.VISIBLE);

            }
        }



        first_name_et.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if(source.equals("")){ // for backspace
                    return source;
                }
                if(source.toString().matches("^[a-zA-Z ]+$")){
                    return source;
                }

                return "";
            }
        }});

        last_name_et.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if(source.equals("")){ // for backspace
                    return source;
                }
                if(source.toString().matches("^[a-zA-Z ]+$")){
                    return source;
                }

                return "";
            }
        }});


//        first_name_et.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                String st;
//                Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
//                Matcher ms = ps.matcher(first_name_et.getText().toString());
//                boolean bs = ms.matches();
//                if (bs == false) {
//
//
//                    Toast.makeText(getActivity(),"Only Alphabets",Toast.LENGTH_LONG).show();
//
//                }
//            }
//        });

        city_spr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String pro=proviance_spr.getSelectedItem().toString();

                if (pro.equals("Select Proviance---")){

                    TextView error=(TextView) proviance_spr.getSelectedView();
                    error.setError("Select Proviance First");
                    error.requestFocus();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


        proviance_spr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pro=proviance_spr.getSelectedItem().toString();

                if (pro.equals("Select Proviance---")){

                    city_spr.setAdapter(null);

                }

                if (pro.equals("Punjab")){
                    punjab_arrayAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,punjab_city);
                    city_spr.setAdapter(punjab_arrayAdapter);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        registration_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendtomenu();
            }
        });






        return view;
    }

    private void sendtomenu() {

        String f_name,l_name,pro,city="",st_add,org="",vol="";
        checkinternet chi=new checkinternet(getActivity());


//        if (!(city_spr.isActivated())){
//            TextView error=(TextView) proviance_spr.getSelectedView();
//            error.setError("city lazmi Required");
//            error.requestFocus();
//            return;
//
//        }




        f_name=first_name_et.getText().toString();
        l_name=last_name_et.getText().toString();
        pro=proviance_spr.getSelectedItem().toString();
        st_add=address_et.getText().toString();

        if (type.equals("volunteer")){
            org=organization_spr.getSelectedItem().toString();
            vol=volunteerno_et.getText().toString();

        }

        if (f_name.isEmpty()){
            first_name_et.setError("First Name is Required");
            first_name_et.requestFocus();
            return;
        }
        if (l_name.isEmpty()){
            last_name_et.setError("Last Name is Required");
            last_name_et.requestFocus();
            return;
        }
        if (pro.equals("Select Proviance---")){
            TextView error=(TextView) proviance_spr.getSelectedView();
            error.setError("Proviance Required");
            error.requestFocus();
            return;
        }
        if (!(pro.equals("Select Proviance---"))){

            city=city_spr.getSelectedItem().toString();
            if (city.equals("Select City---")){
                TextView error=(TextView) city_spr.getSelectedView();
                error.setError("City Required");
                error.requestFocus();
                return;
            }

        }

        if (st_add.isEmpty()){
            address_et.setError("Addresss is Required");
            address_et.requestFocus();
            return;
        }
        if (type.equals("volunteer")) {
            if (org.equals("Select Organization---")) {
                TextView error = (TextView) organization_spr.getSelectedView();
                error.setError("Organization Required");
                error.requestFocus();
                return;
            }
            if (!(org.equals("Select Organization---"))) {

                if (!(org.equals("Others")) && vol.isEmpty()) {
                    volunteerno_et.setError("Volunteer No is Required");
                    volunteerno_et.requestFocus();
                    return;
                }


            }
        }
        if (!(chi.checknet())){

            Toast.makeText(getActivity(),"Check Your Internet Connenction",Toast.LENGTH_LONG).show();
            return;
        }

        else {

            final showbox sb=new showbox(getActivity());
            sb.show("Please Wait...","Connecting to Server...");
            dbhandler_registration db=new dbhandler_registration(getActivity(), new listener_gernal() {
                @Override
                public void onSuccess(Object object) {

                    if (object.equals("true")){

                        Bundle bundle=new Bundle();
                        bundle.putString("userid",userid);
                        bundle.putString("type",type);
                        uploadPictureFragment vol=new uploadPictureFragment();
                        vol.setArguments(bundle);
//                        Intent intent=new Intent(getActivity(),MenuActivity.class);
//                        startActivity(intent);
//                        getActivity().finish();

                        android.support.v4.app.FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.registration_frame,vol);
                        fragmentTransaction.commit();
                        sb.cancel();
                    }

                    else if (object.equals("false")){

                        Intent intent=new Intent(getActivity(),LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        Toast.makeText(getActivity(),"Some Server Error Occured Try Agian",Toast.LENGTH_LONG).show();
                        sb.cancel();
                    }
                    else if (object.equals("already")){

                        Intent intent=new Intent(getActivity(),LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        Toast.makeText(getActivity(),"User ID Already Exist....",Toast.LENGTH_LONG).show();
                        sb.cancel();
                    }
                    //Toast.makeText(getActivity(),object.toString(),Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(Exception e) {

                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                    sb.cancel();
                }
            });
            db.register(type,userid,number,password,f_name,l_name,pro,city,st_add,org,vol);

        }

        Toast.makeText(getActivity(),type+number+userid+password+"acha",Toast.LENGTH_LONG).show();


    }

}
