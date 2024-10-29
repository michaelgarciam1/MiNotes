package com.example.minotes;
import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Note> noteList;
    private NoteAdapter noteAdapter;

    private final ActivityResultLauncher<Intent> addNoteActivityLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    String title = data.getStringExtra("note_title");
                    String content = data.getStringExtra("note_content");

                    // Crea y añade la nueva nota a la lista
                    Note newNote = new Note();
                    newNote.setTitle(title);
                    newNote.setContent(content);
                    newNote.setDate(new Date());

                    noteList.add(newNote);
                    noteAdapter.notifyDataSetChanged();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa la lista de notas y el adaptador
        noteList = new ArrayList<>();
        noteList.add(new Note("Primera Nota", new Date(), "Contenido de la primera nota"));
        noteList.add(new Note("Segunda Nota", new Date(), "Contenido de la segunda nota"));

        // Configura el RecyclerView
        RecyclerView noteRecycler = findViewById(R.id.noteRecycler);
        noteRecycler.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(noteList);
        noteRecycler.setAdapter(noteAdapter);

        // Configura el Floating Action Button para añadir nuevas notas
        FloatingActionButton fabAddNote = findViewById(R.id.fab_add_note);
        fabAddNote.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            addNoteActivityLauncher.launch(intent);
        });
    }
}
