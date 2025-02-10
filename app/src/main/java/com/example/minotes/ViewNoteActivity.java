package com.example.minotes;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.minotes.helpers.Helpers;

public class ViewNoteActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView contentTextView;
    private TextView dateTextView;
    private NoteDao noteDao;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        titleTextView = findViewById(R.id.titleTextView);
        contentTextView = findViewById(R.id.contentTextView);
        dateTextView = findViewById(R.id.dateViewNote);

        noteDao = NoteDatabase.getInstance(this).noteDao();

        int noteId = getIntent().getIntExtra("note_id", -1);
        if (noteId != -1) {
            loadNoteDetails(noteId);
        }

    }

    private void loadNoteDetails(int noteId) {
        new Thread(() -> {
            note = noteDao.getNoteById(noteId);
            runOnUiThread(() -> {
                if (note != null) {
                    titleTextView.setText(note.getTitle());
                    contentTextView.setText(note.getContent());
                    dateTextView.setText(Helpers.dateToString(note.getDate()));
                }
            });
        }).start();
    }
}
