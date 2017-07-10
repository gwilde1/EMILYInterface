package android.app.com.emilyrobot;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by vinay on 4/17/17.
 */


public class FragmentDetails extends Fragment {

    private RecyclerView recyclerView;
    private FragmentAdapter currentstatusAdapter;
    Handler update_handler;


    public FragmentDetails()
    {

    }


    public static FragmentDetails newInstance() {
        FragmentDetails fragment = new FragmentDetails();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /*public void updatedData(ArrayList<Model> new_data){


    }
*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_details, container, false);
        return  rootview;


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.itemslist);

        currentstatusAdapter = new FragmentAdapter(getActivity(),Mydata.generateData());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(currentstatusAdapter);

        update_handler = new Handler();

        update_handler.postDelayed(new Runnable(){
            public void run(){
                currentstatusAdapter.notifyDataSetChanged();
                //do something
                update_handler.postDelayed(this, Settings.detail_refresh_rate);
            }
        }, Settings.detail_refresh_rate);
    }
}
