package com.example.minotes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(tableName = "notes")
public class Note {
    // Getters y setters
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private Date date;
    private String content;

    // Constructor

    public Note() {
    }

    public Note(String title, Date date, String content) {
        this.title = title;
        this.date = date;
        this.content = content;
    }



}
