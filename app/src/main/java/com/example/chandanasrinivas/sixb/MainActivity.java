package com.example.chandanasrinivas.sixb;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn=(Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent,1);
            }
        });

    }

    public void onActivityResult(int req,int res,Intent data)
    {
        super.onActivityResult(req,res,data);
        switch (req)
        {
            case (1) :  if(res== Activity.RESULT_OK)
                        {
                            Uri contactData=data.getData();
                            Cursor c= managedQuery(contactData,null,null,null,null);
                            if(c.moveToFirst()){
                                String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                                String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                                if(hasPhone.equalsIgnoreCase("1"))
                                {
                                    Cursor phones=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+id,null,null);
                                    phones.moveToFirst();
                                    String cNumber=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    Toast.makeText(getApplicationContext(), cNumber, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
        }
    }

}
