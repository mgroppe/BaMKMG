package com.bamgmk.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFrag extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    public GoogleMap mMap;
    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public MapFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFrag newInstance(String param1, String param2) {

        MapFrag fragment = new MapFrag();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        //getActivity().setContentView(R.layout.Fragment_map);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap=googleMap;
        googleMap.setOnMarkerClickListener(new OnMarkerClickListener(){
            public boolean onMarkerClick (Marker arg0){
                Gson gson = new GsonBuilder().create();
                CharData cd = new CharData();
                cd.addChar(1,1,3,40,5,false,0);
                cd.addChar(1,1,3,40,5,false,0);
                cd.addChar(5,1,5,30,8,false,1);
                cd.addChar(3,1,3,30,6,true,2);
                cd.addChar(3,1,3,30,6,true,2);
                cd.addChar(6,5,2,20,5,true,3);
                Log.d("json",gson.toJson(cd));
                Intent intent = new Intent(getActivity(), UnityPlayerActivity.class);
                intent.putExtra("charData",gson.toJson(cd));
                startActivity(intent);
                return true;
            }
        });
        initMarker(0, new LatLng(50.3511528, 7.5951959),"w/e");
    }

    public Marker initMarker(float colorDouble, LatLng position, String name) {
        Marker marker = this.mMap.addMarker(new MarkerOptions()
                .position(position)
                .title(name)
                .draggable(true));
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(colorDouble));
        return marker;
    }
}