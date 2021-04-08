package ru.geekbrains.notebook;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NoteListFragment extends Fragment {

    private boolean isLandscape;
    private Note selectedNote;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNoteList(view);
    }

    private void initNoteList(View view) {
        LinearLayout root = (LinearLayout) view;

        TextView textView1 = new TextView(getContext());
        createNoteItem(textView1, new Note("note1","заметка1"));

        TextView textView2 = new TextView(getContext());
        createNoteItem(textView2, new Note("note2","заметка2"));

        root.addView(textView1);
        root.addView(textView2);

    }

    private void createNoteItem(TextView txtV, Note note) {
        txtV.setText(note.getTitle());
        txtV.setOnClickListener(v->{
            selectedNote = note;
            if(isLandscape){
                showLandscapeNote(note);
            } else{
                showPortraitNote(note);
            }

        });
    }

    private void showLandscapeNote(Note note) {
        NoteFragment fragment = NoteFragment.newInstance(note);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flfrag, fragment)
                .commit();
    }

    private void showPortraitNote(Note note) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), NoteActivity.class);
        intent.putExtra(NoteActivity.PARCEL, note);
        startActivity(intent);

    }

}