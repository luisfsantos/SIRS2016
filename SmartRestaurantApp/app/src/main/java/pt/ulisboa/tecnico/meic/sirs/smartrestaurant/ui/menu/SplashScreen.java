package pt.ulisboa.tecnico.meic.sirs.smartrestaurant.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

import pt.ulisboa.tecnico.meic.sirs.smartrestaurant.R;

/**
 * Created by Catarina on 15/11/2016.
 */

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, SessionStartActivity.class);
                startActivity(i);
                finish();
                }
        }, SPLASH_TIME_OUT);
    }
}