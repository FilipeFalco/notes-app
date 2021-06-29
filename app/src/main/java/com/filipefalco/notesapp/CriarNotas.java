package com.filipefalco.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CriarNotas extends AppCompatActivity {

    EditText criarTituloDaNota, criarConteudoDaNota;
    FloatingActionButton salvarNota;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_notas);

        salvarNota = findViewById(R.id.salvarNotaFab);
        criarTituloDaNota = findViewById(R.id.criarTituloDaNota);
        criarConteudoDaNota = findViewById(R.id.criarConteudoDaNota);

        Toolbar toolbar = findViewById(R.id.toolBarCriarNotas);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        salvarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = criarTituloDaNota.getText().toString();
                String content = criarConteudoDaNota.getText().toString();
                if (title.isEmpty() || content.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ambos os campos são necessários", Toast.LENGTH_SHORT).show();
                }else{
                    DocumentReference documentReference = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document();
                    Map<String, Object> note = new HashMap<>();
                    note.put("title", title);
                    note.put("content", content);

                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Nota criada com sucesso", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(CriarNotas.this, NotasActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Erro ao criar a nota", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(CriarNotas.this, NotasActivity.class));

                        }
                    });
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}