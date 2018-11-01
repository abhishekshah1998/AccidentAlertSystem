package xyz.abhishekshah.accidentalertsystem;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class Pop3 extends Activity {

    String textedit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow3);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.6));
        TextView text=(TextView)findViewById(R.id.textView);
        textedit=getIntent().getExtras().getString("value4");
        text.setText(getIntent().getExtras().getString("value4"));
    }
}
