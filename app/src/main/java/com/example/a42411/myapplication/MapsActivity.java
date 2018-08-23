package com.example.a42411.myapplication;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a42411.myapplication.models.PlaceInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";
    private  static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            //new LatLng(-40,-168), new LatLng(71,136)
            //Ireland:
            new LatLng(51.9,-8.5), new LatLng(53.35,-6.26)
    );
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final float CURRENT_LOCATION_DEFAULT_ZOOM = 15f;
    private static final int range = 200;
    private static final int PLACE_PICKER_REQUEST = 1;
    private ImageView gps;
    private ImageView place_info,maps_info;
    private LatLng destinationPlace;
    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GoogleApiClient mGoogleApiClient;
    private double latitude ;
    private double longitude ;
    private AutoCompleteTextView searchText;
    private PlaceAutocompleteAdapter placeAutocompleteAdapter;
    private GeoDataClient mGeoDataClient;
    private PlaceInfo mPlace;
    private Marker mMarker;
    private Marker destinationMarker;
    private Location currentLocation;
    private float distance;
    private DBUtils dbUtils;
    String snippetQuiz;
    String quizAnswer;
    String destination;
    double x_bd,y_bd;
    int flag;
    //LatLng Glucksman_Library = new LatLng(52.673302, -8.573495);
    LatLng Kemmy_Business_School = new LatLng( 52.672582,  -8.57676);
    LatLng Stables_Club = new LatLng(52.67313759999999,  -8.5705331);
    LatLng Arena = new LatLng(52.6736515,  -8.565119500000002);
    LatLng Pavilion = new LatLng(52.679127900000005, -8.5697567);
    //LatLng Pizza = new LatLng(52.6763517,  -8.575845);
    //LatLng The_Plassey_House = new LatLng(52.673586199999995,  -8.5707292);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        destination = intent.getStringExtra("Destination");
        snippetQuiz = intent.getStringExtra("quizContent");
        quizAnswer = intent.getStringExtra("quizAnswer");
        flag = intent.getIntExtra("flag",-2);
        x_bd = intent.getDoubleExtra("x",3.14);
        y_bd = intent.getDoubleExtra("y",0.9527);

        searchText = (AutoCompleteTextView) findViewById(R.id.search_area);
        gps = (ImageView)findViewById(R.id.gps);
        place_info = (ImageView)findViewById(R.id.place_info);
        maps_info = (ImageView)findViewById(R.id.maps_info);
        mGeoDataClient = Places.getGeoDataClient(this, null);

//        dbUtils = new DBUtils(this,"db_building",1,"tb_bd");
//        count = dbUtils.getCount();
//        random = dbUtils.randomNumber(count);

//        Log.e("shi yi shi",""+random);
//        for (int i = 0;i<count;i++){
//            Log.e("shi yi shi",""+dbUtils.getBuildingName().get(i));
//            Log.e("shi yi shi",""+dbUtils.getBuildingId().get(i));
//        }
//        Log.e("shi yi shi",""+dbUtils.getBuildingName().get(random));
//        Log.e("shi yi shi",""+dbUtils.getBuildingId().get(random));
        Toast.makeText(this, "Your Destination is "+destination , Toast.LENGTH_LONG).show();
//        if(cursor.moveToFirst()){
//            do {
//                snippetQuiz = cursor.getString(cursor.getColumnIndex("name_bd"));
//                Log.i("test",snippetQuiz);
//            }
//            while (cursor.moveToNext());
//        }        cursor.close();

        getLocationPermission();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//        Intent intent = getIntent();
//        latitude = intent.getExtras().getDouble("latitude");
//        longitude = intent.getExtras().getDouble("longitude");

    }
    private void init(){
        Log.d(TAG, "init: initializing");


        searchText.setOnItemClickListener(autoCompleteClickListener);

        placeAutocompleteAdapter = new PlaceAutocompleteAdapter(this,mGeoDataClient,LAT_LNG_BOUNDS,null);
        searchText.setAdapter(placeAutocompleteAdapter);
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){

                    //execute our method for searching
                    geoLocate();
                }

                return false;
            }
        });
        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceLocation();
                if(destinationPlace!=null&&destinationPlace.latitude==x_bd&&destinationPlace.longitude==y_bd){
                    if(distanceBetween2Points(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),destinationPlace)<=range){
                        MarkerOptions options = new MarkerOptions()
                                .position(destinationPlace)
                                .title(mPlace.getName())
                                .snippet(snippetQuiz);
                        destinationMarker = mMap.addMarker(options);
                        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {
                                Intent i = new Intent(MapsActivity.this,QuizActivity.class);
                                i.putExtra("q",snippetQuiz);
                                i.putExtra("a",quizAnswer);
                                i.putExtra("flag",flag);
                                startActivity(i);
                            }
                        });
                    }
                }


            }
        });
        place_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"clicked place Info");
                try{
                    if(destinationMarker.isInfoWindowShown()){
                        destinationMarker.hideInfoWindow();
                    }else {
                        destinationMarker.showInfoWindow();
                    }
                }catch (NullPointerException e ){
                    Log.e(TAG, "onClick: NullPointerException "+ e.getMessage());
                }
            }
        });
        maps_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(MapsActivity.this), PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        hideSoftKeyboard();

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this,data);
                Task<PlaceBufferResponse> placeResult = mGeoDataClient.getPlaceById(place.getId());
                placeResult.addOnCompleteListener(mUpdatePlaceDetailsCallback);
                //String toastMsg = String.format("Place: %s", place.getName());
                //Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }



    private void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");

        String searchString = searchText.getText().toString();

        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
        }

        if(list.size() > 0){
            Address address = list.get(0);
            //maddress = list.get(0);
            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();
            moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),DEFAULT_ZOOM,address.getAddressLine(0));

        }
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(latitude, longitude);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Location!"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;
        defaultMarker();
        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            init();
        }
    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                             currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    CURRENT_LOCATION_DEFAULT_ZOOM,"Current Location");
                            Circle circle = mMap.addCircle(new CircleOptions()
                                    .center(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
                                    .radius(range)
                                    .strokeWidth(2)
                                    .strokeColor(Color.BLUE)
                                    .fillColor(0x00000000)
                            );

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void moveCamera(LatLng latLng, float zoom, PlaceInfo placeInfo){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        mMap.clear();

        mMap.setInfoWindowAdapter(new CustomInfoAdapter(MapsActivity.this));

        if(placeInfo!=null){
            try{
                //distance = distanceBetween2Points(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),latLng);
                //Log.i("DISTANCE","distance between ur current location and destination is : "+distance+" meters");
                //Toast.makeText(MapsActivity.this,"distance between ur current location and destination is : "+distance+" meters",Toast.LENGTH_SHORT);
                String snippet = "Address: " + placeInfo.getAddress() + "\n" +
                        "Phone Number: " + placeInfo.getPhoneNumber() + "\n" +
                        "Website: " + placeInfo.getWebsiteUri() + "\n" +
                        "Price Rating: " + placeInfo.getRating() + "\n";
                //snippetQuiz="questions here";

//                if(distance>200){
                    MarkerOptions options = new MarkerOptions()
                            .position(latLng)
                            .title(placeInfo.getName())
                            .snippet(snippet);
                    destinationMarker = mMap.addMarker(options);
//                }else {

//                    MarkerOptions options = new MarkerOptions()
//                            .position(latLng)
//                            .title(placeInfo.getName())
//                            .snippet(snippet/*Quiz*/);
//                    destinationMarker = mMap.addMarker(options);
//                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//                        @Override
//                        public void onInfoWindowClick(Marker marker) {
//                            Intent i = new Intent(MapsActivity.this,QuizActivity.class);
//                            i.putExtra("q",marker.getSnippet());
//                            startActivity(i);
//                        }
//                    });
//                }
                defaultMarker();

            }catch (NullPointerException e){
                Log.e(TAG,"NullPointerException " + e.getMessage());
            }
        }else{
            mMap.addMarker(new MarkerOptions().position(latLng));
            defaultMarker();
        }
        hideSoftKeyboard();
    }

    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if(!title.equals("Current Location")){
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(markerOptions);
        }
        hideSoftKeyboard();
    }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapsActivity.this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }


    private void hideSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    /* google places API autocomplete suggestions*/
    private AdapterView.OnItemClickListener autoCompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            hideSoftKeyboard();

            final AutocompletePrediction item = placeAutocompleteAdapter.getItem(i);
            final String placeID = item.getPlaceId();
            Task<PlaceBufferResponse> placeResult = mGeoDataClient.getPlaceById(placeID);
            placeResult.addOnCompleteListener(mUpdatePlaceDetailsCallback);

        }
    };

    private OnCompleteListener<PlaceBufferResponse> mUpdatePlaceDetailsCallback = new OnCompleteListener<PlaceBufferResponse>() {
        @Override
        public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
            if(task.isSuccessful()){
                PlaceBufferResponse places = task.getResult();
                final Place myPlace = places.get(0);
                destinationPlace = myPlace.getLatLng();
                try{
                    mPlace = new PlaceInfo();
                    mPlace.setName(myPlace.getName().toString());
                    mPlace.setAddress(myPlace.getAddress().toString());
                    mPlace.setAttributions(myPlace.getAttributions().toString());
                    mPlace.setId(myPlace.getId());
                    mPlace.setLatlng(myPlace.getLatLng());
                    mPlace.setRating(myPlace.getRating());
                    mPlace.setPhoneNumber(myPlace.getPhoneNumber().toString());
                    mPlace.setWebsiteUri(myPlace.getWebsiteUri());
                }catch (NullPointerException e){
                    Log.e(TAG,e.getMessage());
                }
                moveCamera(myPlace.getLatLng(),DEFAULT_ZOOM,mPlace);
//                Log.d(TAG,"place details: "+myPlace.getAttributions());
//                Log.d(TAG,"place details: "+myPlace.getViewport());
//                Log.d(TAG,"place details: "+myPlace.getPhoneNumber());
//                Log.d(TAG,"place details: "+myPlace.getWebsiteUri());
//                Log.d(TAG,"place details: "+myPlace.getId());
//                Log.d(TAG,"place details: "+myPlace.getAddress());
//                Log.d(TAG,"place details: "+myPlace.getLatLng());
                places.release();

            }else{
                Log.e(TAG,"Place not Found");
            }
        }
    };

    private float distanceBetween2Points(LatLng locationA,LatLng locationB){
        float[] distance = new float[1];
        Location.distanceBetween(locationA.latitude,locationA.longitude,locationB.latitude,locationB.longitude,distance);
        return distance[0];
        //Another way to calculate distance :
        //Float distance = currentLoc.distanceTo(markerLoc);
    }
    public void defaultMarker(){
        //mMap.addMarker(new MarkerOptions().position(Glucksman_Library)).setTitle("Glucksman Library");
        MarkerOptions arena = new MarkerOptions()
                .position(Arena)
                .title("3. UL Sport Arena")
                .snippet("Gym ,Basketball and Swimming here!");
        MarkerOptions kemmy = new MarkerOptions()
                .position(Kemmy_Business_School)
                .title("1. Kemmy Business School")
                .snippet("Business Business Business!");
        MarkerOptions stables = new MarkerOptions()
                .position(Stables_Club)
                .title("2. Stables Club")
                .snippet("Irish Whiskey!");
        MarkerOptions pavilion = new MarkerOptions()
                .position(Pavilion)
                .title("4. The Pavilion")
                .snippet("Restaurant!");
        mMap.addMarker(arena);
        mMap.addMarker(kemmy);
        //mMap.addMarker(new MarkerOptions().position(Pizza)).setTitle("The Pizza Co.");
        mMap.addMarker(stables);
        mMap.addMarker(pavilion);
    }
}
