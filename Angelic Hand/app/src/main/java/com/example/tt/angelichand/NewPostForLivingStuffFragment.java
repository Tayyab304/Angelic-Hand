package com.example.tt.angelichand;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewPostForLivingStuffFragment extends Fragment {


    public EditText item;

    public NewPostForLivingStuffFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_new_post_for_living_stuff, container, false);


        return view;
    }

    public void receive(String userid,String type){


    }

}
