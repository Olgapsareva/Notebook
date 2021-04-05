package ru.geekbrains.notebook;

import java.util.Date;

public class Note {

    private String title;
    private String body;
    private Date dateOfCreation;
    private static int count;

    public Note(String title, String body){
        this.title = title;
        this.body = body;
        count++;
    }

    public Note() {
        this(String.format("new note %d", count), "");
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    @Override
    public String toString() {
        return "Note{ " + title + '\'' +
                ", dateOfCreation=" + dateOfCreation +
                ", body='" + body + '\'' +'}';
    }


}
