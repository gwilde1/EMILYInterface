package android.app.com.emilyrobot;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by vinay on 4/17/17.
 */

public class FragmentMaps extends Fragment implements OnMapReadyCallback {

    static GoogleMap mMap;
    public static ArrayList<LatLng> coordinations=new ArrayList<>();
    public static ArrayList<LatLng> waypoints=new ArrayList<>();
    static Random rand=new Random();
    static Handler mHandler=new Handler();
    static ArrayList<Polyline> polylines=new ArrayList<>();
    CircleOptions circle=new CircleOptions();;
    static Circle circleList;
    static MarkerOptions mEmily=new MarkerOptions();
    static Location L1=new Location("starting");
    static Location L2=new Location("destination");
    static int bearing;
    Marker HomeLocation;
    boolean animateToHome = true;

    static int zoom = 0;

    public FragmentMaps()
    {
    }

    public static FragmentMaps newInstance() {
        FragmentMaps fragment = new FragmentMaps();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    ///FOR update track and waypoint every second
    public Runnable mTask =new Runnable() {
        @Override

        public void run() {
            try{
                //drawCircle(waypoints);
                if(Settings.is_connected){
                    //HomeLocation.remove();

                    //HomeLocation.setPosition(Mydata.getEmilyHomeLocation());


                    mMap.clear();
                    //mMap.addMarker(new MarkerOptions().position(Mydata.getEmilyHomeLocation()).title("HOME"));
                    if(Mydata.getEmilyLocation().latitude !=0 || Mydata.getEmilyLocation().longitude !=0)
                        coordinations.add(Mydata.getEmilyLocation());
                    //if(animateToHome){
                        //animateToHome = false;

                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Mydata.getEmilyLocation(),zoom));
                    //}
                    if(coordinations.size() > 1)
                        mEmily.position(coordinations.get(coordinations.size()-1));
                    if(coordinations.size()>=2){
                        L1.setLatitude(coordinations.get(coordinations.size()-2).latitude);
                        L1.setLongitude(coordinations.get(coordinations.size()-2).longitude);
                        L2.setLatitude(coordinations.get(coordinations.size()-1).latitude);
                        L2.setLongitude(coordinations.get(coordinations.size()-1).longitude);
                        bearing= (int) L1.bearingTo(L2);
                        mEmily.rotation(bearing);
                    }
                    if(coordinations.size() > 1) {
                        mMap.addMarker(mEmily.position(coordinations.get(coordinations.size() - 1)));
                        trackingCor(coordinations, mMap);
                    }
                    //
                }else{
                    animateToHome = true;
                    lineRemove();
                }
            }finally {
                mHandler.postDelayed(mTask, 1000); //repeate every 1 second
            }

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mTask);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return  v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mEmily.icon(BitmapDescriptorFactory.fromResource(R.drawable.arrow));
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
       // HomeLocation.setPosition(Mydata.getEmilyHomeLocation());
       // HomeLocation.setTitle("Home");
        //HomeLocation.
        HomeLocation = mMap.addMarker(new MarkerOptions().position(Mydata.getEmilyHomeLocation()).title("HOME"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Mydata.getEmilyLocation(),0));
        zoom = 19;
        mTask.run();

    }

    //Draw Waypoint coordination
    public void drawCircle(ArrayList<LatLng> cor){
        circleRemove();
        for(int i=0;i<cor.size();i++){
            circleList=mMap.addCircle(circle.center(cor.get(i)).radius(5).strokeColor(Color.RED).fillColor(Color.RED));
        }
    }

    //Draw line to track EMILY
    static void trackingCor(ArrayList<LatLng> cor, GoogleMap map){
        //lineRemove();
        if(cor.size()>=2) {
            for(int i=0;i<cor.size()-1;i++) {
                polylines.add(map.addPolyline(new PolylineOptions().add(cor.get(i),cor.get(i+1)).width(10).color(Color.WHITE).geodesic(true)));
            }

        }
    }

    //remove all the line on the map
    public static void lineRemove(){
        for(Polyline line:polylines){
            line.remove();
        }
        polylines.clear();
    }

    public static void circleRemove(){
        if(circleList != null) circleList.remove();
    }



}
