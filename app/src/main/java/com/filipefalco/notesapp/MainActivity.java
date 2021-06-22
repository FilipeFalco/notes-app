package com.filipefalco.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mLoginEmail, mLoginPassword;
    private RelativeLayout mLogin, mVoltarRegistro;
    private TextView mIrParaEsqueciSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        mLoginEmail = findViewById(R.id.loginEmail);
        mLoginPassword = findViewById(R.id.loginPassword);
        mLogin = findViewById(R.id.login);
        mVoltarRegistro = findViewById(R.id.voltarRegistro);
        mIrParaEsqueciSenha = findViewById(R.id.irParaEsqueciSenha);

        mVoltarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Registro.class));
            }
        });

        mIrParaEsqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EsqueciSenha.class));
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mLoginEmail.getText().toString().trim();
                String senha = mLoginPassword.getText().toString().trim();

                if(mail.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Todos os campos precisão estar preenchidos", Toast.LENGTH_SHORT).show();
                } else {
                    // Logar usuário
                }
            }
        });
    }
}