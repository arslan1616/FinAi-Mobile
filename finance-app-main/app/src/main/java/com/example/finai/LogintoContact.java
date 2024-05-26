package com.example.finai;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogintoContact extends AppCompatActivity {
    EditText fname, lname, pno;
    Button loginButton;
    String ad, soyad, numara;
    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginto_contact);

        // View'ları başlat
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        pno = findViewById(R.id.phone);
        loginButton = findViewById(R.id.loginButton);

        // UserRepository'yi başlat
        userRepository = new UserRepository(this);
        userRepository.open();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad = fname.getText().toString();
                soyad = lname.getText().toString();
                numara = pno.getText().toString();

                if (ad.isEmpty() || soyad.isEmpty() || numara.isEmpty()) {
                    Toast.makeText(LogintoContact.this, "Tüm alanları doldurun", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Admin kontrolünü burada yapalım
                if (ad.equals("admin") && soyad.equals("admin") && numara.equals("1453")) {
                    Intent intent = new Intent(LogintoContact.this, AdminActivity.class);
                    startActivity(intent);
                    finish(); // Aktiviteyi bitir
                    return;
                }

                Cursor cursor = userRepository.getUserByCredentials(ad, soyad, numara);

                if (cursor != null && cursor.moveToFirst()) {
                    int userId = cursor.getInt(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_ID));
                    boolean isAdmin = userRepository.isAdmin(userId);

                    if (isAdmin) {
                        Intent intent = new Intent(LogintoContact.this, AdminActivity.class);
                        intent.putExtra("USER_ID", userId);
                        startActivity(intent);
                        finish(); // Aktiviteyi bitir
                    } else {
                        Intent intent = new Intent(LogintoContact.this, Main_page.class);
                        intent.putExtra("USER_ID", userId);
                        startActivity(intent);
                        finish(); // Aktiviteyi bitir
                    }
                    cursor.close();
                } else {
                    Toast.makeText(LogintoContact.this, "Kullanıcı bulunamadı", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userRepository.close();
    }
}