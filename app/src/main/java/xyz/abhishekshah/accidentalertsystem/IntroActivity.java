package xyz.abhishekshah.accidentalertsystem;


import android.content.Intent;
import android.os.Bundle;
        import android.widget.Toast;

        import com.chyrta.onboarder.OnboarderActivity;
        import com.chyrta.onboarder.OnboarderPage;

        import java.util.ArrayList;
        import java.util.List;

public class IntroActivity extends OnboarderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnboarderPage onboarderPage1 = new OnboarderPage("WOMAN SAFETY\n", "The woman safety module uses the features like GPS and SOS of the application and can be used in any case.\n", R.drawable.woman);
        OnboarderPage onboarderPage2 = new OnboarderPage("SOS Auto Triggers\n", "The SOS feature will notify emergency responders immediately on RTA(Road Traffic Accident). \n", R.drawable.sos1);
        OnboarderPage onboarderPage3 = new OnboarderPage("Real Time Data Telemetry\n", "Real time vehicle metrics like Car Speed, Fuel Level, Brake System Warnings can be shared and accessed from any remote locations.\n", R.drawable.data);

        onboarderPage1.setBackgroundColor(R.color.onboarder_bg_1);
        onboarderPage2.setBackgroundColor(R.color.onboarder_bg_2);
        onboarderPage3.setBackgroundColor(R.color.onboarder_bg_3);

        List<OnboarderPage> pages = new ArrayList<>();

        pages.add(onboarderPage1);
        pages.add(onboarderPage2);
        pages.add(onboarderPage3);

        for (OnboarderPage page : pages) {
            page.setTitleColor(R.color.primary_text);
            page.setDescriptionColor(R.color.secondary_text);
            //page.setMultilineDescriptionCentered(true);
        }

        setSkipButtonTitle("Skip");
        setFinishButtonTitle("Finish");

        setOnboardPagesReady(pages);

    }

    @Override
    public void onSkipButtonPressed() {
        super.onSkipButtonPressed();
        Toast.makeText(this, "Skip button was pressed!", Toast.LENGTH_SHORT).show();
        Intent startIntent=new Intent(getApplicationContext(),MainActivity.class);

        startActivity(startIntent);
    }

    @Override
    public void onFinishButtonPressed() {
        Toast.makeText(this, "Finish button was pressed", Toast.LENGTH_SHORT).show();
        Intent startIntent1=new Intent(getApplicationContext(),MainActivity.class);

        startActivity(startIntent1);
    }

}