package com.filipefalco.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class NotasActivity extends AppCompatActivity {

    FloatingActionButton mCriarNotaFab;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        mCriarNotaFab = findViewById(R.id.criarNotaFab);
        firebaseAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setTitle("Todas as Notas");

        mCriarNotaFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NotasActivity.this, CriarNotas.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sair:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(NotasActivity.this, MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}