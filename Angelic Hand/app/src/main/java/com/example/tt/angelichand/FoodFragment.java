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
public class FoodFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout_food;
    ListView listView_food;
    TextView nopost_food_tv;

    public FoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_food, container, false);

        swipeRefreshLayout_food=view.findViewById(R.id.swipeToRefresh_food);
        listView_food=view.findViewById(R.id.listview_food);
        nopost_food_tv=view.findViewById(R.id.nopost_food_textview);

        nopost_food_tv.setVisibility(View.INVISIBLE);
        dbhandler_post db=new dbhandler_post(getActivity(), new listener_for_post_receiver() {
            @Override
            public void onSuccess(String code, post_receiver object) {
                if (code.equals("true")){
                    nopost_food_tv.setVisibility(View.INVISIBLE);
                    listView_food.setAdapter(object);
                }
                else if (code.equals("false")){
                    nopost_food_tv.setVisibility(View.VISIBLE);
                    listView_food.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Exception e) {

                Toast.makeText(getActivity(),"Server Error Occured"+e,Toast.LENGTH_LONG).show();
            }
        });
        db.receive_post("show","Food");

        swipeRefreshLayout_food.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                dbhandler_post db=new dbhandler_post(getActivity(), new listener_for_post_receiver() {
                    @Override
                    public void onSuccess(String code, post_receiver object) {
                        if (code.equals("true")){
                            nopost_food_tv.setVisibility(View.INVISIBLE);
                            listView_food.setAdapter(object);
                        }
                        else if (code.equals("false")){
                            nopost_food_tv.setVisibility(View.VISIBLE);
                            listView_food.setVisibility(View.INVISIBLE);
                        }
                        swipeRefreshLayout_food.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Exception e) {

                        Toast.makeText(getActivity(),"Server Error Occured"+e,Toast.LENGTH_LONG).show();
                        swipeRefreshLayout_food.setRefreshing(false);
                    }

                });
                db.receive_post("show","Food");
            }
        });

       return view;
    }

}
