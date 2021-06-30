package com.filipefalco.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetalhesDaNotaActivity extends AppCompatActivity {

    private TextView tituloDetalhesDaNota, conteudoDetalhesDaNota;
    FloatingActionButton irParaEditarNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_da_nota);
        tituloDetalhesDaNota = findViewById(R.id.tituloDetalhesDaNota);
        conteudoDetalhesDaNota = findViewById(R.id.conteudoDetalhesDaNota);
        irParaEditarNotas = findViewById(R.id.editarNotaFAB);
        Toolbar toolbar = findViewById(R.id.toolBarDetalhesDaNota);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent data = getIntent();

        irParaEditarNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(v.getContext(), EditarNotasActivity.class);
                it.putExtra("title", data.getStringExtra("title"));
                it.putExtra("content", data.getStringExtra("content"));
                it.putExtra("noteId", data.getStringExtra("noteId"));
                v.getContext().startActivity(it);
            }
        });

        conteudoDetalhesDaNota.setText(data.getStringExtra("content"));
        tituloDetalhesDaNota.setText(data.getStringExtra("title"));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}