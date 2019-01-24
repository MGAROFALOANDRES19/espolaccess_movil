package com.access.espol.marco77713.espolaccess.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.access.espol.marco77713.espolaccess.MainActivity;
import com.access.espol.marco77713.espolaccess.R;
import com.access.espol.marco77713.espolaccess.views.fragments.intro.Intro0Fragment;
import com.access.espol.marco77713.espolaccess.views.fragments.intro.Intro1Fragment;
import com.access.espol.marco77713.espolaccess.views.fragments.intro.Intro2Fragment;
import com.access.espol.marco77713.espolaccess.views.fragments.intro.Intro3Fragment;
import com.access.espol.marco77713.espolaccess.views.fragments.intro.Intro4Fragment;
import com.access.espol.marco77713.espolaccess.views.fragments.intro.Intro_1Fragment;


public class IntroductionActivity extends /*AppIntro*/ AppCompatActivity {

    Button btnJump, btnNext, btnClose;
int contIntro = 0;
    Intro0Fragment intro0Fragment;
    Intro_1Fragment intro_1Fragment;
    Intro1Fragment intro1Fragment;
    Intro2Fragment intro2Fragment;
    Intro3Fragment intro3Fragment;
    Intro4Fragment intro4Fragment;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);


        this.setViews();
        this.setEvents();

        intro1Fragment = new Intro1Fragment();
        intro2Fragment = new Intro2Fragment();
        intro3Fragment = new Intro3Fragment();
        intro4Fragment = new Intro4Fragment();
        intro0Fragment = new Intro0Fragment();
        intro_1Fragment = new Intro_1Fragment();

        this.setFragmentIntro(intro0Fragment, "Informar");
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
/*        addSlide(SampleSlide.newInstance(R.layout.intro_1));
        addSlide(SampleSlide.newInstance(R.layout.intro_2));
        addSlide(SampleSlide.newInstance(R.layout.intro_3));
        addSlide(SampleSlide.newInstance(R.layout.intro_4));

        setSkipText("Saltar");
        showSkipButton(false);
        setDoneText("Listo");


        //ATENCION CON ESTA LINEA QUE NO ESTA SIRVIENDO
        askForPermissions(new String[]{Manifest.permission.CAMERA}, 1);
*/
    }


    private void setViews() {
        btnJump = (Button) findViewById(R.id.jump);
        btnNext = (Button) findViewById(R.id.next);
        btnClose = (Button) findViewById(R.id.close_credits);
System.out.println(btnJump);
    }


    private void setEvents() {

    }

    public void nextSlide(View view) {

        this.setEnabled(this.btnNext);

        if(this.btnNext.getTag().equals("Listo")){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        else {
            contIntro++;
            this.setSlide(contIntro);
        }
        this.setEnabled(this.btnNext);
        //btnNext.setBackground(getResources().getDrawab
    }

    private void setEnabled(Button btnNext) {

        if(btnNext.isEnabled()) {
            btnNext.setEnabled(false);
            btnNext.setBackground(getResources().getDrawable(R.drawable.btn_rounded_pushed));
        }
        else{
            btnNext.setEnabled(true);
            btnNext.setBackground(getResources().getDrawable(R.drawable.btn_rounded));
        }

    }

    private void setFragmentIntro(Fragment fragment, String saltar) {
        btnJump.setTag(saltar);
        btnJump.setText(saltar);

        if (saltar == "Cerrar"){
            btnNext.setVisibility(View.INVISIBLE);
            btnJump.setVisibility(View.INVISIBLE);
            btnClose.setVisibility(View.VISIBLE);
        }
        else {
            btnNext.setVisibility(View.VISIBLE);
            btnJump.setVisibility(View.VISIBLE);
            btnClose.setVisibility(View.INVISIBLE);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container_intro, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null).commit();
    }

    public void jumpIntro(View view) {
        this.setEnabled(this.btnJump);
        if (view.getTag().equals("Saltar")){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        else if(view.getTag().equals("Anterior")){
            contIntro--;
            this.setSlide(contIntro);
        }
        else if(view.getTag().equals("Informar")){
            contIntro = 5;
            this.setSlide(contIntro);
        }
        else if(view.getTag().equals("Cerrar")){
            contIntro = 0;
            this.setSlide(contIntro);
        }
        this.setEnabled(this.btnJump);
    }

    private void setSlide(int contIntro) {


        if (contIntro == 0){

            this.setFragmentIntro(intro0Fragment, "Informar");

        }
        else if (contIntro == 5){

            this.setFragmentIntro(intro_1Fragment, "Cerrar");

        }
        else if (contIntro == 1){

            this.setFragmentIntro(intro1Fragment, "Saltar");

        }
        else if (contIntro == 2){

            this.setFragmentIntro(intro2Fragment, "Anterior");

        }
        else if (contIntro == 3){

            this.setFragmentIntro(intro3Fragment, "Anterior");

        }
        else if (contIntro == 4){

            this.setFragmentIntro(intro4Fragment, "Anterior");
            this.btnNext.setText("Listo");
            this.btnNext.setTag("Listo");
        }
    }



    /*
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
*/
}
