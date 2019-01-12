package com.access.espol.marco77713.espolaccess.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;

import com.access.espol.marco77713.espolaccess.MainActivity;
import com.access.espol.marco77713.espolaccess.R;
import com.access.espol.marco77713.espolaccess.model.SampleSlide;
import com.github.paolorotolo.appintro.AppIntro;

public class IntroductionActivity extends AppIntro {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//CON ESTO SOLO CORRE UNA VEZ LA ACTIVIDAD
        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        if(pref.getBoolean("activity_executed", false)){
            Intent intent = new Intent(IntroductionActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            SharedPreferences.Editor ed = pref.edit();
            ed.putBoolean("activity_executed", true);
            ed.commit();
        }


        //SE ANADEN LOS SLIDES A LA INTRODUCCION
        addSlide(SampleSlide.newInstance(R.layout.intro_1));
        addSlide(SampleSlide.newInstance(R.layout.intro_2));
        addSlide(SampleSlide.newInstance(R.layout.intro_3));
        addSlide(SampleSlide.newInstance(R.layout.intro_4));

        setSkipText("Saltar");
        showSkipButton(false);
        setDoneText("Listo");


        //ATENCION CON ESTA LINEA QUE NO ESTA SIRVIENDO
        askForPermissions(new String[]{Manifest.permission.CAMERA}, 1);

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
      finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(IntroductionActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

}
