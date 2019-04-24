package com.example.tt.angelichand;


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
public class InputNumberFragment extends Fragment {


    EditText inputnuberfragment_number_et;
    Button inputnuberfragment_send_bt;
    String countycode,type;

    public InputNumberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_input_number, container, false);

        inputnuberfragment_number_et=view.findViewById(R.id.inputnumberfragment_number_edittext);
        inputnuberfragment_send_bt=view.findViewById(R.id.inputnumberfragment_send_button);


        Bundle bundle = this.getArguments();
        type=bundle.getString("type");
        inputnuberfragment_number_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String num=inputnuberfragment_number_et.getText().toString();
                if (num.length()>10){

                    Toast.makeText(getActivity(),"Only 11 Digit Acceptable",Toast.LENGTH_LONG).show();

                }

            }
        });




        inputnuberfragment_send_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendnumber();
            }
        });


        return view;
    }

    private void sendnumber() {
        final String number=inputnuberfragment_number_et.getText().toString().trim();

        checkinternet chi=new checkinternet(getActivity());
        if (number.isEmpty()){
            inputnuberfragment_number_et.setError("Number is Required.");
            inputnuberfragment_number_et.requestFocus();
            return;

        }

        if(number.length()<11){
            inputnuberfragment_number_et.setError("Enter a Valid Phone Number..");
            inputnuberfragment_number_et.requestFocus();
            return;
        }

        if (!(chi.checknet())){
            Toast.makeText(getActivity(),"Check Your Internet Connenction",Toast.LENGTH_LONG).show();
            return;

        }

        else {

            final showbox sb=new showbox(getActivity());
            sb.show("Please Wait...","Connecting To Server");

            final dbhandler_mobile_checking db=new dbhandler_mobile_checking(getActivity(), new listener_gernal() {
                @Override
                public void onSuccess(Object object) {
                    if (object.equals("true")){
                        inputnuberfragment_number_et.setError("Mobile Number Already Registerd.");
                        inputnuberfragment_number_et.requestFocus();
                        sb.cancel();
                        return;
                    }

                    else if (object.equals("false")){
                        sendtofirebase(number);
                        sb.cancel();
                    }


                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
                    sb.cancel();
                }
            });
            db.isnumberExist(number);



        }
    }

    private void sendtofirebase(final String number) {

        final showbox sb=new showbox(getActivity());
        sb.show("Please Wait...","Sending Code...");
        FireBaseNumber_Authentication auth=new FireBaseNumber_Authentication(getActivity(), new listener_gernal() {
            @Override
            public void onSuccess(Object object) {

                if (object!=null){

                    String code=object.toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("number",number);
                    bundle.putString("code",code);
                    bundle.putString("type",type);

                    VerifyNumberFragment vrfy=new VerifyNumberFragment();
                    vrfy.setArguments(bundle);

                    sb.cancel();
                    android.support.v4.app.FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.mobileverification_frame,vrfy);
                    fragmentTransaction.commit();

                    sb.cancel();
                }

                sb.cancel();

            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
                sb.cancel();

            }
        });
        auth.send_code(number);
    }

}
