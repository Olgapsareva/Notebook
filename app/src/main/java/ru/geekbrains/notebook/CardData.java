package ru.geekbrains.notebook;

import android.os.Build;

import java.time.LocalDate;

public class CardData {

    private String title;
    private String body;
    private LocalDate dateOfCreation;

    public CardData(String title, String body) {
        this.title = title;
        this.body = body;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateOfCreation = LocalDate.now();
        }
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }
}
