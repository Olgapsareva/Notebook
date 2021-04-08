package ru.geekbrains.notebook;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NoteActivity extends AppCompatActivity {

    /*public static final String TITLE = "TITLE";
    public static final String BODY = "BODY";*/
    public static final String PARCEL = "PARCEL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Note selectedNote = getIntent().getParcelableExtra(PARCEL);
        TextView tx1 = findViewById(R.id.title);
        tx1.setText(selectedNote.getTitle());

        TextView tx2 = findViewById(R.id.body);
        tx2.setText(selectedNote.getBody());

    }
}