package com.liefery.android.waypoint_map_view;

import android.content.Context;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class FixedWaypointMap extends WaypointMap {
    private final int defaultFocusZoom;

    public FixedWaypointMap( Context context, GoogleMap map ) {
        super( context, map );

        this.defaultFocusZoom = context.getResources().getInteger(
            R.integer.fixed_waypoint_map_focus_zoom );
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

    public void setFocus( LatLng focus, int zoom ) {
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom( focus, zoom );
        map.moveCamera( update );
    }

    public void setFocus( LatLng focus ) {
        setFocus( focus, defaultFocusZoom );
    }

    public void setOnMarkerClickListener( final OnMarkerClickListener listener ) {
        // Custom marker to prevent the map focus from moving to the clicked
        // marker
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