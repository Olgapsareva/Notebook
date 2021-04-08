package ru.geekbrains.notebook;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Note implements Parcelable {

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

    public Date getDateOfCreation() {
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
}
