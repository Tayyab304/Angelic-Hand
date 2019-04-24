package com.example.tt.angelichand;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class TypeOfUserFragment extends Fragment {

    String user[]={"Select---","Organization","Volunteer","Donor"};
    Spinner type_f_user;
    ArrayAdapter arrayAdapter;

    public TypeOfUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_type_of_user, container, false);

        type_f_user=view.findViewById(R.id.spiner_type_of_user);

        arrayAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,user);
        type_f_user.setAdapter(arrayAdapter);

        type_f_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seledted_user=type_f_user.getSelectedItem().toString();
                if (seledted_user.equals("Volunteer")){

                    Bundle bundle=new Bundle();
                    bundle.putString("type","volunteer");

                    InputNumberFragment vol=new InputNumberFragment();
                    vol.setArguments(bundle);

                    android.support.v4.app.FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.mobileverification_frame,vol)
                            .addToBackStack(null);
                    fragmentTransaction.commit();

                    type_f_user.setSelection(0);
                }

                if (seledted_user.equals("Organization")){



                    Intent intent=new Intent(getActivity(),RegistrationActivity.class);
                    intent.putExtra(RegistrationActivity.FLAG_REGISTRATION,"organization");
                    startActivity(intent);
                    getActivity().finish();
//                    android.support.v4.app.FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.mobileverification_frame,new OrganizationRegistrationFragment())
//                            .addToBackStack(null);
//                    fragmentTransaction.commit();
                    type_f_user.setSelection(0);
                }

                if (seledted_user.equals("Donor")){

                    Bundle bundle=new Bundle();
                    bundle.putString("type","donor");

                    InputNumberFragment vol=new InputNumberFragment();
                    vol.setArguments(bundle);
                    android.support.v4.app.FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.mobileverification_frame,vol)
                            .addToBackStack(null);
                    fragmentTransaction.commit();
                    type_f_user.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                //type_f_user.setSelection(0);
            }
        });
        return view;
    }

}
