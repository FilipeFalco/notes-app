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

public class Registro extends AppCompatActivity {

    private EditText mRegistroEmail, mRegistroPassword;
    private RelativeLayout mRegistro;
    private TextView mVoltarLogin;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        getSupportActionBar().hide();

        mRegistroEmail = findViewById(R.id.registroEmail);
        mRegistroPassword = findViewById(R.id.registroPassword);
        mRegistro = findViewById(R.id.registrar);
        mVoltarLogin = findViewById(R.id.voltarLogin);

        // pegar instancia firebase
        firebaseAuth = FirebaseAuth.getInstance();

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
                    firebaseAuth.createUserWithEmailAndPassword(mail, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Registrdo com sucesso", Toast.LENGTH_SHORT).show();
                                sendEmailVerification();
                            } else {
                                Toast.makeText(getApplicationContext(), "Falha ao registrar", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    // enviar verificação de email
    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(), "E-mail de verificação enviado, verifique o e-mail e faça o login", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(Registro.this, MainActivity.class));
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Falha ao enviar o e-mail de verificação", Toast.LENGTH_SHORT).show();
        }
    }
}