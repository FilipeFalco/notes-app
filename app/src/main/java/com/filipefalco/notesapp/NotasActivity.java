package com.filipefalco.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


//import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class NotasActivity extends AppCompatActivity {

    FloatingActionButton mCriarNotaFab;
    private FirebaseAuth firebaseAuth;

    RecyclerView recyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    FirestoreRecyclerAdapter<FirebaseModel, NoteViewHolder> noteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        mCriarNotaFab = findViewById(R.id.criarNotaFab);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        getSupportActionBar().setTitle("Todas as Notas");

        mCriarNotaFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NotasActivity.this, CriarNotas.class));
            }
        });

        Query query = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").orderBy("title", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<FirebaseModel> allUserNotes = new FirestoreRecyclerOptions.Builder<FirebaseModel>().setQuery(query, FirebaseModel.class).build();

        noteAdapter = new FirestoreRecyclerAdapter<FirebaseModel, NoteViewHolder>(allUserNotes) {
            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull FirebaseModel model) {

                holder.notetitle.setText(model.getTitle());
                holder.notecontent.setText(model.getContent());

            }

            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
               View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notas_layout, viewGroup, false);
               return new NoteViewHolder(view);
            }
        };

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(noteAdapter);

    }


    public class NoteViewHolder extends RecyclerView.ViewHolder{
        private TextView notetitle;
        private TextView notecontent;
        LinearLayout note;


        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            notetitle = itemView.findViewById(R.id.notaTitulo);
            notecontent = itemView.findViewById(R.id.conteudoNota);
            note = itemView.findViewById(R.id.nota);

        }
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

    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.startListening();
    }
    protected void onStop() {
        super.onStop();
        if (noteAdapter != null){
            noteAdapter.startListening();
        }
    }
}