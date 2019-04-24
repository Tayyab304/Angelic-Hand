package com.example.tt.angelichand;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.Toast;

import com.github.leonardoxh.fakesearchview.FakeSearchView;


/**
 * A simple {@link Fragment} subclass.
 */
public class VolunteerFragment extends Fragment implements FakeSearchView.OnSearchListener {


    ListView listView_vol;
    Toolbar toolbar;
    String name,from,req;
    SwipeRefreshLayout swipeRefreshLayout_vol;
    public VolunteerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_volunteer, container, false);

        toolbar=view.findViewById(R.id.app_bar_volunteer);
        listView_vol=view.findViewById(R.id.listview_vol);
        swipeRefreshLayout_vol=view.findViewById(R.id.swipeToRefresh_vol);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);



        Bundle bundle = this.getArguments();
        from=bundle.getString("from");

        if(from.equals("my_vol") || from.equals("req_vol")){
            name=bundle.getString("name");

            if (from.equals("my_vol")) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(name + " Volunteers");
                req = "awe";
            }


            else {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Request"+name + " Volunteers");
                req="req";
            }

        }

        else if (from.equals("all_vol")){
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Volunteers");
            name="all";
            req="awe";

        }



        dbhandler_volunteer db=new dbhandler_volunteer(getActivity(), new listener_for_volunteers() {
            @Override
            public void onSuccess(String code, volunteer object) {
                if (code.equals("true")){
                    listView_vol.setAdapter(object);
                }
                else {
                    Toast.makeText(getActivity(),"masla",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onSuccess(String code, requested_volunteer object) {

                listView_vol.setAdapter(object);
            }

            @Override
            public void onFailure(Exception e) {

                Toast.makeText(getActivity(),"Server Error Occured"+e.toString(),Toast.LENGTH_LONG).show();
            }
        });
        db.receive_volunteer("show",name,req);


        swipeRefreshLayout_vol.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dbhandler_volunteer db=new dbhandler_volunteer(getActivity(), new listener_for_volunteers() {
                    @Override
                    public void onSuccess(String code, volunteer object) {
                        if (code.equals("true")){
                            listView_vol.setAdapter(object);
                        }
                        else {
                            Toast.makeText(getActivity(),"masla",Toast.LENGTH_LONG).show();
                        }

                        swipeRefreshLayout_vol.setRefreshing(false);
                    }

                    @Override
                    public void onSuccess(String code, requested_volunteer object) {

                        listView_vol.setAdapter(object);
                    }

                    @Override
                    public void onFailure(Exception e) {

                        Toast.makeText(getActivity(),"Server Error Occured"+e.toString(),Toast.LENGTH_LONG).show();
                        swipeRefreshLayout_vol.setRefreshing(false);
                    }
                });
                db.receive_volunteer("show",name,req);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(),MenuActivity.class);
                startActivity(intent);
                getActivity().finish();
                //getActivity().onBackPressed();

            }
        });



        return view;
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       // super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.fake_search);
        FakeSearchView fakeSearchView = (FakeSearchView) MenuItemCompat.getActionView(menuItem);
        fakeSearchView.setOnSearchListener(this);

    }


    @Override
    public void onSearch(FakeSearchView fakeSearchView, CharSequence constraint) {

        Toast.makeText(getActivity(),"input :"+constraint.toString(),Toast.LENGTH_LONG).show();
        ((Filterable)listView_vol.getAdapter()).getFilter().filter(constraint);
    }

    @Override
    public void onSearchHint(FakeSearchView fakeSearchView, CharSequence constraint) {

    }
}
