package com.filipefalco.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText mLoginEmail, mLoginPassword;
    private RelativeLayout mLogin, mVoltarRegistro;
    private TextView mIrParaEsqueciSenha;

    private FirebaseAuth firebaseAuth;

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

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null) {
            finish();
            startActivity(new Intent(MainActivity.this, NotasActivity.class));
        }

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
                    firebaseAuth.signInWithEmailAndPassword(mail, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                checkMailVerification();
                            } else {
                                Toast.makeText(getApplicationContext(), "Conta não existe", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void checkMailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser.isEmailVerified() == true) {
            Toast.makeText(getApplicationContext(), "Logado", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(MainActivity.this, NotasActivity.class));
        } else {
            Toast.makeText(getApplicationContext(), "Verifique primeiro seu e-mail", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}