package xyz.abhishekshah.accidentalertsystem;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class womensafety extends AppCompatActivity {


    private Button yes;
    private Button no;
    DatabaseHelper myDB;
    String contactNumber[]=new String[4];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_womensafety);

        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);
        myDB = new DatabaseHelper(this);


        Cursor res= myDB.getData();
        StringBuffer buffer = new StringBuffer();
        int count=0;





        while (res.moveToNext() && count<4){

            //   buffer.append("Id :"+ res.getString(0)+"\n");
            buffer.append("Name :"+ res.getString(1)+"\n");
            buffer.append("Phone :"+ res.getString(2)+"\n");

            contactNumber[count]=res.getString(2);
            count++;
        }
        count=0;










        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(startintent);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

                    sendMySMS();
                }














            }
        });






    }

    public void sendMySMS() {

        for(int i=0;i<4;i++){

            String phone = contactNumber[i];
            String message = "I have met with an accident at: https://www.google.com/maps/search/?api=1&query=18.489894,73.852447";

            //Check if the phoneNumber is empty
            if (phone.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please select all four contacts.", Toast.LENGTH_SHORT).show();
            } else {

                SmsManager sms = SmsManager.getDefault();
                // if message length is too long messages are divided
                List<String> messages = sms.divideMessage(message);
                for (String msg : messages) {

                    PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT"), 0);
                    PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED"), 0);
                    sms.sendTextMessage(phone, null, msg, sentIntent, deliveredIntent);

                }
            }

        }

    }














}
