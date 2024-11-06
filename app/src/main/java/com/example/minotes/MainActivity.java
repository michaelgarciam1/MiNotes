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

    private NoteDatabase noteDatabase;
    private NoteDao noteDao;
    private List<Note> noteList = new ArrayList<>();
    private NoteAdapter noteAdapter;

    // Definir el ActivityResultLauncher para manejar el resultado de AddNoteActivity
    private final ActivityResultLauncher<Intent> addNoteActivityLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    String title = data.getStringExtra("note_title");
                    String content = data.getStringExtra("note_content");

                    // Crear y añadir la nueva nota a la base de datos
                    Note newNote = new Note();
                    newNote.setTitle(title);
                    newNote.setContent(content);
                    newNote.setDate(new Date());

                    addNoteToDatabase(newNote);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar la base de datos y el DAO
        noteDatabase = NoteDatabase.getInstance(this);
        noteDao = noteDatabase.noteDao();

        // Configura el RecyclerView y el adaptador
        RecyclerView noteRecycler = findViewById(R.id.noteRecycler);
        noteRecycler.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(noteList, note -> {
            Intent intent = new Intent(MainActivity.this, ViewNoteActivity.class);
            intent.putExtra("note_id", note.getId());
            startActivity(intent);
        });
        noteRecycler.setAdapter(noteAdapter);

        // Cargar las notas desde la base de datos en un hilo secundario
        loadNotesFromDatabase();

        // Configura el FAB para añadir nuevas notas
        FloatingActionButton fabAddNote = findViewById(R.id.fab_add_note);
        fabAddNote.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
            addNoteActivityLauncher.launch(intent);
        });
    }

    private void loadNotesFromDatabase() {
        new Thread(() -> {
            // Cargar las notas desde la base de datos
            List<Note> notes = noteDao.getAllNotes();
            noteList.clear();
            noteList.addAll(notes);

            // Actualizar el RecyclerView en el hilo principal
            runOnUiThread(() -> noteAdapter.notifyDataSetChanged());
        }).start();
    }

    private void addNoteToDatabase(Note note) {
        new Thread(() -> {
            noteDao.insert(note);
            noteList.add(note);
            runOnUiThread(() -> {
                noteAdapter.notifyDataSetChanged();
            });
        }).start();
    }
}
