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
public class MedicineFragment extends Fragment {

    SwipeRefreshLayout swipeRefreshLayout_medi;
    ListView listView_medi;
    TextView nopost_medi;


    public MedicineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_medicine, container, false);


        swipeRefreshLayout_medi=view.findViewById(R.id.swipeToRefresh_medi);
        listView_medi=view.findViewById(R.id.listview_medi);
        nopost_medi=view.findViewById(R.id.nopost_medi_textview);

        nopost_medi.setVisibility(View.INVISIBLE);
        dbhandler_post db=new dbhandler_post(getActivity(), new listener_for_post_receiver() {
            @Override
            public void onSuccess(String code, post_receiver object) {
                if (code.equals("true")){
                    nopost_medi.setVisibility(View.INVISIBLE);
                    listView_medi.setAdapter(object);
                }
                else if (code.equals("false")){
                    nopost_medi.setVisibility(View.VISIBLE);
                    listView_medi.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Exception e) {

                if (e==null){
                    Toast.makeText(getActivity(),"Server Error Occured",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getActivity(),"Server Error Occured No"+e,Toast.LENGTH_LONG).show();
                }


            }
        });
        db.receive_post("show","Medicine");

        swipeRefreshLayout_medi.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                dbhandler_post db=new dbhandler_post(getActivity(), new listener_for_post_receiver() {
                    @Override
                    public void onSuccess(String code, post_receiver object) {
                        if (code.equals("true")){
                            nopost_medi.setVisibility(View.INVISIBLE);
                            listView_medi.setAdapter(object);
                        }
                        else if (code.equals("false")){
                            nopost_medi.setVisibility(View.VISIBLE);
                            listView_medi.setVisibility(View.INVISIBLE);
                        }
                        swipeRefreshLayout_medi.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Exception e) {

                        Toast.makeText(getActivity(),"Server Error Occured"+e,Toast.LENGTH_LONG).show();
                        swipeRefreshLayout_medi.setRefreshing(false);
                    }

                });
                db.receive_post("show","Medicine");
            }
        });

        return view;
    }

}
