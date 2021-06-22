package com.filipefalco.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EsqueciSenha extends AppCompatActivity {

    private EditText mEsqueceuSenha;
    private Button mRecuperarSenhaButton;
    private TextView mVoltarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci_senha);

        getSupportActionBar().hide();
        mEsqueceuSenha = findViewById(R.id.esqueceuSenha);
        mRecuperarSenhaButton = findViewById(R.id.recuperarSenha);
        mVoltarLogin = findViewById(R.id.voltarLogin);

        mVoltarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EsqueciSenha.this, MainActivity.class);
                startActivity(intent);
            }
        });

        mRecuperarSenhaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mEsqueceuSenha.getText().toString().trim();

                if(mail.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Entre com seu e-mail primeiramente", Toast.LENGTH_SHORT).show();
                } else {
                    // temos que enviar email para recuperação de senha
                }
            }
        });
    }
}