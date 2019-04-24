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
public class OrganizationRegistrationFragment extends Fragment {

    EditText org_name,org_address,org_phone,org_link,org_no,org_email;
    Button org_next;
    public OrganizationRegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_organization_registration, container, false);

        org_name=view.findViewById(R.id.organization_reg_name_edittext);
        org_address=view.findViewById(R.id.organization_reg_address_edittext);
        org_phone=view.findViewById(R.id.organization_reg_phone_edittext);
        org_link=view.findViewById(R.id.organization_reg_website_edittext);
        org_no=view.findViewById(R.id.organization_reg_organization_no_edittext);
        org_email=view.findViewById(R.id.organization_reg_email_edittext);
        org_next=view.findViewById(R.id.organization_reg_next_button);

        org_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_for_id();
            }
        });

        return view;
    }

    private void send_for_id() {

        checkinternet chi=new checkinternet(getActivity());
        String name,add,phone,link,no,mail;
        name=org_name.getText().toString();
        add=org_address.getText().toString();
        no=org_no.getText().toString();
        phone=org_phone.getText().toString();
        link=org_link.getText().toString();
        mail=org_email.getText().toString();

        if (name.isEmpty()){
            org_name.setError("Name is Required");
            org_name.requestFocus();
            return;
        }
        if (add.isEmpty()){
            org_address.setError("Address is Required");
            org_address.requestFocus();
            return;
        }
        if (no.isEmpty()){
            org_no.setError("Organization No# is Required");
            org_no.requestFocus();
            return;
        }
        if (phone.isEmpty()){
            org_phone.setError("Land Line is Required");
            org_phone.requestFocus();
            return;
        }
        if (mail.isEmpty()){
            org_email.setError("Email is Required");
            org_email.requestFocus();
            return;
        }

        if (!(chi.checknet())){

            Toast.makeText(getActivity(),"Check Your Internet Connenction",Toast.LENGTH_LONG).show();
            return;
        }

        else {

            VolunteerIdFragment vol=new VolunteerIdFragment();
            Bundle bundle=new Bundle();
            bundle.putString(VolunteerIdFragment.FLAG_ID,"organization");
            bundle.putString("org_name",name);
            bundle.putString("org_add",add);
            bundle.putString("org_no",no);
            bundle.putString("org_phone",phone);
            bundle.putString("org_link",link);
            bundle.putString(VolunteerIdFragment.FLAG_ID,"organization");

            vol.setArguments(bundle);
            android.support.v4.app.FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.registration_frame,vol)
                    .addToBackStack(null);
            fragmentTransaction.commit();

        }

    }

}
