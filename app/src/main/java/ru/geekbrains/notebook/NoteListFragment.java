package ru.geekbrains.notebook;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

public class NoteListFragment extends Fragment{

    private boolean isLandscape;

    private CardSource data;
    private NoteListAdapter adapter;
    private RecyclerView recyclerView;
    private Publisher publisher;
    private Navigation navigation;
    //private boolean moveToLastPosition;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new CardSourceImpl(getResources()).init();
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
        setHasOptionsMenu(true);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();
        navigation = activity.getNavigation();
    }

    @Override
    public void onDetach() {
        publisher = null;
        navigation = null;
        super.onDetach();
    }


    private void initNoteList(View view) {
        recyclerView = view.findViewById(R.id.recycle_note_list);

        //создаем адаптер и сетим листенера на нажание элемента списка
        adapter = new NoteListAdapter(data,
                (view1, position) ->
                //TODO заменить на что-то полезное:
                Log.i("PLACEHOLDER", String.format("note %s selected", data.getCardData(position).getTitle())),
                this);

        //прикручиваем адаптер к ресайкл вью и отображаем с помощью менеджера
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

    }

    //если ориентация альбомная то создается новый фрагмент рядом

    private void showLandscapeNote(CardData cardData) {
        CardFragment fragment = CardFragment.newInstance(cardData);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flfrag, fragment)
                .commit();
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                //data.addCardData(new CardData("Новая заметка", "", Calendar.getInstance().getTime()));
                navigation.addFragment(CardFragment.newInstance());
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateCardData(CardData cardData) {
                        data.addCardData(cardData);
                        adapter.notifyItemInserted(data.size() - 1);
                        recyclerView.scrollToPosition(data.size() - 1);
                    }
                });

                return true;
            case R.id.action_delete:
                if (data.size() > 0) {
                    data.deleteCardData(data.size() - 1);
                    adapter.notifyDataSetChanged();
                }
                return true;
        }
        return false;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_context_card, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_update:
                int index = adapter.getSelectedContextMenuItem();
                CardData cd = data.getCardData(index);
                data.updateCardData(index, new CardData(String.format("%s updated", cd.getTitle()), cd.getBody(), Calendar.getInstance().getTime()));
                adapter.notifyItemChanged(index);
                return true;
            case R.id.action_clear:
                data.clearCardData();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }

}