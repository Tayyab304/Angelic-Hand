package com.example.tt.angelichand;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPassword_take_id_Fragment extends Fragment {


    EditText frgpassfragment_user_id_et;
    Button frgpassfragment_send_bt;

    public ForgetPassword_take_id_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_forget_password_take_id_, container, false);

        frgpassfragment_user_id_et=view.findViewById(R.id.frgpass_fragment_userid_edittext);
        frgpassfragment_send_bt=view.findViewById(R.id.frgpass_fragment_send_button);

        frgpassfragment_send_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_password();
            }
        });

        return view;
    }

    private void send_password() {

        final String userid=frgpassfragment_user_id_et.getText().toString().trim();

        checkinternet chi=new checkinternet(getActivity());
        if (userid.isEmpty()){
            frgpassfragment_user_id_et.setError("UserId is Required.");
            frgpassfragment_user_id_et.requestFocus();
            return;

        }

        if (!(chi.checknet())){
            Toast.makeText(getActivity(),"Check Your Internet Connenction",Toast.LENGTH_LONG).show();
            return;

        }

        else {

            final showbox sb = new showbox(getActivity());
            sb.show("Please Wait...", "Sending code");

            dbhandler_fargetpassword db=new dbhandler_fargetpassword(getActivity(), new listener_gernal() {
                @Override
                public void onSuccess(Object object) {
                    final String mobile=object.toString();

                    if (object.equals("false")){

                        Toast.makeText(getActivity(),"NO UserId Found",Toast.LENGTH_LONG).show();
                    }
                    else if (mobile!=null){

                        FireBaseNumber_Authentication auth=new FireBaseNumber_Authentication(getActivity(), new listener_gernal() {
                            @Override
                            public void onSuccess(Object object) {
                                String code=object.toString();

                                sb.cancel();
                                Bundle bundle = new Bundle();
                                bundle.putString("type","forget");
                                bundle.putString("number",mobile);
                                bundle.putString("code",code);


                                VerifyNumberFragment vrfy=new VerifyNumberFragment();
                                vrfy.setArguments(bundle);

                                sb.cancel();
                                android.support.v4.app.FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.forgetpass_frame,vrfy);
                                fragmentTransaction.commit();
                            }

                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(getActivity(),"Error"+e.toString(),Toast.LENGTH_LONG).show();
                                sb.cancel();
                            }
                        });
                        auth.send_code(mobile);





                        sb.cancel();
                    }
                    sb.cancel();
                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getActivity(),"Error"+e.toString(),Toast.LENGTH_LONG).show();
                    sb.cancel();
                }
            });
            db.isuseridExist(userid);


        }

        }

}
