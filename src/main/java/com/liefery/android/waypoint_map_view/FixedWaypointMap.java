package com.liefery.android.waypoint_map_view;

import android.content.Context;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class FixedWaypointMap extends WaypointMap {
    public FixedWaypointMap( Context context, GoogleMap map, LatLng focus ) {
        super( context, map );
        setUpFocus( focus );
    }

    @Override
    protected void initialize() {
        setOnMarkerClickListener( null );

        UiSettings settings = map.getUiSettings();

        settings.setAllGesturesEnabled( false );
        settings.setCompassEnabled( false );
        settings.setIndoorLevelPickerEnabled( false );
        settings.setMapToolbarEnabled( false );
        settings.setMyLocationButtonEnabled( false );
    }

    private void setUpFocus( LatLng focus ) {
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom( focus, 16 );
        map.moveCamera( update );
    }

    public void setOnMarkerClickListener( final OnMarkerClickListener listener ) {
        map.setOnMarkerClickListener( new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick( Marker marker ) {
                if ( listener != null )
                    listener.onMarkerClick( marker );
                return true;
            }
        } );
    }

    public interface OnMarkerClickListener {
        void onMarkerClick( Marker listener );
    }
}