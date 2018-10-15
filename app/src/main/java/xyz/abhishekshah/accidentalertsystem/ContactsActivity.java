package xyz.abhishekshah.accidentalertsystem;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


import java.util.List;
import static android.Manifest.permission.SEND_SMS;


public class ContactsActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    Button ViewButton,DeleteButton,LoadButton,SendButton;
    private static final int MISSED_CALL_TYPE = 0;

    String contactNumber[]=new String[4];

    private static final int RESULT_PICK_CONTACT = 1;
    private static final int REQUEST_SMS = 0;

    private BroadcastReceiver sentStatusReceiver, deliveredStatusReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        ViewButton=  findViewById(R.id.ViewButton);
        DeleteButton=findViewById(R.id.delete_button);
        LoadButton=findViewById(R.id.load_button);
        SendButton=findViewById(R.id.send_button);





        myDB = new DatabaseHelper(this);


        ViewData();

        DeleteData();
        pickContact();

        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    int hasSMSPermission = checkSelfPermission(Manifest.permission.SEND_SMS);
                    if (hasSMSPermission != PackageManager.PERMISSION_GRANTED) {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)) {
                            showMessageOKCancel("You need to allow access to Send SMS",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[] {Manifest.permission.SEND_SMS},
                                                        REQUEST_SMS);
                                            }
                                        }
                                    });
                            return;
                        }
                        requestPermissions(new String[] {Manifest.permission.SEND_SMS},
                                REQUEST_SMS);
                        return;
                    }
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

    public void onResume() {
        super.onResume();
        sentStatusReceiver=new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent arg1) {
                String s = "Unknown Error";
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        s = "Message Sent Successfully !!";
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        s = "Generic Failure Error";
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        s = "Error : No Service Available";
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        s = "Error : Null PDU";
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        s = "Error : Radio is off";
                        break;
                    default:
                        break;
                }
               // sendStatusTextView.setText(s);

            }
        };
        deliveredStatusReceiver=new BroadcastReceiver() {

            @Override
            public void onReceive(Context arg0, Intent arg1) {
                String s = "Message Not Delivered";
                switch(getResultCode()) {
                    case Activity.RESULT_OK:
                        s = "Message Delivered Successfully";
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                }
               // deliveryStatusTextView.setText(s);
               // phoneEditText.setText("");
               // messageEditText.setText("");
            }
        };
        registerReceiver(sentStatusReceiver, new IntentFilter("SMS_SENT"));
        registerReceiver(deliveredStatusReceiver, new IntentFilter("SMS_DELIVERED"));
    }



    public void onPause() {
        super.onPause();
        unregisterReceiver(sentStatusReceiver);
        unregisterReceiver(deliveredStatusReceiver);
    }



    private boolean checkPermission() {
        return ( ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS ) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{SEND_SMS}, REQUEST_SMS);
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_SMS:
                if (grantResults.length > 0 &&  grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access sms", Toast.LENGTH_SHORT).show();
                    sendMySMS();

                }else {
                    Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and sms", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(SEND_SMS)) {
                            showMessageOKCancel("You need to allow access to both the permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{SEND_SMS},
                                                        REQUEST_SMS);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                builder.setMessage(message);
                builder.setPositiveButton("OK", okListener);
                builder.setNegativeButton("Cancel", null);
                android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.show();


    }

    public void pickContact()
    {
       LoadButton.setOnClickListener(
               new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                       Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                               ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                       startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);

                   }
               }
       );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check whether the result is ok
        if (resultCode == RESULT_OK) {
            // Check for the request code, we might be usign multiple startActivityForReslut
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }
    /**
     * Query the Uri and read contact details. Handle the picked contact data.
     * @param data
     */
    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            String phoneNo = null ;
            String name = null;
            // getData() method will have the Content Uri of the selected contact
            Uri uri = data.getData();
            //Query the content uri
            cursor = getContentResolver().query(uri, null, null, null, null);
           cursor.moveToFirst();
            // column index of the phone number
            int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            // column index of the contact name
            int  nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phoneIndex);
            name = cursor.getString(nameIndex);
            // Set the value to the textviews
            boolean isInserted= myDB.addData(name,phoneNo);

            if(isInserted==true) {
                Toast.makeText(ContactsActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
            }
            else {

                Toast.makeText(ContactsActivity.this,"Data NOT Inserted",Toast.LENGTH_LONG).show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //SQLite Functions
    public void DeleteData(){
        DeleteButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer isDeleted=myDB.deleteData();


                        if(isDeleted>0) {

                            Toast.makeText(ContactsActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();

                            for(int i=0;i<4;i++){
                                contactNumber[i]="";


                            }
                        }
                        else {

                            Toast.makeText(ContactsActivity.this,"Data NOT Deleted",Toast.LENGTH_LONG).show();

                        }
                    }
                }

        );

    }

/*    public void UpdateData(){
        UpdateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdated = myDB.updateData(editId.getText().toString(),editName.getText().toString(),editNumber.getText().toString());

                        if(isUpdated==true ) {
                            Toast.makeText(ContactsActivity.this,"Data is Updated",Toast.LENGTH_LONG).show();
                        }
                        else {

                            Toast.makeText(ContactsActivity.this,"Data NOT Updated",Toast.LENGTH_LONG).show();

                        }

                    }
                }
        );

    }*/

 /*   public void AddData(){

        AddButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       boolean isInserted= myDB.addData(editName.getText().toString(),editNumber.getText().toString());

                       if(isInserted==true) {
                           Toast.makeText(ContactsActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                       }
                       else {

                           Toast.makeText(ContactsActivity.this,"Data NOT Inserted",Toast.LENGTH_LONG).show();

                       }
                    }
                }
        );
    }*/

    public void ViewData(){
        ViewButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Cursor res= myDB.getData();
                        StringBuffer buffer = new StringBuffer();
                        int count=0;

                       if(res.getCount()==0){
                           showMessage("Error","Nothing found");
                           return;
                       }



                       while (res.moveToNext() && count<4){

                        //   buffer.append("Id :"+ res.getString(0)+"\n");
                           buffer.append("Name :"+ res.getString(1)+"\n");
                           buffer.append("Phone :"+ res.getString(2)+"\n");

                            contactNumber[count]=res.getString(2);
                            count++;
                       }
                       count=0;
                       showMessage("Data",buffer.toString());
                    }
                }

        );

    }

    //End of SQLite Functions

    public void showMessage(String Title,String Message){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        builder.setCancelable(true);

        builder.setTitle(Title);

        builder.setMessage(Message);

        builder.show();
    }
}