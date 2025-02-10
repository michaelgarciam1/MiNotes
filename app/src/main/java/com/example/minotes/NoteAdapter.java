package com.example.minotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minotes.helpers.Helpers;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> noteList;
    private OnNoteClickListener onNoteClickListener;

    // Interfaz para manejar los clics en las notas
    public interface OnNoteClickListener {
        void onNoteClick(Note note);

        void onNoteLongClick(Note note);
    }


    // Constructor que recibe la lista de notas y el listener para el clic
    public NoteAdapter(List<Note> noteList, OnNoteClickListener onNoteClickListener) {
        this.noteList = noteList;
        this.onNoteClickListener = onNoteClickListener;
    }

    // ViewHolder: Define cómo se verán los datos de cada elemento
    public class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView contentTextView;
        public TextView dateTextView;

        public NoteViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.note_title);
            contentTextView = itemView.findViewById(R.id.note_content);
            dateTextView = itemView.findViewById(R.id.note_date);
            // Configurar el clic para cada nota
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // Llama al método onNoteClick del listener con la nota correspondiente
                    onNoteClickListener.onNoteClick(noteList.get(position));
                }
            });
            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onNoteClickListener.onNoteLongClick(noteList.get(position));
                }
                return true; // Indica que el evento se ha manejado
            });

        }
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el layout para cada elemento de la lista
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        // Enlazar los datos de cada nota con las vistas
        Note note = noteList.get(position);
        holder.titleTextView.setText(note.getTitle());
        holder.dateTextView.setText(Helpers.dateToString(note.getDate()));
        holder.contentTextView.setText(note.getContent());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void removeNote(Note note) {
        int position = noteList.indexOf(note);
        if (position != -1) {
            noteList.remove(position);
            notifyItemRemoved(position);
        }
    }
}
