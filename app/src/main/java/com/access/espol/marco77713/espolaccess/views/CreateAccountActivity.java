package com.access.espol.marco77713.espolaccess.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.access.espol.marco77713.espolaccess.MainActivity;
import com.access.espol.marco77713.espolaccess.R;
import com.access.espol.marco77713.espolaccess.model.User;
import com.access.espol.marco77713.espolaccess.views.fragments.MapsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class CreateAccountActivity extends AppCompatActivity {
    private EditText name, email_id, passwordcheck;
    private FirebaseAuth mAuth;
    private static final String TAG = "";
    private ProgressBar progressBar;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef2 = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        TextView btnSignUp = (TextView) findViewById(R.id.login_page);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#333333'>Crea una cuenta</font>"));
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.upbutton));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mAuth = FirebaseAuth.getInstance();

        email_id = (EditText) findViewById(R.id.input_email);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        passwordcheck = (EditText) findViewById(R.id.input_password);
        final Button ahsignup = (Button) findViewById(R.id.btn_signup);


        ahsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ahsignup.setEnabled(false);
                ahsignup.setBackground(getResources().getDrawable(R.drawable.btn_rounded_pushed));

                final String email = email_id.getText().toString();
                String password = passwordcheck.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Ingresa correo", Toast.LENGTH_SHORT).show();

                    ahsignup.setEnabled(true);
                    ahsignup.setBackground(getResources().getDrawable(R.drawable.btn_rounded));
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Ingresa contraseña", Toast.LENGTH_SHORT).show();

                    ahsignup.setEnabled(true);
                    ahsignup.setBackground(getResources().getDrawable(R.drawable.btn_rounded));
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                ConnectivityManager cm = (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

                if(isConnected) {

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(CreateAccountActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    progressBar.setVisibility(View.GONE);

                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        ArrayList<String> edificios_evaluados = new ArrayList<String>();
                                        edificios_evaluados.add("none");
                                        User userObject = new User(0, edificios_evaluados, email, 2);
                                        System.out.println(userObject.getEdificios_evaluados().size());
                                        myRef2.child("users").child(user.getUid()).setValue(userObject);


                                        Intent intent = new Intent(CreateAccountActivity.this, MapsActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(CreateAccountActivity.this, "Fallo, asegúrate que los datos sean correctos.",
                                                Toast.LENGTH_SHORT).show();

                                        ahsignup.setEnabled(true);
                                        ahsignup.setBackground(getResources().getDrawable(R.drawable.btn_rounded));

                                    }

                                }


                            });
                }
                else{
                    Toast.makeText(getBaseContext(), "No tienes acceso a internet, lo necesitas para hacer uso de la app", Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}

