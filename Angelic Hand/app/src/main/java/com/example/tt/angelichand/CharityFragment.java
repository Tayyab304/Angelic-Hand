package com.example.tt.angelichand;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CharityFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout_charity;
    ListView listView_charity;
    TextView nopost_charity_tv;

    public CharityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_charity, container, false);

        swipeRefreshLayout_charity=view.findViewById(R.id.swipeToRefresh_charity);
        listView_charity=view.findViewById(R.id.listview_charity);
        nopost_charity_tv=view.findViewById(R.id.nopost_charity_textview);

        nopost_charity_tv.setVisibility(View.INVISIBLE);
        dbhandler_post db=new dbhandler_post(getActivity(), new listener_for_post_receiver() {
            @Override
            public void onSuccess(String code, post_receiver object) {
                if (code.equals("true")){
                    nopost_charity_tv.setVisibility(View.INVISIBLE);
                    listView_charity.setAdapter(object);
                }
                else if (code.equals("false")){
                    nopost_charity_tv.setVisibility(View.VISIBLE);
                    listView_charity.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Exception e) {

                Toast.makeText(getActivity(),"Server Error Occured"+e,Toast.LENGTH_LONG).show();
            }
        });
        db.receive_post("show","Charity");

        swipeRefreshLayout_charity.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                dbhandler_post db=new dbhandler_post(getActivity(), new listener_for_post_receiver() {
                    @Override
                    public void onSuccess(String code, post_receiver object) {
                        if (code.equals("true")){
                            nopost_charity_tv.setVisibility(View.INVISIBLE);
                            listView_charity.setAdapter(object);
                        }
                        else if (code.equals("false")){
                            nopost_charity_tv.setVisibility(View.VISIBLE);
                            listView_charity.setVisibility(View.INVISIBLE);
                        }
                        swipeRefreshLayout_charity.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Exception e) {

                        Toast.makeText(getActivity(),"Server Error Occured"+e,Toast.LENGTH_LONG).show();
                        swipeRefreshLayout_charity.setRefreshing(false);
                    }

                });
                db.receive_post("show","Charity");
            }
        });
        return view;
    }

}
