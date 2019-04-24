package com.example.tt.angelichand;


import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllCategoryFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout_all;
    ListView listView_all;
    TextView nopost_tv;

    public AllCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_all_category, container, false);

        swipeRefreshLayout_all=view.findViewById(R.id.swipeToRefresh_all);
        listView_all=view.findViewById(R.id.listview_all);
        nopost_tv=view.findViewById(R.id.nopost_textview);

        nopost_tv.setVisibility(View.INVISIBLE);
        dbhandler_post db=new dbhandler_post(getActivity(), new listener_for_post_receiver() {
            @Override
            public void onSuccess(String code, post_receiver object) {
                if (code.equals("true")){
                    nopost_tv.setVisibility(View.INVISIBLE);
                    listView_all.setAdapter(object);
                }
                else if (code.equals("false")){
                    nopost_tv.setVisibility(View.VISIBLE);
                    listView_all.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Exception e) {


                Toast.makeText(getActivity(),"Server Error Occured"+e,Toast.LENGTH_LONG).show();
            }
        });
        db.receive_post("show","all");

        swipeRefreshLayout_all.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                dbhandler_post db=new dbhandler_post(getActivity(), new listener_for_post_receiver() {
                    @Override
                    public void onSuccess(String code, post_receiver object) {
                        if (code.equals("true")){
                            nopost_tv.setVisibility(View.INVISIBLE);
                            listView_all.setAdapter(object);
                        }
                        else if (code.equals("false")){
                            nopost_tv.setVisibility(View.VISIBLE);
                            listView_all.setVisibility(View.INVISIBLE);
                        }
                        swipeRefreshLayout_all.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Exception e) {

                        Toast.makeText(getActivity(),"Server Error Occured"+e,Toast.LENGTH_LONG).show();
                        swipeRefreshLayout_all.setRefreshing(false);
                    }

                });
                db.receive_post("show","all");
            }
        });

        return view;
    }

}
