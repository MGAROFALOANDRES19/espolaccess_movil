package com.access.espol.marco77713.espolaccess.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.access.espol.marco77713.espolaccess.R;
import com.access.espol.marco77713.espolaccess.model.Edificio;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BuildingActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myRef;

    TextView txtResultado, txtAscensor, txtBanos, txtParqueaderos, txtRampas;

    String edificio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        Intent intent = getIntent();
        edificio = intent.getStringExtra("edificio");


        this.setViews();


        //DATABASE CALL AND WRITTING
        myRef = database.getReference("edificios/" + edificio);
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Edificio edificio = dataSnapshot.getValue(Edificio.class);

                txtResultado.setText(String.valueOf(edificio.getResultado_accesibilidad()));
                txtAscensor.setText("" + edificio.isAscensor());
                txtBanos.setText(""+ edificio.isBanos_discapacidad());
                txtParqueaderos.setText("" + edificio.isParqueaderos());
                txtRampas.setText("" + edificio.isRampas());

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value." + error.toException());
            }
        });


        showToolbar(edificio, true);
    }

    private void setViews() {
        this.txtResultado = (TextView) findViewById(R.id.resultado_accesibilidad);
        this.txtAscensor = (TextView) findViewById(R.id.ascensor);
        this.txtBanos = (TextView) findViewById(R.id.banos_discapacidad);
        this.txtParqueaderos = (TextView) findViewById(R.id.parqueaderos);
        this.txtRampas = (TextView) findViewById(R.id.rampas);
    }


    public void  showToolbar(String title, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }



}
