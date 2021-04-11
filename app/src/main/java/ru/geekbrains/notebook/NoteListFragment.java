package ru.geekbrains.notebook;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NoteListFragment extends Fragment {

    private boolean isLandscape;

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
        Note[] data = Note.getNotes();
        RecyclerView recyclerView = view.findViewById(R.id.recycle_note_list);

        //создаем адаптер и сетим листенера на нажание элемента списка
        NoteListAdapter adapter = new NoteListAdapter(data, (view1, position) ->
                //TODO заменить на что-то полезное:
                Log.i("PLACEHOLDER", String.format("note %s selected", data[position].getTitle()))
        );

        //прикручиваем адаптер к ресайкл вью и отображаем с помощью менеджера
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    private void createNoteItem(TextView txtV, Note note) {
        txtV.setText(note.getTitle());
        txtV.setOnClickListener(v->{
            if(isLandscape){
                showLandscapeNote(note);
            } else{
                showPortraitNote(note);
            }

        });
    }
    //если ориентация альбомная то создается новый фрагмент рядом
    private void showLandscapeNote(Note note) {
        NoteFragment fragment = NoteFragment.newInstance(note);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flfrag, fragment)
                .commit();
    }

    //если ориентация портретная то вызывается новая активити
    private void showPortraitNote(Note note) {
        /*Intent intent = new Intent();
        intent.setClass(getActivity(), NoteActivity.class);
        intent.putExtra(NoteActivity.PARCEL, note);
        startActivity(intent);*/

        NoteFragment fragment = NoteFragment.newInstance(note);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.note_list_fragment, fragment)
                .commit();
    }

}