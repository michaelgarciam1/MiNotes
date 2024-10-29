package com.example.minotes;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class Note {
    @Setter @Getter private  String title;
    @Setter @Getter private Date date;
    @Setter @Getter private String content;

    public Note() {
        this.title = "";
        this.date = new Date();
        this.content = "";
    }

    public Note(String title, Date date, String content) {
        this.title = title;
        this.date = date;
        this.content = content;
    }
}
