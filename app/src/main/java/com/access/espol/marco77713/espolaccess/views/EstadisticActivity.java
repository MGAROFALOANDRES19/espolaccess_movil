package com.access.espol.marco77713.espolaccess.views;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telecom.TelecomManager;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.access.espol.marco77713.espolaccess.R;
import com.access.espol.marco77713.espolaccess.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EstadisticActivity extends AppCompatActivity {


    int edificios_evaluados;
    String nombre;
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadistic);

        nombre = getIntent().getStringExtra("nombre");
        edificios_evaluados = getIntent().getIntExtra("edificios_evaluados", 0);
        edificios_evaluados--;

        Toolbar toolbar = ((Toolbar) findViewById(R.id.toolbar));
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.editTextColorBlue)));
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.upbutton));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'> Estadísticas de " + nombre  + "</font>"));

        imageView = (ImageView) findViewById(R.id.percentage_circle);
        textView = (TextView) findViewById(R.id.edificios_evaluados_number);

        textView.setText("Edificios evaluados: " + edificios_evaluados);

        new AsyncCaller().execute();
    }

    private class AsyncCaller extends AsyncTask<Void, Void, Drawable>
    {

        private FirebaseDatabase database = FirebaseDatabase.getInstance();
        private DatabaseReference myRef;
        int total_edificios;
        int percentage;
        Drawable drawable;

        @Override
        protected Drawable doInBackground(Void...voids) {

            myRef = database.getReference("edificios/");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                        total_edificios = (int) dataSnapshot.getChildrenCount();

                        System.out.println("Edificios evaluados" + total_edificios);
                        System.out.println("Edificios evaluados" + edificios_evaluados);

                    percentage = (int) (Math.round((edificios_evaluados * 100 / total_edificios)/10.0)*10);

                    switch (percentage){
                        case (0):
                            drawable = getResources().getDrawable(R.drawable.percentage_00_circle);
                            break;
                        case (10):
                            drawable = getResources().getDrawable(R.drawable.percentage_10_circle);
                            break;
                        case (20):
                            drawable = getResources().getDrawable(R.drawable.percentage_20_circle);
                            break;
                        case (30):
                            drawable = getResources().getDrawable(R.drawable.percentage_30_circle);
                            break;
                        case (40):
                            drawable = getResources().getDrawable(R.drawable.percentage_40_circle);
                            break;
                        case (50):
                            drawable = getResources().getDrawable(R.drawable.percentage_50_circle);
                            break;
                        case (60):
                            drawable = getResources().getDrawable(R.drawable.percentage_60_circle);
                            break;
                        case (70):
                            drawable = getResources().getDrawable(R.drawable.percentage_70_circle);
                            break;
                        case (80):
                            drawable = getResources().getDrawable(R.drawable.percentage_80_circle);
                            break;
                        case (90):
                            drawable = getResources().getDrawable(R.drawable.percentage_90_circle);
                            break;
                        case (100):
                            drawable = getResources().getDrawable(R.drawable.percentage_90_circle);
                            break;
                    }

                    imageView.setBackground(drawable);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

        });



            return drawable;
    }
}
}
