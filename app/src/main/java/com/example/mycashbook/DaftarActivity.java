package com.example.mycashbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DaftarActivity extends Activity {

    EditText username, password, ulangi_pass;
    Button daftar;
    TextView login;
    private com.example.mycashbook.DBHelper databaseHelper;
    private com.example.mycashbook.User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        username = findViewById(R.id.daftar_username);
        password = findViewById(R.id.daftar_password);
        ulangi_pass = findViewById(R.id.daftar_ulangi_password);
        daftar = findViewById(R.id.daftar);
        login = findViewById(R.id.daftar_kliklogin);

        databaseHelper = new com.example.mycashbook.DBHelper(this);
        user = new com.example.mycashbook.User();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty() && !ulangi_pass.getText().toString().isEmpty()){
                    if (password.getText().toString().trim().equals(ulangi_pass.getText().toString().trim())){
                        if (!databaseHelper.checkUser(username.getText().toString().trim())) {
                            user.setUsername(username.getText().toString().trim());
                            user.setPassword(password.getText().toString().trim());
                            databaseHelper.addUser(user);
                            // Snack Bar to show success message that record saved successfully
                            Toast.makeText(getApplicationContext(), "Username berhasil terdaftar", Toast.LENGTH_LONG).show();
                            emptyInputEditText();
                            Intent intent = new Intent(getApplicationContext(), com.example.mycashbook.MainActivity.class);
                            intent.putExtra("username", username.getText().toString());
                            startActivity(intent);
                            finish();
                        } else {
                            // Snack Bar to show error message that record already exists
                            Toast.makeText(getApplicationContext(), "Username telah terdaftar", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Password dan Ulangi password tidak sama!", Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(getApplicationContext(), "Tidak boleh ada yang kosong!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void emptyInputEditText() {
        username.setText(null);
        password.setText(null);
        ulangi_pass.setText(null);
    }
}
