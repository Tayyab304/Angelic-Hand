package com.example.tt.angelichand;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class VolunteerIdFragment extends Fragment {

    public static String FLAG_ID;
    EditText userid,passowrd,confirmpassword;
    Button next;
    String number,type;
    String user,pass,confpass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String org_name,org_add,org_no,org_phone,org_link;

    public VolunteerIdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_volunteer_id, container, false);
        userid=view.findViewById(R.id.volunteer_fragment_userid_edittext);
        passowrd=view.findViewById(R.id.volunteer_fragment_password_edittext);
        confirmpassword=view.findViewById(R.id.volunteer_fragment_confirm_password_edittext);
        next=view.findViewById(R.id.volunteer_fragment_next_button);

        Bundle bundle = this.getArguments();


            type=bundle.getString(FLAG_ID);
            if (type.equals("organization")){

                org_name=bundle.getString("org_name");
                org_add=bundle.getString("org_add");
                org_no=bundle.getString("org_no");
                org_phone=bundle.getString("org_phone");
                org_link=bundle.getString("org_link");

            }
            else if (type.equals("volunteer")  ){
                number=bundle.getString("number");

            }
            else if (type.equals("donor")){

                number=bundle.getString("number");
                Toast.makeText(getActivity(),"id"+number,Toast.LENGTH_LONG).show();
            }



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendtoregistration();
            }
        });


        return view;
    }

    private void sendtoregistration() {


        user=userid.getText().toString();
        pass=passowrd.getText().toString();
        confpass=confirmpassword.getText().toString();

        checkinternet chi=new checkinternet(getActivity());

        if (user.isEmpty()){
            userid.setError("Enter UserId");
            userid.requestFocus();
            return;
        }

        if (pass.isEmpty()){

            passowrd.setError("Enter Password");
            passowrd.requestFocus();
            return;
        }

        if (confpass.isEmpty()){
            confirmpassword.setError("Enter Password");
            confirmpassword.requestFocus();
            return;

        }

        if (!(pass.equals(confpass))){

            passowrd.setText("");
            confirmpassword.setText("");
            passowrd.setError("Enter Same password");
            confirmpassword.setError("Enter Same Password");
            return;
        }

        if (!(chi.checknet())){

            Toast.makeText(getActivity(),"Check Your Internet Connenction",Toast.LENGTH_LONG).show();
            return;
        }


        else {

            final showbox sb=new showbox(getActivity());
            sb.show("Please Wait...","Connecting TO Server...");
            dbhandler_checkuser db=new dbhandler_checkuser(getActivity(), new listener_gernal() {
                @Override
                public void onSuccess(Object object) {

                    if (object.equals("true")){
                        userid.setError("UserId Already Exist");
                        userid.requestFocus();
                        sb.cancel();
                        return;

                    }
                    if (object.equals("false")) {

                        if (type.equals("volunteer") || type.equals("donor")) {
                            Bundle bundle = new Bundle();
                            bundle.putString("number", number);
                            bundle.putString("userid", user);
                            bundle.putString("password", pass);
                            bundle.putString("type", type);
                            VolunteerRegistrationFragment vol = new VolunteerRegistrationFragment();
                            vol.setArguments(bundle);

                            android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.registration_frame, vol);
                            fragmentTransaction.commit();

                        }

                        if (type.equals("organization")){

                            send_data_to_server();

                        }
                        sb.cancel();
                    }

                }

                @Override
                public void onFailure(Exception e) {

                    Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
                    sb.cancel();
                }
            });
            db.ifexist(type,user);


//
        }
    }

    private void send_data_to_server() {

        final showbox sb=new showbox(getActivity());
        sb.show("Please Wait...","Connecting TO Server...");

        dbhandler_registration_organization db=new dbhandler_registration_organization(getActivity(), new listener_gernal() {
            @Override
            public void onSuccess(Object object) {
                if (object.equals("true")){
                    save_data_to_phone();
                    Intent intent=new Intent(getActivity(),MenuActivity.class);
                    intent.putExtra(MenuActivity.FLAG_MENU,"organization");
                    startActivity(intent);
                    getActivity().finish();
                    sb.cancel();
                }
                else if (object.equals("false")){

                    Toast.makeText(getActivity(),"Server Error",Toast.LENGTH_LONG).show();
                    sb.cancel();
                }
                else if (object.equals("already")){

                    Toast.makeText(getActivity(),"User Id Already Exist",Toast.LENGTH_LONG).show();
                    sb.cancel();
                }
            }

            @Override
            public void onFailure(Exception e) {

                Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
                sb.cancel();
            }
        });
        db.register(user,org_name,org_add,org_no,org_phone,org_link,pass);

    }

    private void save_data_to_phone() {

        sharedPreferences =getActivity().getSharedPreferences("Angelic_Hand_user_data",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putBoolean("islogin",true);
        editor.putString("type",type);
        editor.putString("userid",user);
        editor.putString("organization_name",org_name);
        editor.putString("organization_no",org_no);
        editor.putString("organization_phone",org_phone);
        editor.putString("organization_address",org_add);
        editor.putString("organization_link",org_link);



        editor.apply();
    }

}
