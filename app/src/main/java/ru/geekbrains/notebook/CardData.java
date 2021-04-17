package ru.geekbrains.notebook;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

public class CardData implements Parcelable, Observer {

    private String title;
    private String body;
    private Date dateOfCreation;

    public CardData(String title, String body, Date dateOfCreation){
        this.title = title;
        this.body = body;
        this.dateOfCreation = dateOfCreation;
    }

    protected CardData(Parcel in) {
        title = in.readString();
        body = in.readString();
        dateOfCreation = new Date(in.readLong());
    }

    public static final Creator<CardData> CREATOR = new Creator<CardData>() {
        @Override
        public CardData createFromParcel(Parcel in) {
            return new CardData(in);
        }

        @Override
        public CardData[] newArray(int size) {
            return new CardData[size];
        }
    };

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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(body);
        dest.writeLong(dateOfCreation.getTime());
    }

    @Override
    public void updateCardData(CardData cardData) {

    }
}
