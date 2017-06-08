package com.liefery.android.waypoint_map_view;

import android.content.Context;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class FixedWaypointMap extends WaypointMap {
    public FixedWaypointMap( Context context, GoogleMap map, LatLng focus ) {
        super( context, map );
        initialize( context );
        setUpFocus( focus );
    }

    @Override
    protected void initialize( Context context ) {
        super.initialize( context );

        UiSettings settings = map.getUiSettings();

        settings.setAllGesturesEnabled( false );
        settings.setCompassEnabled( false );
        settings.setIndoorLevelPickerEnabled( false );
        settings.setMapToolbarEnabled( false );
        settings.setMyLocationButtonEnabled( false );
        map.setOnMarkerClickListener( new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick( Marker marker ) {
                return true;
            }
        } );
    }

    private void setUpFocus( LatLng focus ) {
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom( focus, 16 );
        map.moveCamera( update );
    }
}