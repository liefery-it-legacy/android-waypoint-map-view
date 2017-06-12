package com.liefery.android.waypoint_map_view;

import android.content.Context;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class FreeWaypointMap extends WaypointMap {
    public FreeWaypointMap( Context context, GoogleMap map ) {
        super( context, map );
        initialize( context );
    }

    @Override
    protected void initialize() {
        UiSettings settings = map.getUiSettings();

        settings.setAllGesturesEnabled( true );
        settings.setCompassEnabled( true );
        settings.setIndoorLevelPickerEnabled( true );
        settings.setMapToolbarEnabled( true );
        settings.setMyLocationButtonEnabled( true );
    }

    public CameraUpdate adjustZoomToMarkers() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for ( MarkerOptions marker : markers ) {
            builder.include( marker.getPosition() );
        }

        LatLngBounds bounds = builder.build();

        return CameraUpdateFactory.newLatLngBounds( bounds, getZoomPadding() );
    }
}