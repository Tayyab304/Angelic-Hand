package com.example.tt.angelichand;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class VerifyNumberFragment extends Fragment {
    EditText verifynuberfragment_number_et;
    Button verifynuberfragment_send_bt;
    String code,number,type;



    public VerifyNumberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_verify_number, container, false);
        verifynuberfragment_number_et=view.findViewById(R.id.verifynumberfragment_number_edittext);
        verifynuberfragment_send_bt=view.findViewById(R.id.verifynumberfragment_send_button);

        verifynuberfragment_number_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String num=verifynuberfragment_number_et.getText().toString();
                if (num.length()>5){
                    Toast.makeText(getActivity(),"Only 6 Digit Acceptable",Toast.LENGTH_LONG).show();

                }
            }
        });



        Bundle bundle = this.getArguments();

        if(bundle != null){
            number=bundle.getString("number");
            code=bundle.getString("code");
            type=bundle.getString("type");
        }

        verifynuberfragment_send_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifynumber();
            }
        });
        return view;
    }

    private void verifynumber() {

        String enter_code=verifynuberfragment_number_et.getText().toString();
        checkinternet chi=new checkinternet(getActivity());

        if (enter_code.isEmpty()){

            verifynuberfragment_number_et.setError("Enter Verification Code");
            verifynuberfragment_number_et.requestFocus();
            return;
        }
        if(enter_code.length()<5){
            verifynuberfragment_number_et.setError("Enter a Valid Code..");
            verifynuberfragment_number_et.requestFocus();
            return;
        }
        if (!(chi.checknet())){
            Toast.makeText(getActivity(),"Check Your Internet Connenction",Toast.LENGTH_LONG).show();
            return;

        }

        else {

            final showbox sb=new showbox(getActivity());
            sb.show("Please Wait...","Verifying Code...");
            FireBaseNumber_Authentication auth=new FireBaseNumber_Authentication(getActivity(), new listener_gernal() {
                @Override
                public void onSuccess(Object object) {
                    if (object.equals("true")){

                        Intent intent=new Intent(getActivity(),RegistrationActivity.class);
                        intent.putExtra(RegistrationActivity.FLAG_REGISTRATION,type);
                        intent.putExtra("number",number);

                        Toast.makeText(getActivity(),"verify"+number,Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        getActivity().finish();
//                        Bundle bundle=new Bundle();
//                        bundle.putString("number",number);
//                        VolunteerIdFragment vol=new VolunteerIdFragment();
//                        vol.setArguments(bundle);
//                        android.support.v4.app.FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.replace(R.id.mobileverification_frame,vol);
//                        fragmentTransaction.commit();

                        Toast.makeText(getActivity(),"Registered..",Toast.LENGTH_LONG).show();
                    }
                    if (object.equals("false")){

                        verifynuberfragment_number_et.setText("");
                        verifynuberfragment_number_et.setError("InValid Code");
                        Toast.makeText(getActivity(),"Verification Failed",Toast.LENGTH_LONG).show();

                    }

                    sb.cancel();
                }

                @Override
                public void onFailure(Exception e) {

                    Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
                    sb.cancel();
                }


            });
            auth.verify_code(code,enter_code);
        }
    }

}
