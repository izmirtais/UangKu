package com.example.mycashbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PengaturanActivity extends AppCompatActivity {
    EditText pass_now, pass_new;
    Button save;
    private com.example.mycashbook.DBHelper databaseHelper;
    private com.example.mycashbook.User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        databaseHelper = new com.example.mycashbook.DBHelper(getApplicationContext());
        user = new com.example.mycashbook.User();

        pass_now = findViewById(R.id.setting_pass_lama);
        pass_new = findViewById(R.id.setting_pass_baru);
        save = findViewById(R.id.setting_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pass_now.getText().toString().isEmpty() && !pass_new.getText().toString().isEmpty()){
                    if (databaseHelper.checkUser(getIntent().getStringExtra("username")
                            , pass_now.getText().toString().trim())) {
                        user.setUsername(getIntent().getStringExtra("username"));
                        user.setPassword(pass_new.getText().toString().trim());
                        databaseHelper.updateUser(user);
                        Toast.makeText(getApplicationContext(), "Password berhasil diubah!", Toast.LENGTH_LONG).show();
                    } else {
                        // Snack Bar to show success message that record is wrong
                        Toast.makeText(getApplicationContext(), "Password salah!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Tidak boleh ada yang kosong!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void toHome(View view) {
        Intent intent = new Intent(getApplicationContext(), com.example.mycashbook.MainActivity.class);
        intent.putExtra("username", getIntent().getStringExtra("username"));
        startActivity(intent);
        finish();
    }
}
