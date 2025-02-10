package com.example.minotes.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helpers {

    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
}
