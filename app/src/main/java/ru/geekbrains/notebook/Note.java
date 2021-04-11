package ru.geekbrains.notebook;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;

public class Note implements Parcelable {

    private String title;
    private String body;
    private LocalDate dateOfCreation;
    private static int count;

    public static Note[] notes = {
            new Note("shopping list", "sugar\nsalt\nbread"),
            new Note("To DO list", "wake up\ndo stuff\ngo to bed"),
            new Note("note","")};

    private Note(String title, String body){
        this.title = title;
        this.body = body;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateOfCreation = LocalDate.now();
        }
        count++;
    }

    private Note() {
        this(String.format("new note %d", count), "");
    }

    protected Note(Parcel in) {
        title = in.readString();
        body = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Note{ " + title + '\'' +
                ", dateOfCreation=" + dateOfCreation +
                ", body='" + body + '\'' +'}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(body);
    }

    public static Note[] getNotes() {
        return notes;
    }
}
