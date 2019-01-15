package com.access.espol.marco77713.espolaccess.views.fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.access.espol.marco77713.espolaccess.MainActivity;
import com.access.espol.marco77713.espolaccess.R;
import com.access.espol.marco77713.espolaccess.model.Edificio;
import com.access.espol.marco77713.espolaccess.model.Objetos;
import com.access.espol.marco77713.espolaccess.model.User;
import com.access.espol.marco77713.espolaccess.views.BuildingActivity;
import com.access.espol.marco77713.espolaccess.views.ContainerActivity;
import com.access.espol.marco77713.espolaccess.views.EvaluationActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import static com.access.espol.marco77713.espolaccess.R.layout.see_or_evaluate;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Marker prueba;
    double x1=-0.000002295;//solo cambiar penultimo digito 115 => 125 OJO 195
    double x2= 0.000002295;
    double y1=-0.000002295;
    double y2= 0.000002295;
    ArrayList<Objetos> listaobj = new ArrayList<Objetos>();
    public ArrayList<String> edificios_evaluados = new ArrayList<>();
    private static int RETICION_PERMISO_LOCALIZACION = 101;
    private double lat, lon;
    private String mensaje;

    int i =0;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;


    private DatabaseReference myRef2;

    User user;

    private List<Edificio> edificioList = new ArrayList<>();

    Dialog myDialog;
    public BottomBar bottomBar;

    SearchFragment searchFragment = new SearchFragment();

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Maps");
        getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable( this, R.drawable.btn_rounded));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFFFF));


        bottomBar = (BottomBar) findViewById(R.id.bottombar);
        bottomBar.setDefaultTab(R.id.maps);

        final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.container);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                Intent intent = new Intent(MapsActivity.this, ContainerActivity.class);
                while (i<=1) {
                    switch (tabId) {
                        case R.id.challenge:
                            intent.putExtra("tabSelected", R.id.challenge);
                            startActivity(intent);
                            tabId = R.id.maps;
                            break;
                        case R.id.maps:
                            //getPuntos();
                            //MapsActivity mapsActivity = new MapsActivity();
                            //changeFragment(mapsActivity);
                            frameLayout.setVisibility(View.INVISIBLE);
                            break;
                        case R.id.search:
                            frameLayout.setVisibility(View.VISIBLE);

                            getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .addToBackStack(null).commit();
                            /*intent.putExtra("tabSelected", R.id.search);
                            startActivity(intent);
                            tabId = R.id.maps;*/
                            break;
                        case R.id.profile:
                            intent.putExtra("tabSelected", R.id.profile);
                            startActivity(intent);
                            tabId = R.id.maps;
                            break;
                    }
                    System.out.println("TAB");
                    i++;
                }
                i=0;
            }

        });

        //////////////////// LLAMADA A LA BASE DE DATOS 2

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(MapsActivity.this, MainActivity.class));
                }
            }
        };

        myRef2 = database.getReference("users/" + String.valueOf(mAuth.getCurrentUser().getUid()));

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                System.out.println("QUE PASO " + dataSnapshot.getValue());
                user = dataSnapshot.getValue(User.class);
                System.out.println(""+user.getEdificios_evaluados());
                //mapFragment.edificios_evaluados = user.getEdificios_evaluados();

                switch (user.getEdificios_evaluados().size()){
                    case (0):
                        getSupportActionBar().setHomeAsUpIndicator(R.drawable.btn_rounded);
                        break;
                    case (2):
                        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icons8_love_24);
                        break;


                }
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value." + error.toException());
            }



        });




        ////////////////////LLAMADA A LA BASE DE DATOS

        myRef = database.getReference("edificios");;
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    Edificio edificio = snap.getValue(Edificio.class);
                    edificioList.add(edificio);
                    System.out.println("Value is: " + edificioList);
                    llenarUbicacion();
                    llenarMarker();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value." + error.toException());
            }
        });


        myDialog = new Dialog(this);

        miUbicacion();

/*      Toast toast1=Toast.makeText(getApplicationContext(), "Prueba: "+listaobj, Toast.LENGTH_SHORT);
        toast1.setGravity(Gravity.CENTER,10,10);

        toast1.show();
*/        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        int estado = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (estado == ConnectionResult.SUCCESS) {

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

            mapFragment.getMapAsync(this);
        } else {
            //Dialog mjs = GooglePlayServicesUtil.getErrorDialog(estado, (Activity) getApplicationContext(), 10);
            //mjs.show();
        }


    }
    private void llenarUbicacion(){

        for(Edificio edificio : edificioList){
            listaobj.add(new Objetos(edificio.getNombre(),edificio.getLatitud(),edificio.getLongitud(),true,"sol"));
        }


        //listaobj.add(new Objetos("Ubicacion B",-2.2772470,-79.8914440,true,"nina"));
    }
    private void llenarMarker(){
        int b=0;
        LatLng a;

        for(Objetos ob:listaobj){
            b++;
            prueba = null;
            System.out.println("PROBAMOS");
            a = new LatLng(ob.latitud,ob.longitud);
            int imagen = getResources().getIdentifier(ob.icono, "drawable", getPackageName());
            if (ob.estado)
            {
                prueba = mMap.addMarker(new MarkerOptions().position(a).title(ob.nombre));
                prueba.showInfoWindow();
            }
            else {
                prueba = mMap.addMarker(new MarkerOptions().position(a).title(ob.nombre).icon(BitmapDescriptorFactory.fromResource(imagen)));
            }
        }
    }
    private void animarEspol() {
        LatLng espol = new LatLng(-2.1518811021216453, -79.95260821832615);

        CameraPosition camPos = new CameraPosition.Builder()
                .target(espol)   //Centramos el mapa en Madrid
                .zoom(15)         //Establecemos el zoom en 19
                .bearing(290)      //Establecemos la orientación con el noreste arriba
                .tilt(50)         //Bajamos el punto de vista de la cámara 70 grados
                .build();

        CameraUpdate camUpd3 =
                CameraUpdateFactory.newCameraPosition(camPos);

        mMap.animateCamera(camUpd3);


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker m) {

                Button btn1, btn2;
                myDialog.setContentView(see_or_evaluate);
                btn1 = (Button) myDialog.findViewById(R.id.see);
                btn2 = (Button) myDialog.findViewById(R.id.evaluate);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(myDialog.getContext(), BuildingActivity.class);
                        intent.putExtra("edificio", m.getTitle());

                        startActivity(intent);

                        myDialog.dismiss();
                    }
                });

                if (edificios_evaluados.contains(m.getTitle())){
                    btn2.setEnabled(false);
                }

                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(myDialog.getContext(), EvaluationActivity.class);
                        intent.putExtra("user", mAuth.getCurrentUser().getUid());
                        startActivity(intent);

                        myDialog.dismiss();
                    }
                });

                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                myDialog.show();

                return true;
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        llenarMarker();
        /*final LatLng espol = new LatLng(-2.1518811021216453, -79.95260821832615);
        final LatLng edcom = new LatLng(-2.143550400594397, -79.9621009999996);
        final LatLng fiec = new LatLng(-2.1462901, -79.9682957);
        mMap.addMarker(new MarkerOptions().position(espol).title("Bienvenidos a Espol").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mMap.addMarker(new MarkerOptions().position(edcom).title("EDCOM").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMap.addMarker(new MarkerOptions().position(fiec).title("FIEC").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));*/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location loc) {

                final LatLng x = new LatLng(loc.getLatitude(), loc.getLongitude());
                LatLng dir;
                for(Objetos ob:listaobj) {
                    dir = new LatLng(ob.latitud, ob.longitud);
                    if (ob.estado) {
                        double lat = dir.latitude;
                        double lon = dir.longitude; //Metodo para radios cruzados
                        if ((loc.getLatitude() > lat + x1 && lat + x2 > loc.getLatitude()) && (loc.getLongitude() > lon + y1 && lon + y2 > loc.getLongitude())) {
                            System.out.println("estoy en el rango");
                            prueba.remove();
                            //Intent in = new Intent(MapsActivity.this, Opcion.class);
                            //startActivity(in);
                            ob.estado = false;
                            llenarMarker();
                        } else {

                        }
                    }
                }



            }
        });
        animarEspol();
    }

    private void miUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},RETICION_PERMISO_LOCALIZACION);

            return;
        }
        else
        {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            actualizarubi(location);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1200,0,locListener);
        }
    }
    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            lat=location.getLatitude();
            lon=location.getLongitude();

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            mensaje = "GPS ACTIVADO";
            Mensaje();
        }

        @Override
        public void onProviderDisabled(String provider) {
            mensaje = "GPS Desactivado";
            locationStart();
            Mensaje();
        }
    };
    public void locationStart() {
        LocationManager mLocMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final boolean gps = mLocMan.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gps) {
            Intent in = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(in);
        }
    }
    public void Mensaje(){
        Toast toast=Toast.makeText(this, mensaje,Toast.LENGTH_LONG);
        toast.show();
    }
    public void actualizarubi(Location loc) {
        if (loc != null) {
            lat = loc.getLatitude();
            lon = loc.getLongitude();
        }
    }

}