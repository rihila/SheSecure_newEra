//package com.example.shesecure10;
//
//import android.os.Bundle;
//import androidx.fragment.app.FragmentActivity;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.maps.android.heatmaps.HeatmapTileProvider;
//import com.google.maps.android.heatmaps.WeightedLatLng;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import java.io.InputStream;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//    private HeatmapTileProvider mProvider;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.mapactivity);
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map_fragment_heatmap);
//        mapFragment.getMapAsync(this);
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        LatLng defaultLocation = new LatLng(26.6329, 88.4071); // Example: default location
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10));
//        addHeatMap();
//    }
//
//    private void addHeatMap() {
//        List<WeightedLatLng> list = new ArrayList<>();
//
//        try {
//            // Load the GeoJSON file from the assets folder
//            InputStream inputStream = getAssets().open("test.geojson");
//            byte[] buffer = new byte[inputStream.available()];
//            inputStream.read(buffer);
//            inputStream.close();
//            String json = new String(buffer, StandardCharsets.UTF_8);
//
//            JSONObject jsonObject = new JSONObject(json);
//            JSONArray features = jsonObject.getJSONArray("features");
//
//            for (int i = 0; i < features.length(); i++) {
//                JSONObject feature = features.getJSONObject(i);
//                JSONObject geometry = feature.getJSONObject("geometry");
//                JSONArray coordinates = geometry.getJSONArray("coordinates").getJSONArray(0).getJSONArray(0);
//
//                // Calculate the centroid of the polygon
//                double centroidLat = 0;
//                double centroidLng = 0;
//                int numPoints = coordinates.length();
//
//                for (int j = 0; j < numPoints; j++) {
//                    JSONArray point = coordinates.getJSONArray(j);
//                    double lon = point.getDouble(0);
//                    double lat = point.getDouble(1);
//                    centroidLat += lat;
//                    centroidLng += lon;
//                }
//
//                centroidLat /= numPoints;
//                centroidLng /= numPoints;
//
//                // Use DN property as weight for the heatmap
//                int weight = feature.getJSONObject("properties").getInt("DN");
//
//                // Add centroid with weight to the heatmap
//                list.add(new WeightedLatLng(new LatLng(centroidLat, centroidLng), weight));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Create the heatmap tile provider, passing it the list of weighted LatLngs
//        mProvider = new HeatmapTileProvider.Builder()
//                .weightedData(list)
//                .build();
//
//        // Add the tile overlay to the map
//        mMap.addTileOverlay(new com.google.android.gms.maps.model.TileOverlayOptions().tileProvider(mProvider));
//    }
//}





//package com.example.shesecure10;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.FragmentActivity;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.os.Bundle;
//import android.widget.Toast;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.example.shesecure10.databinding.ActivityMapsBinding;
//import com.google.android.gms.tasks.OnSuccessListener;
//
//public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
//
//    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
//    private GoogleMap mMap;
//    private ActivityMapsBinding binding;
//    private FusedLocationProviderClient fusedLocationClient;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        binding = ActivityMapsBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.route_activity);
//
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map_fragment_heatmap);
//        mapFragment.getMapAsync(this);
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap)
//    {
//        mMap = googleMap;
//
//        mMap.setTrafficEnabled(true);
//
//        // Check for location permissions
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
//        } else {
//            // Permission has already been granted
//            enableMyLocation();
//        }
//    }
//
//    private void enableMyLocation() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            mMap.setMyLocationEnabled(true);
//            fusedLocationClient.getLastLocation()
//                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                        @Override
//                        public void onSuccess(Location location) {
//                            if (location != null) {
//                                LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
//                                mMap.addMarker(new MarkerOptions().position(currentLocation).title("Your Current Location"));
//                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 18));
//                            }
//                        }
//                    });
//        } else {
//            Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted
//                enableMyLocation();
//            } else {
//                // Permission denied
//                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//}







package com.example.shesecure10;

import static com.mapbox.maps.extension.style.expressions.dsl.generated.ExpressionDslKt.heatmapDensity;
import static com.mapbox.maps.extension.style.expressions.dsl.generated.ExpressionDslKt.literal;
import static com.mapbox.maps.extension.style.expressions.dsl.generated.ExpressionDslKt.rgba;
import static com.mapbox.maps.extension.style.expressions.dsl.generated.ExpressionDslKt.zoom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.extension.style.expressions.generated.Expression;
import com.mapbox.maps.extension.style.layers.LayerUtils;
import com.mapbox.maps.extension.style.layers.generated.CircleLayer;
import com.mapbox.maps.extension.style.layers.generated.HeatmapLayer;
import com.mapbox.maps.extension.style.sources.SourceUtils;
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource;

public class MapActivity extends AppCompatActivity {
    private final String EARTHQUAKE_SOURCE_URL = "https://www.mapbox.com/mapbox-gl-js/assets/earthquakes.geojson";
    private final String EARTHQUAKE_SOURCE_ID = "earthquakes";
    private final String HEATMAP_LAYER_ID = "earthquakes-heat";
    private final String HEATMAP_LAYER_SOURCE = "earthquakes";
    private final String CIRCLE_LAYER_ID = "earthquake-circle";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapactivity);

        MapView mapView = findViewById(R.id.mapView);
        mapView.getMapboxMap().loadStyleUri(Style.DARK, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                addEarthquakeSource(style);
                addHeatmapLayer(style);
                addCircleLayer(style);
            }
        });
    }

    private void addEarthquakeSource(Style style) {
        GeoJsonSource.Builder builder = new GeoJsonSource.Builder(EARTHQUAKE_SOURCE_ID).url(EARTHQUAKE_SOURCE_URL);
        SourceUtils.addSource(style, builder.build());
    }

    private void addHeatmapLayer(Style style) {
        HeatmapLayer heatmapLayer = new HeatmapLayer(HEATMAP_LAYER_ID, EARTHQUAKE_SOURCE_ID);
        heatmapLayer.maxZoom(8);
        heatmapLayer.sourceLayer(HEATMAP_LAYER_SOURCE);
        heatmapLayer.heatmapColor(Expression.interpolate(
                Expression.linear(),
                heatmapDensity(),
                literal(0),
                rgba(33.0,102.0,172.0,0.0),
                literal(0.2),
                Expression.rgb(103.0,169.0,207.0),
                literal(0.4),
                Expression.rgb(209.0,229.0,240.0),
                literal(0.6),
                Expression.rgb(253.0,219.0,240.0),
                literal(0.8),
                Expression.rgb(239.0,138.0,98.0),
                literal(1),
                Expression.rgb(178.0,24.0,43.0)
        ));
        heatmapLayer.heatmapIntensity(
                Expression.interpolate(
                        Expression.linear(),
                        Expression.zoom(),
                        literal(0),
                        literal(1),
                        literal(3),
                        literal(9)
                )
        );
        heatmapLayer.heatmapOpacity(Expression.interpolate(
                Expression.linear(),
                Expression.zoom(),
                literal(1),
                literal(7),
                literal(9),
                literal(0)
        ));
        heatmapLayer.heatmapWeight(Expression.interpolate(
                Expression.linear(),
                Expression.get(literal("mag")),
                literal(0),
                literal(0),
                literal(6),
                literal(1)
        ));
        LayerUtils.addLayerAbove(style,heatmapLayer,"waterway-label");
    }

    private void addCircleLayer(Style style) {
        CircleLayer circleLayer = new CircleLayer(CIRCLE_LAYER_ID, EARTHQUAKE_SOURCE_ID);
        circleLayer.circleRadius(Expression.interpolate(
                Expression.linear(),
                Expression.zoom(),
                literal(4),
                Expression.interpolate(
                        Expression.linear(),
                        Expression.get(literal("mag")),
                        literal(1),
                        Expression.literal(1)
                ),
                literal(6),
                literal(7),
                literal(16),
                Expression.interpolate(
                        Expression.linear(),
                        Expression.get(literal("mag")),
                        literal(1),
                        literal(5),
                        literal(6),
                        literal(50)
                )
        ));
        circleLayer.circleColor(Expression.interpolate(
                Expression.linear(),
                Expression.get(literal("mag")),
                literal(1),
                Expression.rgba(33.0,102.0,172.0,0.0),
                literal(2),
                Expression.rgb(102.0,169.0,207.0),
                literal(3),
                Expression.rgb(209.0,219.0,199.0),
                literal(5),
                Expression.rgb(239.0,138.0,98.0),
                literal(6),
                Expression.rgb(178.0,24.0,43.0)
        ));
        circleLayer.circleOpacity(Expression.interpolate(
                Expression.linear(),
                Expression.zoom(),
                literal(7),
                literal(0),
                literal(8),
                literal(1)
        ));
        circleLayer.circleStrokeWidth(0.1);
        circleLayer.circleStrokeColor("white");
        LayerUtils.addLayerBelow(style, circleLayer, HEATMAP_LAYER_ID);
    }
}


