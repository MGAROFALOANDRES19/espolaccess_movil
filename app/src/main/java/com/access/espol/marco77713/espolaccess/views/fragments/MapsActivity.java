package com.access.espol.marco77713.espolaccess.views.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.access.espol.marco77713.espolaccess.MainActivity;
import com.access.espol.marco77713.espolaccess.R;
import com.access.espol.marco77713.espolaccess.model.Edificio;
import com.access.espol.marco77713.espolaccess.model.Objetos;
import com.access.espol.marco77713.espolaccess.model.User;
import com.access.espol.marco77713.espolaccess.views.BuildingActivity;
import com.access.espol.marco77713.espolaccess.views.EvaluationActivity;
import com.access.espol.marco77713.espolaccess.views.IntroductionActivity;
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

import java.util.ArrayList;
import java.util.List;

import static com.access.espol.marco77713.espolaccess.R.layout.info_popup;
import static com.access.espol.marco77713.espolaccess.R.layout.see_or_evaluate;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    //DECLARACION DE VARIABLES

    private GoogleMap mMap;
    private Marker prueba; //*
    double x1=-0.00020;//solo cambiar penultimo digito 115 => 125 OJO 195
    double x2= 0.00020;
    double y1=-0.00020;
    double y2= 0.00020;
    private ArrayList<Objetos> listaobj = new ArrayList<Objetos>(); //*
    public ArrayList<String> edificios_evaluados = new ArrayList<>(); //*
    private static int RETICION_PERMISO_LOCALIZACION = 101;
    private double lat, lon;
    private String mensaje;
    private boolean puedeEvaluar = false;
    int i =0; //*
    Boolean x1_l = false, x2_l = false, y1_l = false, y2_l = false;


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef2;
    private  User user; //*
    private List<Edificio> edificioList = new ArrayList<>();
    private Dialog myDialog;
    public BottomNavigationView bottomBar;
    private ImageView imageView, imageViewInformation;
    private Toolbar toolbar;
    private SearchFragment searchFragment = new SearchFragment();
    private ProfileFragment profileFragment = new ProfileFragment(mAuth);
    private ChallengeFragment challengeFragment = new ChallengeFragment();
    private FrameLayout frameLayout;
    private Dialog infoDialog;

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
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        this.setViews();

        this.setEvents();

        ConnectivityManager cm = (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
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

        if (isConnected) {


            new AsyncCaller().execute();

            ////////////////////LLAMADA A LA BASE DE DATOS


            myDialog = new Dialog(this);
            infoDialog = new Dialog(this);

            miUbicacion();

/*      Toast toast1=Toast.makeText(getApplicationContext(), "Prueba: "+listaobj, Toast.LENGTH_SHORT);
        toast1.setGravity(Gravity.CENTER,10,10);/
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

        else{
            Toast.makeText(getBaseContext(), "No tienes acceso a internet, lo necesitas para hacer uso de la app", Toast.LENGTH_LONG).show();
        }

    }

    private void setFragment(Fragment fragment, Spanned html, ColorDrawable colorDrawable){
        frameLayout.setVisibility(View.VISIBLE);

        getImageViewInformation().setVisibility(View.INVISIBLE);
        getImageView().setVisibility(View.INVISIBLE);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setTitle(html);
        getSupportActionBar().show();

    }

    private void setEvents() {

        this.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setFragment(getChallengeFragment(), Html.fromHtml("<font color='#ffffff'>Premios</font>"), new ColorDrawable(getResources().getColor(R.color.editTextColorBlue)));
                bottomBar.setSelectedItemId(R.id.challenge);

                //startActivity(new Intent(MapsActivity.this, IntroductionActivity.class));
               // finish();
            }
        });

        /*this.getImageViewInformation().setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getImageViewInformation().setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.info_button_pushed));


                Button btnClose;
                infoDialog.setContentView(info_popup);
                btnClose = (Button) infoDialog.findViewById(R.id.btn_closeInfo);

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        getImageViewInformation().setBackground(getResources().getDrawable(R.drawable.info_button));

                        infoDialog.dismiss();
                    }
                });

                infoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                infoDialog.show();
            }
        });
*/
        this.getBottomBar().setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.challenge:
                        setFragment(getChallengeFragment(), Html.fromHtml("<font color='#ffffff'>Premios</font>"), new ColorDrawable(getResources().getColor(R.color.editTextColorBlue)));
                        break;
                    case R.id.maps:
                        //getPuntos();
                        //MapsActivity mapsActivity = new MapsActivity();
                        //changeFragment(mapsActivity);
                        getSupportActionBar().hide();
                        frameLayout.setVisibility(View.INVISIBLE);
                        getImageView().setVisibility(View.VISIBLE);
                        getImageViewInformation().setVisibility(View.VISIBLE);
                        break;
                    case R.id.search:
                        setFragment(getSearchFragment(), Html.fromHtml("<font color='#3333'>Buscar aquí...</font>"), new ColorDrawable(getResources().getColor(R.color.white)));
                        break;
                    case R.id.profile:
                        getProfileFragment().userClass = user;
                        setFragment(getProfileFragment(), Html.fromHtml("<font color='#ffffff'>Perfil</font>"), new ColorDrawable(getResources().getColor(R.color.editTextColorBlue)));
                        break;

                }

                return true;
            }
        });

        bottomBar.setSelectedItemId(R.id.maps);

    }

    private void setViews() {

        this.setToolbar((Toolbar) findViewById(R.id.toolbar));
        setSupportActionBar(this.getToolbar());
        getSupportActionBar().setTitle("");
        getSupportActionBar().hide();

        this.setImageView((ImageView)findViewById(R.id.edificios_evaluados));
        this.setImageViewInformation((ImageView) findViewById(R.id.information));
        this.setBottomBar ((BottomNavigationView) findViewById(R.id.bottom_navigation));

        frameLayout = (FrameLayout) findViewById(R.id.container);


    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        bottomBar.setSelectedItemId(R.id.maps);
    }

    private void llenarUbicacion(){

        for(Edificio edificio : edificioList){
            listaobj.add(new Objetos(edificio.getNombre(),edificio.getLatitud(),edificio.getLongitud(),false,edificio.getResultado_accesibilidad()));

        }
        System.out.println(edificioList);

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
//            int imagen = getResources().getIdentifier(ob.icono, "drawable", getPackageName());
            if (ob.resultado_accesbilidad == 0)
            {
                prueba = mMap.addMarker(new MarkerOptions().position(a).title(ob.nombre).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_no_evaluado)));
                prueba.showInfoWindow();
            }
            else if(ob.resultado_accesbilidad == 1){ //CAMBIO DE IMAGEN
                prueba = mMap.addMarker(new MarkerOptions().position(a).title(ob.nombre).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_no_accesible)));
            }
            else if(ob.resultado_accesbilidad == 2){ //CAMBIO DE IMAGEN
                prueba = mMap.addMarker(new MarkerOptions().position(a).title(ob.nombre).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_medianamente_accesible)));
            }
            else if(ob.resultado_accesbilidad == 3){ //CAMBIO DE IMAGEN
                prueba = mMap.addMarker(new MarkerOptions().position(a).title(ob.nombre).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_accesible)));
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
                TextView textView;
                myDialog.setContentView(see_or_evaluate);
                btn1 = (Button) myDialog.findViewById(R.id.see);
                btn2 = (Button) myDialog.findViewById(R.id.evaluate);
                textView = (TextView) myDialog.findViewById(R.id.notEvaluated);

                for (Edificio edificio : edificioList){
                    if(edificio.getNombre().equals(m.getTitle())){
                        if (edificio.getResultado_accesibilidad() == 0){
                            textView.setText("Edificio no evaluado");
                        }
                        else if (edificio.getResultado_accesibilidad() == 1){
                            textView.setText("Edificio no accesible");
                        }
                        else if (edificio.getResultado_accesibilidad() == 2){
                            textView.setText("Edificio medianamente accesible");
                        }
                        else if (edificio.getResultado_accesibilidad() == 0){
                            textView.setText("Edificio accesible");
                        }
                    }
                }

                if (user.getEdificios_evaluados().contains(m.getTitle())){
                    btn2.setEnabled(false);
                    btn2.setText("Ya evaluado");
                    btn2.setTextColor(Color.parseColor("#7f7b6d"));
                    btn2.setBackgroundColor(Color.parseColor("#333333"));

                }

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
                        System.out.println("NO TE DETENTGAS " + user.getEdificios_evaluados().size());
                        Intent intent = new Intent(myDialog.getContext(), EvaluationActivity.class);
                        intent.putExtra("user", mAuth.getCurrentUser().getUid());
                        intent.putExtra("edificio", m.getTitle());
                        intent.putExtra("n_edificios_evaluados", user.getEdificios_evaluados().size());
                        intent.putExtra("user_puntos", user.getPuntos());

                        for(Objetos ob:listaobj){
                            if (ob.getNombre().equals(m.getTitle())){
                                if(ob.estado){
startActivity(intent);
System.out.println("POR QUE TENGO DOS ACTIVITIES");
                                    x1_l = false;
                                    x2_l = false;
                                    y1_l = false;
                                    y2_l = false;
                                    ob.estado = false;
                                    puedeEvaluar = false;
                                    break;
                                }
                                else{
                                    ob.estado=false;
                                    Toast.makeText(getBaseContext(), "Acércate a la ubicación para evaluar", Toast.LENGTH_LONG).show();
                                }

                            }
                        }

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
                    if (!ob.estado/* && !puedeEvaluar*/) {
                        double lat = dir.latitude;
                        double lon = dir.longitude; //Metodo para radios cruzados


/*
                        if (!x1_l) {
                            if (loc.getLatitude() > lat + x1) {
                                x1_l = true;
                                System.out.println("ENTRAMOS1 " + ob);
                                objetos_name.add(ob.getNombre());
                            }
                        }

                        if (!x2_l) {
                            if (lat + x2 > loc.getLatitude()) {
                                x2_l = true;
                                System.out.println("ENTRAMOS2 " + ob);
                            }
                        }

                        if (!y1_l) {
                            if (loc.getLongitude() > lon + y1) {
                                y1_l = true;
                                System.out.println("ENTRAMOS3 " + ob);
                            }
                        }

                        if (!y2_l) {
                            if (lon + y2 > loc.getLongitude()) {
                                y2_l = true;
                                System.out.println("ENTRAMOS4 " + ob);
                            }
                        }
//3 TRUES Y DOS NOMBRES*/
                        if ((loc.getLatitude() > lat + x1 && lat + x2 > loc.getLatitude()) && (lat + x2 > loc.getLatitude() && lon + y2 > loc.getLongitude())) {
                            System.out.println("estoy en el rango");
                            prueba.remove();


                            ob.estado = true;

                            // SE REALIZA VALIDACION

                            //Intent in = new Intent(MapsActivity.this, Opcion.class);
                            //startActivity(in);
                            System.out.println(ob);
                            puedeEvaluar = true;
                            llenarMarker();
                        } else {
                            ob.estado= false;
                        }
                    }
                }



            }
        });


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
            Intent intent = new Intent(getBaseContext(), MapsActivity.class);
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            startActivity(intent);
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

    public GoogleMap getmMap() {
        return mMap;
    }

    public void setmMap(GoogleMap mMap) {
        this.mMap = mMap;
    }

    public Marker getPrueba() {
        return prueba;
    }

    public void setPrueba(Marker prueba) {
        this.prueba = prueba;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public ArrayList<Objetos> getListaobj() {
        return listaobj;
    }

    public void setListaobj(ArrayList<Objetos> listaobj) {
        this.listaobj = listaobj;
    }

    public ArrayList<String> getEdificios_evaluados() {
        return edificios_evaluados;
    }

    public void setEdificios_evaluados(ArrayList<String> edificios_evaluados) {
        this.edificios_evaluados = edificios_evaluados;
    }

    public static int getReticionPermisoLocalizacion() {
        return RETICION_PERMISO_LOCALIZACION;
    }

    public static void setReticionPermisoLocalizacion(int reticionPermisoLocalizacion) {
        RETICION_PERMISO_LOCALIZACION = reticionPermisoLocalizacion;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isPuedeEvaluar() {
        return puedeEvaluar;
    }

    public void setPuedeEvaluar(boolean puedeEvaluar) {
        this.puedeEvaluar = puedeEvaluar;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public void setDatabase(FirebaseDatabase database) {
        this.database = database;
    }

    public DatabaseReference getMyRef() {
        return myRef;
    }

    public void setMyRef(DatabaseReference myRef) {
        this.myRef = myRef;
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    public FirebaseAuth.AuthStateListener getmAuthListener() {
        return mAuthListener;
    }

    public void setmAuthListener(FirebaseAuth.AuthStateListener mAuthListener) {
        this.mAuthListener = mAuthListener;
    }

    public DatabaseReference getMyRef2() {
        return myRef2;
    }

    public void setMyRef2(DatabaseReference myRef2) {
        this.myRef2 = myRef2;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Edificio> getEdificioList() {
        return edificioList;
    }

    public void setEdificioList(List<Edificio> edificioList) {
        this.edificioList = edificioList;
    }

    public Dialog getMyDialog() {
        return myDialog;
    }

    public void setMyDialog(Dialog myDialog) {
        this.myDialog = myDialog;
    }

    public BottomNavigationView getBottomBar() {
        return bottomBar;
    }

    public void setBottomBar(BottomNavigationView bottomBar) {
        this.bottomBar = bottomBar;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getImageViewInformation() {
        return imageViewInformation;
    }

    public void setImageViewInformation(ImageView imageViewInformation) {
        this.imageViewInformation = imageViewInformation;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    public SearchFragment getSearchFragment() {
        return searchFragment;
    }

    public void setSearchFragment(SearchFragment searchFragment) {
        this.searchFragment = searchFragment;
    }

    public ProfileFragment getProfileFragment() {
        return profileFragment;
    }

    public void setProfileFragment(ProfileFragment profileFragment) {
        this.profileFragment = profileFragment;
    }

    public ChallengeFragment getChallengeFragment() {
        return challengeFragment;
    }

    public void setChallengeFragment(ChallengeFragment challengeFragment) {
        this.challengeFragment = challengeFragment;
    }

    public LocationListener getLocListener() {
        return locListener;
    }

    public void setLocListener(LocationListener locListener) {
        this.locListener = locListener;
    }

    public FrameLayout getFrameLayout() {
        return frameLayout;
    }

    public void setFrameLayout(FrameLayout frameLayout) {
        this.frameLayout = frameLayout;
    }

    public Dialog getInfoDialog() {
        return infoDialog;
    }

    public void setInfoDialog(Dialog infoDialog) {
        this.infoDialog = infoDialog;
    }



    private class AsyncCaller extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void...voids) {

            myRef2 = database.getReference("users/" + String.valueOf(mAuth.getCurrentUser().getUid()));

            myRef2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    System.out.println("QUE PASO " + dataSnapshot.getValue());
                    user = dataSnapshot.getValue(User.class);
                    System.out.println("" + user.getEdificios_evaluados().size());
                    //mapFragment.edificios_evaluados = user.getEdificios_evaluados();

                    switch (user.getEdificios_evaluados().size()) {
                        case (1):
                            imageView.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.edificios_evaluados0));
                            break;
                        case (2):
                            imageView.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.edificios_evaluados1));
                            break;
                        case (3):
                            imageView.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.edificios_evaluados2));
                            break;
                        case (4):
                            imageView.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.edificios_evaluados3));
                            break;
                        case (5):
                            imageView.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.edificios_evaluados4));
                            break;


                    }
                    //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    System.out.println("Failed to read value." + error.toException());
                }


            });
            return null;
        }

    }
    private class AsyncMarker extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void...voids) {

            myRef2 = database.getReference("users/" + String.valueOf(mAuth.getCurrentUser().getUid()));

            myRef2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    System.out.println("QUE PASO " + dataSnapshot.getValue());
                    user = dataSnapshot.getValue(User.class);
                    System.out.println("" + user.getEdificios_evaluados().size());
                    //mapFragment.edificios_evaluados = user.getEdificios_evaluados();

                    switch (user.getEdificios_evaluados().size()) {
                        case (1):
                            imageView.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.edificios_evaluados0));
                            break;
                        case (2):
                            imageView.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.edificios_evaluados1));
                            break;
                        case (3):
                            imageView.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.edificios_evaluados2));
                            break;
                        case (4):
                            imageView.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.edificios_evaluados3));
                            break;
                        case (5):
                            imageView.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.edificios_evaluados4));
                            break;


                    }
                    //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    System.out.println("Failed to read value." + error.toException());
                }


            });
            return null;
        }

    }
}

