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
import android.widget.EditText;
import android.widget.Toast;

public class ContactsActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    Button UpdateButton,AddButton,ViewButton,DeleteButton,LoadButton,SendButton;
    EditText editName,editNumber,editId;

    private static final int RESULT_PICK_CONTACT = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        AddButton = (Button) findViewById(R.id.AddButton);
        ViewButton= (Button) findViewById(R.id.ViewButton);
        UpdateButton=(Button)findViewById(R.id.UpdateButton);
        DeleteButton=(Button)findViewById(R.id.delete_button);
        LoadButton=(Button)findViewById(R.id.load_button);
        SendButton=(Button)findViewById(R.id.send_button);

        editId=(EditText)findViewById(R.id.id_editText);
        editName= (EditText) findViewById(R.id.name_editText);
        editNumber=(EditText)findViewById(R.id.phone_editText);


        myDB = new DatabaseHelper(this);

        AddData();
        ViewData();
        UpdateData();
        DeleteData();
        pickContact();
        send();

    }

    public void send(){
        SendButton.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendSMS("9403955423", "Hi You got a message!");
                    }
                }
        );

    }
    
    //Following code doesnot work

    @SuppressWarnings("deprecation")
    private void sendSMS(String phoneNumber, String message)
    {
        SmsManager smsManager =     SmsManager.getDefault();
        smsManager.sendTextMessage("Phone Number", null, "Message", null, null);
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
                        Integer isDeleted=myDB.deleteData(editId.getText().toString());

                        if(isDeleted>0) {
                            Toast.makeText(ContactsActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        }
                        else {

                            Toast.makeText(ContactsActivity.this,"Data NOT Deleted",Toast.LENGTH_LONG).show();

                        }
                    }
                }

        );

    }

    public void UpdateData(){
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

    }

    public void AddData(){

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
    }

    public void ViewData(){
        ViewButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Cursor res= myDB.getData();
                        StringBuffer buffer = new StringBuffer();

                       if(res.getCount()==0){
                           showMessage("Error","Nothing found");
                           return;
                       }



                       while (res.moveToNext()){

                           buffer.append("Id :"+ res.getString(0)+"\n");
                           buffer.append("Name :"+ res.getString(1)+"\n");
                           buffer.append("Phone :"+ res.getString(2)+"\n");

                       }
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
