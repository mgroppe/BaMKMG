package com.bamgmk.demo.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bamgmk.demo.MapReadyListener;
import com.bamgmk.demo.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * { MapFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFrag extends Fragment implements OnMapReadyCallback   {

    public GoogleMap mMap;
    public MapReadyListener callback;


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
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (MapReadyListener) getActivity();
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
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setZoomGesturesEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker) {

                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                // Inflate custom layout
                View v = getActivity().getLayoutInflater().inflate(R.layout.info_window, null);

                // Set desired height and width
                TextView title = (TextView) v.findViewById(R.id.markerTitle);
                TextView startFight = (TextView) v.findViewById(R.id.fightStartView);
                title.setText(marker.getTitle());

                if(marker.getTitle().compareTo("Essling") == 0)
                    startFight.setText("");

                if(marker.getSnippet().compareTo("hard")==0){
                    v.setBackgroundColor(Color.RED);

                    startFight.setTextColor(Color.WHITE);
                    title.setTextColor(Color.WHITE);
                }
                if(marker.getSnippet().compareTo("easy")==0){
                    v.setBackgroundColor(Color.GREEN);
                }
                if(marker.getSnippet().compareTo("normal")==0){
                    v.setBackgroundColor(Color.argb(255,255,165,0));
                    startFight.setTextColor(Color.WHITE);
                    title.setTextColor(Color.WHITE);
                }




                return v;
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return true;
            }
        });

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                                   @Override
                                                   public void onInfoWindowClick(Marker marker) {
                                                       callback.fightIfCloseEnough(marker);
                                                   }
                                               }
        );
        callback.mapReady(mMap);
    }

    public Marker initMarker(float colorDouble, LatLng position, String name) {
        Marker marker = this.mMap.addMarker(new MarkerOptions()
                .position(position)
                .title(name)
                );
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(colorDouble));
        return marker;
    }


}