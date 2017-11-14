package com.liefery.android.waypoint_map_view.sample;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;
import com.liefery.android.icon_badge.IconBadge;
import com.liefery.android.waypoint_map_view.FixedWaypointMap;
import com.liefery.android.waypoint_map_view.FreeWaypointMap;
import com.liefery.android.waypoint_map_view.WaypointMap;

import java.util.ArrayList;

public class Activity extends android.app.Activity {
    private MapView map1;

    private MapView map2;

    @Override
    public void onCreate( Bundle state ) {
        super.onCreate( state );

        setContentView( R.layout.main );

        map1 = findViewById( R.id.map1 );
        map2 = findViewById( R.id.map2 );

        map1.onCreate( state );
        map2.onCreate( state );

        map1.getMapAsync( new OnMap1ReadyCallback( this ) );
        map2.getMapAsync( new OnMap2ReadyCallback( this ) );
    }

    @Override
    protected void onStart() {
        super.onStart();

        map1.onStart();
        map2.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        map1.onResume();
        map2.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();

        map1.onStop();
        map2.onStop();
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        super.onSaveInstanceState( outState );

        map1.onSaveInstanceState( outState );
        map2.onSaveInstanceState( outState );
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        map1.onLowMemory();
        map2.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        map1.onDestroy();
        map2.onDestroy();
    }

    static void configureMap( Context context, WaypointMap map ) {
        LatLng position1 = new LatLng( 52.4953633, 13.3495338 );
        LatLng position2 = new LatLng( 52.4963643, 13.3485328 );

        IconBadge badge1 = new IconBadge( context );
        badge1.setNumber( 1 );
        IconBadge badge2 = new IconBadge( context );
        badge2.setNumber( 2 );

        map.addWaypoint( badge1, position1 );
        map.addWaypoint( badge2, position2 );

        ArrayList<LatLng> planned = new ArrayList<>( 3 );
        planned.add( new LatLng( 52.49625750906928, 13.348448602482677 ) );
        planned.add( new LatLng( 52.49643060295327, 13.349650232121348 ) );
        planned.add( new LatLng( 52.495267921517275, 13.349698511883616 ) );
        map.addRoutePlanned( PolyUtil.encode( planned ) );

        ArrayList<LatLng> actual = new ArrayList<>( 7 );
        actual.add( new LatLng( 52.49528098552441, 13.349714605137706 ) );
        actual.add( new LatLng( 52.49463431251219, 13.349741427227855 ) );
        actual.add( new LatLng( 52.49448080792577, 13.347928253933787 ) );
        actual.add( new LatLng( 52.494516734579136, 13.346082894131541 ) );
        actual.add( new LatLng( 52.49506542891036, 13.346243826672435 ) );
        actual.add( new LatLng( 52.495940712837644, 13.346254555508494 ) );
        actual.add( new LatLng( 52.49627057278243, 13.348545162007213 ) );
        map.addRouteActual( PolyUtil.encode( actual ) );
    }

    static class OnMap1ReadyCallback implements OnMapReadyCallback {
        Context context;

        OnMap1ReadyCallback( Context context ) {
            this.context = context;
        }

        @Override
        public void onMapReady( GoogleMap googleMap ) {
            final FreeWaypointMap map = new FreeWaypointMap( context, googleMap );
            configureMap( context, map );

            new Handler().post( new Runnable() {
                @Override
                public void run() {
                    CameraUpdate update = map.adjustZoomToMarkers();
                    map.getGoogleMap().moveCamera( update );
                }
            } );
        }
    }

    static class OnMap2ReadyCallback implements OnMapReadyCallback {
        Context context;

        OnMap2ReadyCallback( Context context ) {
            this.context = context;
        }

        @Override
        public void onMapReady( GoogleMap googleMap ) {
            LatLng focus = new LatLng( 52.4963643, 13.3485328 );
            final WaypointMap map = new FixedWaypointMap( context, googleMap );
            configureMap( context, map );
        }
    }
}