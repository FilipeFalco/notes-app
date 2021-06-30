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

public class EditarNotasActivity extends AppCompatActivity {

    Intent data;
    EditText editarTituloDaNota, editarConteudoDaNota;
    FloatingActionButton alterarNota;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_notas);
        editarTituloDaNota = findViewById(R.id.editarTituloDaNota);
        editarConteudoDaNota = findViewById(R.id.editarConteudoDaNota);
        alterarNota = findViewById(R.id.alterarNotaFAB);

        data=getIntent();

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Toolbar toolbar = findViewById(R.id.toolBarEditarNota);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        alterarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "clicou", Toast.LENGTH_SHORT);

                String novoTitulo = editarTituloDaNota.getText().toString();
                String novoConteudo = editarConteudoDaNota.getText().toString();

                if (novoTitulo.isEmpty() || novoConteudo.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Algo ficou vazio", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    DocumentReference documentReference = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document(data.getStringExtra("noteId"));
                    Map<String, Object> note = new HashMap<>();
                    note.put("title", novoTitulo);
                    note.put("content", novoConteudo);
                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "A nota foi atualizada", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(EditarNotasActivity.this, NotasActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Erro ao atualizar a nota", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        String tituloDaNota = data.getStringExtra("title");
        String conteudoDaNota = data.getStringExtra("content");
        editarTituloDaNota.setText(tituloDaNota);
        editarConteudoDaNota.setText(conteudoDaNota);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}