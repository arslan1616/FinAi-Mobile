package com.example.finai;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddtoContact extends AppCompatActivity {
    EditText fname, lname, pno;
    Button addButton;
    UserDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addto_contact);

        // View'ları başlat
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        pno = findViewById(R.id.phone);
        addButton = findViewById(R.id.addButton);

        // Database helper'ı başlat
        dbHelper = new UserDatabaseHelper(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ad = fname.getText().toString();
                String soyad = lname.getText().toString();
                String numara = pno.getText().toString();

                if (ad.isEmpty() || soyad.isEmpty() || numara.isEmpty()) {
                    Toast.makeText(AddtoContact.this, "Tüm alanları doldurun", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Veritabanına yeni kullanıcı ekle
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(UserDatabaseHelper.COLUMN_FIRST_NAME, ad);
                values.put(UserDatabaseHelper.COLUMN_LAST_NAME, soyad);
                values.put(UserDatabaseHelper.COLUMN_PHONE, numara);

                long newRowId = db.insert(UserDatabaseHelper.TABLE_USERS, null, values);

                if (newRowId != -1) {
                    Toast.makeText(AddtoContact.this, "Kullanıcı başarıyla eklendi", Toast.LENGTH_SHORT).show();
                    // Formu sıfırla
                    fname.setText("");
                    lname.setText("");
                    pno.setText("");
                } else {
                    Toast.makeText(AddtoContact.this, "Kullanıcı eklenirken bir hata oluştu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}