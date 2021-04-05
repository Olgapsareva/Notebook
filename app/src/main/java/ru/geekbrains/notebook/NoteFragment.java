package ru.geekbrains.notebook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class NoteFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String KEY = "NOTE";
    private Note selectedNote;


    public NoteFragment() {
        // Required empty public constructor
    }

    public static NoteFragment newInstance(Note note) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedNote = getArguments().getParcelable(KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_note, container, false);

        //если в bundle нет аргументов, то ландшафтная ориентация ещё не вызывалась
        if (getArguments() != null) {
            EditText body = view.findViewById(R.id.body);
            body.setText(selectedNote.getBody());
            TextView title = view.findViewById(R.id.title);
            title.setText(selectedNote.getTitle());
        }

        return view;
    }


}