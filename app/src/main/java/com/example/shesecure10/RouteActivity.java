package com.example.shesecure10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class RouteActivity extends FragmentActivity implements OnMapReadyCallback, TaskLoadedCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private Button findRouteButton;
    private LatLng currentLocation, destinationLocation;
    private MarkerOptions place1, place2;
    private Button getDirection;
    private Polyline currentPolyline;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        getDirection = findViewById(R.id.find_route_button);

        // Initialize the Places API
        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        PlacesClient placesClient = Places.createClient(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Initialize the AutocompleteSupportFragment
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        if (autocompleteFragment != null) {
            autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME));
            autocompleteFragment.setCountry("BD"); // Set country if needed
            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(@NonNull Place place) {
                    Log.i("RouteActivity", "Place: " + place.getName() + ", " + place.getId());
                    place2 = new MarkerOptions().position(place.getLatLng()).title(place.getName());
                    destinationLocation = place.getLatLng();
                    place2 = new MarkerOptions().position(destinationLocation).title(place.getName());
//
                }

                @Override
                public void onError(@NonNull Status status) {
                    Log.e("RouteActivity", "An error occurred: " + status);
                }
            });
        }

        // Map Fragment initialization
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (place2 != null && currentLocation != null) {
                    new FetchURL(RouteActivity.this).execute(getUrl(currentLocation, destinationLocation, "walking"), "walking");
                    mMap.addMarker(place2);
                    //moving the camera in between src and destination
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng((currentLocation.latitude+destinationLocation.latitude)/2 , (currentLocation.longitude+destinationLocation.longitude)/2), 12));

                } else {
                    Toast.makeText(RouteActivity.this, "Please enter a destination and ensure location is available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setTrafficEnabled(true);
        Log.d("RouteActivity", "Map is ready");

        // Check for location permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permission has already been granted
            enableMyLocation();
        }
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null) {
            currentPolyline.remove();
        }
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(currentLocation).title("You are here"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                                place1 = new MarkerOptions().position(currentLocation).title("Location 1");
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&departure_time=now&traffic_model=pessimistic&key=" + getString(R.string.google_maps_key);
        return url;
    }
}



////public class RouteActivity extends FragmentActivity implements OnMapReadyCallback,  TaskLoadedCallback
////{
////
////    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
////    private GoogleMap mMap;
////    private AutoCompleteTextView destinationInput;
////    private Button findRouteButton;
////
////    private LatLng currentLocation;
////
////    private MarkerOptions place1, place2;
////    Button getDirection;
////    private Polyline currentPolyline;
////    private FusedLocationProviderClient fusedLocationClient;
////
////
////
////    @Override
////    protected void onCreate(@Nullable Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_maps);
////        getDirection = findViewById(R.id.find_route_button);
////
////        //get user location
////         fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
////
////        destinationInput = findViewById(R.id.destination_input);
////
////        getDirection.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                new FetchURL(RouteActivity.this).execute(getUrl(currentLocation, place2.getPosition(), "driving"), "driving");
////            }
////        });
////        //27.658143,85.3199503
////        //27.667491,85.3208583
////        place1 = new MarkerOptions().position(currentLocation ).title("Location 1");
////        place2 = new MarkerOptions().position(new LatLng(27.667491, 85.3208583)).title("Location 2");
////
////        MapFragment mapFragment = (MapFragment) getFragmentManager()
////                .findFragmentById(R.id.map_fragment);
////        mapFragment.getMapAsync(this);
////    }
////
//////    @Override
//////    protected void onCreate(Bundle savedInstanceState) {
//////        super.onCreate(savedInstanceState);
//////
//////        // Initialize the Places API
//////        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
//////        PlacesClient placesClient = Places.createClient(this);
//////
//////        setContentView(R.layout.activity_maps);
//////
//////        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//////        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//////                .findFragmentById(R.id.map_fragment);
//////        mapFragment.getMapAsync(this);
//////
//////        destinationInput = findViewById(R.id.destination_input);
//////        findRouteButton = findViewById(R.id.find_route_button);
//////
//////        findRouteButton.setOnClickListener(v -> {
//////            String destination = destinationInput.getText().toString();
//////            if (!destination.isEmpty()) {
//////
//////                new FetchURL(RouteActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
//////
////////                findRoute(destination);
//////            } else {
//////                Toast.makeText(RouteActivity.this, "Please enter a destination", Toast.LENGTH_SHORT).show();
//////            }
//////        });
//////    }
////
////    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
////        // Origin of route
////        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
////        // Destination of route
////        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
////        // Mode
////        String mode = "mode=" + directionMode;
////        // Building the parameters to the web service
////        String parameters = str_origin + "&" + str_dest + "&" + mode;
////        // Output format
////        String output = "json";
////        // Building the url to the web service
////        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
////        return url;
////    }
////
////    @Override
////    public void onMapReady(GoogleMap googleMap) {
////        mMap = googleMap;
////        Log.d("mylog", "Added Markers");
////        mMap.addMarker(place1);
////        mMap.addMarker(place2);
////
////        // Check for location permissions
////        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
////        } else {
////            // Permission has already been granted
////            enableMyLocation();
////        }
////    }
////
////    @Override
////    public void onTaskDone(Object... values) {
////        if (currentPolyline != null)
////            currentPolyline.remove();
////        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
////    }
////
////
////    private void enableMyLocation() {
////        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
////            mMap.setMyLocationEnabled(true);
////            fusedLocationClient.getLastLocation()
////                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
////                        @Override
////                        public void onSuccess(Location location) {
////                            if (location != null) {
////                                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
////                                mMap.addMarker(new MarkerOptions().position(currentLocation).title("You are here"));
////                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
////
//////                                place1 = new MarkerOptions().position(new LatLng(27.658143, 85.3199503)).title("Location 1");
//////                                place2 = new MarkerOptions().position(new LatLng(27.667491, 85.3208583)).title("Location 2");
////
////                            }
////                        }
////                    });
////        } else {
////            Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show();
////        }
////    }
////
//////    @Override
//////    public void onMapReady(GoogleMap googleMap) {
//////        mMap = googleMap;
//////
//////        // Add a marker for the current location (assuming you have current location logic in place)
//////        LatLng currentLocation = new LatLng(-34, 151); // Use actual current location here
//////        mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
//////        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
//////    }
////
//////    private void findRoute(String destination) {
//////        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//////        try {
//////            List<Address> addresses = geocoder.getFromLocationName(destination, 1);
//////            if (addresses.size() > 0) {
//////                Address address = addresses.get(0);
//////                LatLng destinationLatLng = new LatLng(address.getLatitude(), address.getLongitude());
//////                mMap.addMarker(new MarkerOptions().position(destinationLatLng).title("Destination"));
//////
//////                // Move camera to the destination
//////                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destinationLatLng, 15));
//////
//////                // Implement route finding logic here (for example using Directions API)
//////                // Example:
//////                // findDirections(currentLocation, destinationLatLng);
//////
//////
//////                place1 = new MarkerOptions().position(new LatLng(27.658143, 85.3199503)).title("Location 1");
//////                place2 = new MarkerOptions().position(new LatLng(27.667491, 85.3208583)).title("Location 2");
//////                MapFragment mapFragment = (MapFragment) getFragmentManager()
//////                .findFragmentById(R.id.map_fragment);
//////                mapFragment.getMapAsync(this);
//////
//////            } else {
//////                Toast.makeText(this, "Destination not found", Toast.LENGTH_SHORT).show();
//////            }
//////        } catch (IOException e) {
//////            e.printStackTrace();
//////            Toast.makeText(this, "Error finding destination", Toast.LENGTH_SHORT).show();
//////        }
//////    }
////
////    // Add method to find directions using Directions API
////    // private void findDirections(LatLng origin, LatLng destination) {
////    //     // Implement Directions API call here
////    // }
////}
////
////
////
//////package com.thecodecity.mapsdirection;
//////
//////import android.os.Bundle;
//////import android.support.annotation.Nullable;
//////import android.support.v7.app.AppCompatActivity;
//////import android.util.Log;
//////import android.view.View;
//////import android.widget.Button;
//////
//////import com.google.android.gms.maps.GoogleMap;
//////import com.google.android.gms.maps.MapFragment;
//////import com.google.android.gms.maps.OnMapReadyCallback;
//////import com.google.android.gms.maps.model.LatLng;
//////import com.google.android.gms.maps.model.Marker;
//////import com.google.android.gms.maps.model.MarkerOptions;
//////import com.google.android.gms.maps.model.Polyline;
//////import com.google.android.gms.maps.model.PolylineOptions;
//////import com.thecodecity.mapsdirection.directionhelpers.FetchURL;
//////import com.thecodecity.mapsdirection.directionhelpers.TaskLoadedCallback;
//////
///////**
////// * Created by Vishal on 10/20/2018.
////// */
//////
//////public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
//////    private GoogleMap mMap;
//////    private MarkerOptions place1, place2;
//////    Button getDirection;
//////    private Polyline currentPolyline;
//////
//////    @Override
//////    protected void onCreate(@Nullable Bundle savedInstanceState) {
//////        super.onCreate(savedInstanceState);
//////        setContentView(R.layout.map_activity);
//////        getDirection = findViewById(R.id.btnGetDirection);
//////        getDirection.setOnClickListener(new View.OnClickListener() {
//////            @Override
//////            public void onClick(View view) {
//////                new FetchURL(MapActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
//////            }
//////        });
//////        //27.658143,85.3199503
//////        //27.667491,85.3208583
//////        place1 = new MarkerOptions().position(new LatLng(27.658143, 85.3199503)).title("Location 1");
//////        place2 = new MarkerOptions().position(new LatLng(27.667491, 85.3208583)).title("Location 2");
//////        MapFragment mapFragment = (MapFragment) getFragmentManager()
//////                .findFragmentById(R.id.mapNearBy);
//////        mapFragment.getMapAsync(this);
//////    }
//////
//////    @Override
//////    public void onMapReady(GoogleMap googleMap) {
//////        mMap = googleMap;
//////        Log.d("mylog", "Added Markers");
//////        mMap.addMarker(place1);
//////        mMap.addMarker(place2);
//////    }
//////
//////    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
//////        // Origin of route
//////        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
//////        // Destination of route
//////        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
//////        // Mode
//////        String mode = "mode=" + directionMode;
//////        // Building the parameters to the web service
//////        String parameters = str_origin + "&" + str_dest + "&" + mode;
//////        // Output format
//////        String output = "json";
//////        // Building the url to the web service
//////        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
//////        return url;
//////    }
//////
//////    @Override
//////    public void onTaskDone(Object... values) {
//////        if (currentPolyline != null)
//////            currentPolyline.remove();
//////        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
//////    }
//////}
////
////
////
//////package com.example.shesecure10;
//////
//////
//////import androidx.fragment.app.FragmentActivity;
//////import android.os.Bundle;
//////import android.content.Intent;
//////import com.google.android.gms.maps.CameraUpdateFactory;
//////import com.google.android.gms.maps.GoogleMap;
//////import com.google.android.gms.maps.OnMapReadyCallback;
//////import com.google.android.gms.maps.SupportMapFragment;
//////import com.google.android.gms.maps.model.LatLng;
//////import com.google.android.gms.maps.model.MarkerOptions;
//////
//////public class RouteActivity extends FragmentActivity implements OnMapReadyCallback {
//////
//////    private GoogleMap mMap;
//////    private boolean showRoute;
//////
//////    @Override
//////    protected void onCreate(Bundle savedInstanceState) {
//////        super.onCreate(savedInstanceState);
//////
//////        // Get the intent and check for the parameter
//////        Intent intent = getIntent();
//////        showRoute = intent.getBooleanExtra("SHOW_ROUTE", false);
//////
//////        if (showRoute) {
//////            setContentView(R.layout.route_activity);  // Layout for showing route
//////        } else {
//////            setContentView(R.layout.activity_maps);  // Default map layout
//////        }
//////
//////        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//////        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//////                .findFragmentById(R.id.map_fragment);
//////        mapFragment.getMapAsync(this);
//////    }
//////
//////    @Override
//////    public void onMapReady(GoogleMap googleMap) {
//////        mMap = googleMap;
//////
//////        if (showRoute) {
//////            // Implement route showing functionality
//////            LatLng destination = new LatLng(-34, 151);
//////            mMap.addMarker(new MarkerOptions().position(destination).title("Destination"));
//////            mMap.moveCamera(CameraUpdateFactory.newLatLng(destination));
//////        } else {
//////            // Default map functionality
//////            LatLng currentLocation = new LatLng(-34, 151); // Use actual current location here
//////            mMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
//////            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
//////        }
//////    }
//////}