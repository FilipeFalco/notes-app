package com.filipefalco.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Registro extends AppCompatActivity {

    private EditText mRegistroEmail, mRegistroPassword;
    private RelativeLayout mRegistro;
    private TextView mVoltarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        getSupportActionBar().hide();

        mRegistroEmail = findViewById(R.id.registroEmail);
        mRegistroPassword = findViewById(R.id.registroPassword);
        mRegistro = findViewById(R.id.registrar);
        mVoltarLogin = findViewById(R.id.voltarLogin);

        mVoltarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registro.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mRegistroEmail.getText().toString().trim();
                String senha = mRegistroPassword.getText().toString().trim();

                if(mail.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Todos os campos precisão estar preenchidos", Toast.LENGTH_SHORT).show();
                } else if(senha.length() < 7 ){
                    Toast.makeText(getApplicationContext(), "Senha tem que ter mais de 7 caracteres", Toast.LENGTH_SHORT).show();
                } else {
                     // registrar usuario no firebase
                }
            }
        });
    }
}