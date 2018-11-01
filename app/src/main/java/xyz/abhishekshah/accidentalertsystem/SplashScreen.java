package xyz.abhishekshah.accidentalertsystem;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        icon = (ImageView) findViewById(R.id.imgLogo);
        long animationduration = 3000;


        ObjectAnimator animatoralpha = ObjectAnimator.ofFloat(icon,View.ALPHA,0.0f,1.0f);
        animatoralpha.setDuration(animationduration);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatoralpha);
        animatorSet.start();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, IntroActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}