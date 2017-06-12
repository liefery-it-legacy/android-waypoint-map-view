package com.liefery.android.waypoint_map_view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.*;
import com.google.maps.android.PolyUtil;
import com.liefery.android.stop_badge.StopBadge;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public abstract class WaypointMap {
    /**
     * Wrapped GoogleMap instance
     */
    protected final GoogleMap map;

    /**
     * Size of the map marker bitmaps in pixels
     */
    private int markerSize;

    private int routeColorActual;

    private int routeColorBorderActual;

    private int routeColorPlanned;

    private int routeColorBorderPlanned;

    private int routeWidth;

    private int routeWidthBorder;

    /**
     * When focusing the map markers this is the padding left around them
     */
    private int zoomPadding;

    /**
     * List of markers added to the map
     */
    protected ArrayList<MarkerOptions> markers = new ArrayList<>();

    public WaypointMap( Context context, GoogleMap map ) {
        this.map = map;
        initialize( context );
        initialize();
    }

    protected void initialize( Context context ) {
        Resources resources = context.getResources();

        markerSize = resources
                        .getDimensionPixelSize( R.dimen.waypoint_map_view_marker_size );
        routeColorActual = ResourcesCompat.getColor(
            resources,
            R.color.waypoint_map_view_route_color_actual,
            context.getTheme() );
        routeColorBorderActual = ResourcesCompat.getColor(
            resources,
            R.color.waypoint_map_view_route_color_border_actual,
            context.getTheme() );
        routeColorPlanned = ResourcesCompat.getColor(
            resources,
            R.color.waypoint_map_view_route_color_planned,
            context.getTheme() );
        routeColorBorderPlanned = ResourcesCompat.getColor(
            resources,
            R.color.waypoint_map_view_route_color_border_planned,
            context.getTheme() );
        routeWidth = resources
                        .getDimensionPixelOffset( R.dimen.waypoint_map_view_route_width );
        routeWidthBorder = resources
                        .getDimensionPixelOffset( R.dimen.waypoint_map_view_route_width_border );
        zoomPadding = resources
                        .getDimensionPixelOffset( R.dimen.waypoint_map_view_zoom_padding );

        if ( isLocationPermissionGranted( context ) )
            map.setMyLocationEnabled( true );
    }

    abstract protected void initialize();

    private boolean isLocationPermissionGranted( Context context ) {
        int coarse = ContextCompat.checkSelfPermission(
            context,
            ACCESS_COARSE_LOCATION );
        int fine = ContextCompat.checkSelfPermission(
            context,
            ACCESS_FINE_LOCATION );

        return coarse == PERMISSION_GRANTED || fine == PERMISSION_GRANTED;
    }

    public void clear() {
        map.clear();
        initialize();
    }

    public GoogleMap getGoogleMap() {
        return map;
    }

    public int getMarkerSize() {
        return markerSize;
    }

    public void setMarkerSize( int size ) {
        this.markerSize = size;
    }

    public int getRouteColorActual() {
        return routeColorActual;
    }

    public void setRouteColorActual( @ColorInt int color ) {
        this.routeColorActual = color;
    }

    public int getRouteColorBorderActual() {
        return routeColorBorderActual;
    }

    public void setRouteColorBorderActual( @ColorInt int color ) {
        this.routeColorBorderActual = color;
    }

    public int getRouteColorPlanned() {
        return routeColorPlanned;
    }

    public void setRouteColorPlanned( @ColorInt int color ) {
        this.routeColorPlanned = color;
    }

    public int getRouteColorBorderPlanned() {
        return routeColorBorderPlanned;
    }

    public void setRouteColorBorderPlanned( @ColorInt int color ) {
        this.routeColorBorderPlanned = color;
    }

    public int getRouteWidth() {
        return routeWidth;
    }

    public void setRouteWidth( int width ) {
        this.routeWidth = width;
    }

    public int getRouteWidthBorder() {
        return routeWidthBorder;
    }

    public void setRouteWidthBorder( int width ) {
        this.routeWidthBorder = width;
    }

    public int getZoomPadding() {
        return zoomPadding;
    }

    public void setZoomPadding( int zoomPadding ) {
        this.zoomPadding = zoomPadding;
    }

    public void addWaypoint( StopBadge badge, LatLng position ) {
        Bitmap bitmap = badge.export( getMarkerSize() );
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap( bitmap );

        MarkerOptions options = new MarkerOptions();
        options.anchor( .5f, .5f );
        options.icon( icon );
        options.position( position );

        markers.add( options );
        map.addMarker( options );
    }

    public void addRouteActual( String polyline ) {
        addRoute( routeColorActual, routeColorBorderActual, polyline );
    }

    public void addRoutePlanned( String polyline ) {
        addRoute( routeColorPlanned, routeColorBorderPlanned, polyline );
    }

    private void addRoute(
        @ColorInt int colorRoute,
        @ColorInt int colorRouteBorder,
        String polyline ) {
        PolylineOptions routeBorderOptions = new PolylineOptions()
                        .width( routeWidth + routeWidthBorder )
                        .color( colorRouteBorder ).clickable( false );

        PolylineOptions routeOptions = new PolylineOptions().width( routeWidth )
                        .color( colorRoute ).clickable( false );

        List<LatLng> positions = PolyUtil.decode( polyline );

        for ( LatLng position : positions ) {
            routeBorderOptions.add( position );
            routeOptions.add( position );
        }

        map.addPolyline( routeBorderOptions );
        map.addPolyline( routeOptions );
    }
}