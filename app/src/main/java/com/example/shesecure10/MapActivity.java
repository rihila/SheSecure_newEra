package com.example.shesecure10;

import static com.mapbox.maps.extension.style.expressions.dsl.generated.ExpressionDslKt.heatmapDensity;
import static com.mapbox.maps.extension.style.expressions.dsl.generated.ExpressionDslKt.literal;
import static com.mapbox.maps.extension.style.expressions.dsl.generated.ExpressionDslKt.rgb;
import static com.mapbox.maps.extension.style.expressions.dsl.generated.ExpressionDslKt.rgba;
import static com.mapbox.maps.extension.style.expressions.dsl.generated.ExpressionDslKt.zoom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.extension.style.expressions.generated.Expression;
import com.mapbox.maps.extension.style.layers.LayerUtils;
import com.mapbox.maps.extension.style.layers.generated.CircleLayer;
import com.mapbox.maps.extension.style.layers.generated.HeatmapLayer;
import com.mapbox.maps.extension.style.sources.SourceUtils;
import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource;

import java.io.IOException;
import java.io.InputStream;

public class MapActivity extends AppCompatActivity {
    private final String POLLUTION_SOURCE_ID = "pollution";
    private final String HEATMAP_LAYER_ID = "pollution-heat";
    private final String CIRCLE_LAYER_ID = "pollution-circle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapactivity);

        MapView mapView = findViewById(R.id.mapView);
        mapView.getMapboxMap().loadStyleUri(Style.DARK, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                addPollutionSource(style);
                addHeatmapLayer(style);
                addCircleLayer(style);
            }
        });
    }

    private void addPollutionSource(Style style) {
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.test);
            GeoJsonSource.Builder builder = new GeoJsonSource.Builder(POLLUTION_SOURCE_ID)
                    .data(inputStream.toString());
            SourceUtils.addSource(style, builder.build());
        } catch (Exception e) {
            Log.e("MapActivity", "Error loading GeoJSON: " + e.getMessage());
        }
    }

    private void addHeatmapLayer(Style style) {
        HeatmapLayer heatmapLayer = new HeatmapLayer(HEATMAP_LAYER_ID, POLLUTION_SOURCE_ID);
        heatmapLayer.maxZoom(8);
        heatmapLayer.heatmapColor(Expression.interpolate(
                Expression.linear(),
                heatmapDensity(),
                literal(0),
                rgba(33, 102, 172, 0),
                literal(0.2),
                rgb(103, 169, 207),
                literal(0.4),
                rgb(209, 229, 240),
                literal(0.6),
                rgb(253, 219, 240),
                literal(0.8),
                rgb(239, 138, 98),
                literal(1),
                rgb(178, 24, 43)
        ));
        heatmapLayer.heatmapIntensity(
                Expression.interpolate(
                        Expression.linear(),
                        zoom(),
                        literal(0),
                        literal(1),
                        literal(3),
                        literal(9)
                )
        );
        heatmapLayer.heatmapOpacity(Expression.interpolate(
                Expression.linear(),
                zoom(),
                literal(1),
                literal(0.7),
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
        LayerUtils.addLayerAbove(style, heatmapLayer, "waterway-label");
    }

    private void addCircleLayer(Style style) {
        CircleLayer circleLayer = new CircleLayer(CIRCLE_LAYER_ID, POLLUTION_SOURCE_ID);
        circleLayer.circleRadius(Expression.interpolate(
                Expression.linear(),
                zoom(),
                literal(4),
                Expression.interpolate(
                        Expression.linear(),
                        Expression.get(literal("mag")),
                        literal(1),
                        literal(1)
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
                rgba(33, 102, 172, 0),
                literal(2),
                rgb(102, 169, 207),
                literal(3),
                rgb(209, 219, 199),
                literal(5),
                rgb(239, 138, 98),
                literal(6),
                rgb(178, 24, 43)
        ));
        circleLayer.circleOpacity(Expression.interpolate(
                Expression.linear(),
                zoom(),
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



//package com.example.shesecure10;
//
//import static com.mapbox.maps.extension.style.expressions.dsl.generated.ExpressionDslKt.heatmapDensity;
//import static com.mapbox.maps.extension.style.expressions.dsl.generated.ExpressionDslKt.literal;
//import static com.mapbox.maps.extension.style.expressions.dsl.generated.ExpressionDslKt.rgb;
//import static com.mapbox.maps.extension.style.expressions.dsl.generated.ExpressionDslKt.rgba;
//import static com.mapbox.maps.extension.style.expressions.dsl.generated.ExpressionDslKt.zoom;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//import com.mapbox.maps.MapView;
//import com.mapbox.maps.Style;
//import com.mapbox.maps.extension.style.expressions.generated.Expression;
//import com.mapbox.maps.extension.style.layers.LayerUtils;
//import com.mapbox.maps.extension.style.layers.generated.CircleLayer;
//import com.mapbox.maps.extension.style.layers.generated.HeatmapLayer;
//import com.mapbox.maps.extension.style.sources.SourceUtils;
//import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource;
//
//import java.io.InputStream;
//
//public class MapActivity extends AppCompatActivity {
//    private final String EARTHQUAKE_SOURCE_ID = "earthquakes";
//    private final String HEATMAP_LAYER_ID = "earthquakes-heat";
//    private final String HEATMAP_LAYER_SOURCE = "earthquakes";
//    private final String CIRCLE_LAYER_ID = "earthquake-circle";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.mapactivity);
//
//        MapView mapView = findViewById(R.id.mapView);
//        mapView.getMapboxMap().loadStyleUri(Style.DARK, new Style.OnStyleLoaded() {
//            @Override
//            public void onStyleLoaded(@NonNull Style style) {
//                addEarthquakeSource(style);
//                addHeatmapLayer(style);
//                addCircleLayer(style);
//            }
//        });
//    }
//
//    private void addEarthquakeSource(Style style) {
//        // Load the GeoJSON from the raw resource
//        InputStream inputStream = getResources().openRawResource(R.raw.populationfinal);
//        GeoJsonSource.Builder builder = new GeoJsonSource.Builder(EARTHQUAKE_SOURCE_ID)
//                .data(inputStream.toString());
//        SourceUtils.addSource(style, builder.build());
//    }
//
//    private void addHeatmapLayer(Style style) {
//        HeatmapLayer heatmapLayer = new HeatmapLayer(HEATMAP_LAYER_ID, EARTHQUAKE_SOURCE_ID);
//        heatmapLayer.maxZoom(8);
//        heatmapLayer.sourceLayer(HEATMAP_LAYER_SOURCE);
//        heatmapLayer.heatmapColor(Expression.interpolate(
//                Expression.linear(),
//                heatmapDensity(),
//                literal(0),
//                rgba(33, 102, 172, 0),
//                literal(0.2),
//                rgb(103, 169, 207),
//                literal(0.4),
//                rgb(209, 229, 240),
//                literal(0.6),
//                rgb(253, 219, 240),
//                literal(0.8),
//                rgb(239, 138, 98),
//                literal(1),
//                rgb(178, 24, 43)
//        ));
//        heatmapLayer.heatmapIntensity(
//                Expression.interpolate(
//                        Expression.linear(),
//                        zoom(),
//                        literal(0),
//                        literal(1),
//                        literal(3),
//                        literal(9)
//                )
//        );
//        heatmapLayer.heatmapOpacity(Expression.interpolate(
//                Expression.linear(),
//                zoom(),
//                literal(1),
//                literal(0.7),
//                literal(9),
//                literal(0)
//        ));
//        heatmapLayer.heatmapWeight(Expression.interpolate(
//                Expression.linear(),
//                Expression.get(literal("mag")),
//                literal(0),
//                literal(0),
//                literal(6),
//                literal(1)
//        ));
//        LayerUtils.addLayerAbove(style, heatmapLayer, "waterway-label");
//    }
//
//    private void addCircleLayer(Style style) {
//        CircleLayer circleLayer = new CircleLayer(CIRCLE_LAYER_ID, EARTHQUAKE_SOURCE_ID);
//        circleLayer.circleRadius(Expression.interpolate(
//                Expression.linear(),
//                zoom(),
//                literal(4),
//                Expression.interpolate(
//                        Expression.linear(),
//                        Expression.get(literal("mag")),
//                        literal(1),
//                        literal(1)
//                ),
//                literal(6),
//                literal(7),
//                literal(16),
//                Expression.interpolate(
//                        Expression.linear(),
//                        Expression.get(literal("mag")),
//                        literal(1),
//                        literal(5),
//                        literal(6),
//                        literal(50)
//                )
//        ));
//        circleLayer.circleColor(Expression.interpolate(
//                Expression.linear(),
//                Expression.get(literal("mag")),
//                literal(1),
//                rgba(33, 102, 172, 0),
//                literal(2),
//                rgb(102, 169, 207),
//                literal(3),
//                rgb(209, 219, 199),
//                literal(5),
//                rgb(239, 138, 98),
//                literal(6),
//                rgb(178, 24, 43)
//        ));
//        circleLayer.circleOpacity(Expression.interpolate(
//                Expression.linear(),
//                zoom(),
//                literal(7),
//                literal(0),
//                literal(8),
//                literal(1)
//        ));
//        circleLayer.circleStrokeWidth(0.1);
//        circleLayer.circleStrokeColor("white");
//        LayerUtils.addLayerBelow(style, circleLayer, HEATMAP_LAYER_ID);
//    }
//}
//
//
//
//
//
////package com.example.shesecure10;
////
////import static com.mapbox.maps.extension.style.expressions.dsl.generated.ExpressionDslKt.heatmapDensity;
////import static com.mapbox.maps.extension.style.expressions.dsl.generated.ExpressionDslKt.literal;
////import static com.mapbox.maps.extension.style.expressions.dsl.generated.ExpressionDslKt.rgba;
////import static com.mapbox.maps.extension.style.expressions.dsl.generated.ExpressionDslKt.zoom;
////
////import androidx.annotation.NonNull;
////import androidx.appcompat.app.AppCompatActivity;
////
////import android.os.Bundle;
////
////import com.mapbox.maps.MapView;
////import com.mapbox.maps.Style;
////import com.mapbox.maps.extension.style.expressions.generated.Expression;
////import com.mapbox.maps.extension.style.layers.LayerUtils;
////import com.mapbox.maps.extension.style.layers.generated.CircleLayer;
////import com.mapbox.maps.extension.style.layers.generated.HeatmapLayer;
////import com.mapbox.maps.extension.style.sources.SourceUtils;
////import com.mapbox.maps.extension.style.sources.generated.GeoJsonSource;
////
////public class MapActivity extends AppCompatActivity {
////    private final String EARTHQUAKE_SOURCE_URL = "https://www.mapbox.com/mapbox-gl-js/assets/earthquakes.geojson";
////    private final String EARTHQUAKE_SOURCE_ID = "earthquakes";
////    private final String HEATMAP_LAYER_ID = "earthquakes-heat";
////    private final String HEATMAP_LAYER_SOURCE = "earthquakes";
////    private final String CIRCLE_LAYER_ID = "earthquake-circle";
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.mapactivity);
////
////        MapView mapView = findViewById(R.id.mapView);
////        mapView.getMapboxMap().loadStyleUri(Style.DARK, new Style.OnStyleLoaded() {
////            @Override
////            public void onStyleLoaded(@NonNull Style style) {
////                addEarthquakeSource(style);
////                addHeatmapLayer(style);
////                addCircleLayer(style);
////            }
////        });
////    }
////
////    private void addEarthquakeSource(Style style) {
////        GeoJsonSource.Builder builder = new GeoJsonSource.Builder(EARTHQUAKE_SOURCE_ID).url(EARTHQUAKE_SOURCE_URL);
////        SourceUtils.addSource(style, builder.build());
////    }
////
////    private void addHeatmapLayer(Style style) {
////        HeatmapLayer heatmapLayer = new HeatmapLayer(HEATMAP_LAYER_ID, EARTHQUAKE_SOURCE_ID);
////        heatmapLayer.maxZoom(8);
////        heatmapLayer.sourceLayer(HEATMAP_LAYER_SOURCE);
////        heatmapLayer.heatmapColor(Expression.interpolate(
////                Expression.linear(),
////                heatmapDensity(),
////                literal(0),
////                rgba(33.0,102.0,172.0,0.0),
////                literal(0.2),
////                Expression.rgb(103.0,169.0,207.0),
////                literal(0.4),
////                Expression.rgb(209.0,229.0,240.0),
////                literal(0.6),
////                Expression.rgb(253.0,219.0,240.0),
////                literal(0.8),
////                Expression.rgb(239.0,138.0,98.0),
////                literal(1),
////                Expression.rgb(178.0,24.0,43.0)
////        ));
////        heatmapLayer.heatmapIntensity(
////                Expression.interpolate(
////                        Expression.linear(),
////                        Expression.zoom(),
////                        literal(0),
////                        literal(1),
////                        literal(3),
////                        literal(9)
////                )
////        );
////        heatmapLayer.heatmapOpacity(Expression.interpolate(
////                Expression.linear(),
////                Expression.zoom(),
////                literal(1),
////                literal(7),
////                literal(9),
////                literal(0)
////        ));
////        heatmapLayer.heatmapWeight(Expression.interpolate(
////                Expression.linear(),
////                Expression.get(literal("mag")),
////                literal(0),
////                literal(0),
////                literal(6),
////                literal(1)
////        ));
////        LayerUtils.addLayerAbove(style,heatmapLayer,"waterway-label");
////    }
////
////    private void addCircleLayer(Style style) {
////        CircleLayer circleLayer = new CircleLayer(CIRCLE_LAYER_ID, EARTHQUAKE_SOURCE_ID);
////        circleLayer.circleRadius(Expression.interpolate(
////                Expression.linear(),
////                Expression.zoom(),
////                literal(4),
////                Expression.interpolate(
////                        Expression.linear(),
////                        Expression.get(literal("mag")),
////                        literal(1),
////                        Expression.literal(1)
////                ),
////                literal(6),
////                literal(7),
////                literal(16),
////                Expression.interpolate(
////                        Expression.linear(),
////                        Expression.get(literal("mag")),
////                        literal(1),
////                        literal(5),
////                        literal(6),
////                        literal(50)
////                )
////        ));
////        circleLayer.circleColor(Expression.interpolate(
////                Expression.linear(),
////                Expression.get(literal("mag")),
////                literal(1),
////                Expression.rgba(33.0,102.0,172.0,0.0),
////                literal(2),
////                Expression.rgb(102.0,169.0,207.0),
////                literal(3),
////                Expression.rgb(209.0,219.0,199.0),
////                literal(5),
////                Expression.rgb(239.0,138.0,98.0),
////                literal(6),
////                Expression.rgb(178.0,24.0,43.0)
////        ));
////        circleLayer.circleOpacity(Expression.interpolate(
////                Expression.linear(),
////                Expression.zoom(),
////                literal(7),
////                literal(0),
////                literal(8),
////                literal(1)
////        ));
////        circleLayer.circleStrokeWidth(0.1);
////        circleLayer.circleStrokeColor("white");
////        LayerUtils.addLayerBelow(style, circleLayer, HEATMAP_LAYER_ID);
////    }
////}
