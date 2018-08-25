package xyz.abhishekshah.accidentalertsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactsActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    Button AddButton,ViewButton;
    EditText editText,editText1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        editText= (EditText) findViewById(R.id.name_editText);
        AddButton = (Button) findViewById(R.id.AddButton);
        ViewButton= (Button) findViewById(R.id.ViewButton);
        myDB = new DatabaseHelper(this);

        ViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent (ContactsActivity.this,ListDataActivity.class);
                startActivity(intent);
            }
        });


        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEntry = editText.getText().toString();



                if(editText.length()!=0) {
                    AddData(newEntry);
                    editText.setText("");
                }
                else
                {
                    Toast.makeText(ContactsActivity.this,"You must enter the Name  ",Toast.LENGTH_LONG).show();


                }

            }
        });
    }

    public void AddData(String name){

        boolean insertData = myDB.addData(name);

        if(insertData==true)
        {
            Toast.makeText(ContactsActivity.this,"Successfully Entered.",Toast.LENGTH_LONG).show();

        }else{

            Toast.makeText(ContactsActivity.this,"Something went wrong. ",Toast.LENGTH_LONG).show();

        }


    }
}
