package com.example.now.UI.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.now.R;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StoreLocationFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener,
        LocationListener {

    ArrayList<Uri> uriArrayList;
    private LinearLayout back_button;
    private ImageView img_add;
    private TextView tv_next;
    private EditText et_brand;
    private double lat = 0;
    private double lng = 0;
    private boolean isDrag = false;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;

    private String brand = "";
    private String product = "";
    private String model_number = "";
    private String offerName = "";
    private String web_link = "";
    private String youtube_link = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_location, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {

                LatLng loc = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                lat = loc.latitude;
                lng = loc.longitude;

                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }

                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                Address address = null;
                try {
                    List<Address> addresses = geocoder.getFromLocation(loc.latitude, loc.longitude, 1);
                    address = addresses.get(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Place current location marker
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(loc);
                markerOptions.title(address.getAddressLine(0));
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
                mCurrLocationMarker.setDraggable(true);

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(mCurrLocationMarker.getPosition().latitude, mCurrLocationMarker.getPosition().longitude))      // Sets the center of the map to location user
                        .zoom(14)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();

                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.setOnMarkerDragListener(this);
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setOnMapClickListener(this);
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
//        mLastLocation = location;
//
//        if(!isDrag){
//            if (mCurrLocationMarker != null) {
//                mCurrLocationMarker.remove();
//            }
//
//            //Place current location marker
//            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//            MarkerOptions markerOptions = new MarkerOptions();
//            markerOptions.position(latLng);
//            markerOptions.title("Current Position");
//            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//            mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
//            mCurrLocationMarker.setDraggable(true);
//            //move map camera
//            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
//        }

    }


    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                requestPermissions(
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

        isDrag = true;
        LatLng position = marker.getPosition();

        Log.d("store", String.format("Drag from %f:%f",
                position.latitude,
                position.longitude));
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        LatLng position = marker.getPosition();

        Log.d("store",
                String.format("Dragging to %f:%f", position.latitude,
                        position.longitude));
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        LatLng position = marker.getPosition();

        Log.d("store", String.format("Dragged to %f:%f",
                position.latitude,
                position.longitude));

        lat = marker.getPosition().latitude;
        lng = marker.getPosition().longitude;

        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }


        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        Address address = null;
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            address = addresses.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Place current location marker
        LatLng latLng = new LatLng(lat, lng);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(address.getAddressLine(0));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
        mCurrLocationMarker.setDraggable(true);

        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    private void initViews(View view) {
        if (getArguments() != null) {
            uriArrayList = getArguments().getParcelableArrayList("images");
            brand = getArguments().getString("brand");
            product = getArguments().getString("product");
            model_number = getArguments().getString("model_number");
            offerName = getArguments().getString("offerName");
            web_link = getArguments().getString("web_link");
            youtube_link = getArguments().getString("youtube_link");
        }
        et_brand = view.findViewById(R.id.et_brand);
        back_button = view.findViewById(R.id.back_button);
        back_button.setOnClickListener(this);
        img_add = view.findViewById(R.id.img_add);
        img_add.setVisibility(View.GONE);
        tv_next = view.findViewById(R.id.tv_next);
        tv_next.setOnClickListener(this);
        tv_next.setVisibility(View.VISIBLE);
    }

    private void gotoMoreDetailsFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("images", uriArrayList);
        bundle.putString("product",product);
        bundle.putString("brand",brand);
        bundle.putString("model_number",model_number);
        bundle.putString("offerName",offerName);
        bundle.putString("web_link",web_link);
        bundle.putString("youtube_link",youtube_link);
        bundle.putDouble("lat",lat);
        bundle.putDouble("long",lng);
        MoreDetailsFragment moreDetailsFragment = new MoreDetailsFragment();
        moreDetailsFragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment, moreDetailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.back_button:
                getFragmentManager().popBackStack();
                break;
            case R.id.tv_next:
                gotoMoreDetailsFragment();

                break;
        }
    }

    @Override
    public void onMapClick(LatLng latLong) {


        lat = latLong.latitude;
        lng = latLong.longitude;

        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }


        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        Address address = null;
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            address = addresses.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Place current location marker
        LatLng latLng = new LatLng(lat, lng);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(address.getAddressLine(0));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
        mCurrLocationMarker.setDraggable(true);

        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
    }
}