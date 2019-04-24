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
public class ClothFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout_cloth;
    ListView listView_cloth;
    TextView nopost_cloth_tv;

    public ClothFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_cloth, container, false);
        swipeRefreshLayout_cloth=view.findViewById(R.id.swipeToRefresh_cloth);
        listView_cloth=view.findViewById(R.id.listview_cloth);
        nopost_cloth_tv=view.findViewById(R.id.nopost_cloth_textview);

        nopost_cloth_tv.setVisibility(View.INVISIBLE);
        dbhandler_post db=new dbhandler_post(getActivity(), new listener_for_post_receiver() {
            @Override
            public void onSuccess(String code, post_receiver object) {
                if (code.equals("true")){
                    nopost_cloth_tv.setVisibility(View.INVISIBLE);
                    listView_cloth.setAdapter(object);
                }
                else if (code.equals("false")){
                    nopost_cloth_tv.setVisibility(View.VISIBLE);
                    listView_cloth.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Exception e) {

                Toast.makeText(getActivity(),"Server Error Occured"+e,Toast.LENGTH_LONG).show();
            }
        });
        db.receive_post("show","Cloth");

        swipeRefreshLayout_cloth.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                dbhandler_post db=new dbhandler_post(getActivity(), new listener_for_post_receiver() {
                    @Override
                    public void onSuccess(String code, post_receiver object) {
                        if (code.equals("true")){
                            nopost_cloth_tv.setVisibility(View.INVISIBLE);
                            listView_cloth.setAdapter(object);
                        }
                        else if (code.equals("false")){
                            nopost_cloth_tv.setVisibility(View.VISIBLE);
                            listView_cloth.setVisibility(View.INVISIBLE);
                        }
                        swipeRefreshLayout_cloth.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Exception e) {

                        Toast.makeText(getActivity(),"Server Error Occured"+e,Toast.LENGTH_LONG).show();
                        swipeRefreshLayout_cloth.setRefreshing(false);
                    }

                });
                db.receive_post("show","Cloth");
            }
        });

        return view;
    }

}
