package com.bamgmk.demo;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by marting on 20.03.2017.
 */

public interface MapReadyListener {
    public void mapReady(GoogleMap map);
    public void fightIfCloseEnough (Marker marker);
}
