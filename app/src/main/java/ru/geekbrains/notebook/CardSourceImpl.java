package ru.geekbrains.notebook;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class CardSourceImpl implements CardSource{

    private List<CardData> dataSource;
    private Resources resources;

    public CardSourceImpl(Resources resources) {
        dataSource = new ArrayList<>(7);
        this.resources = resources;
    }

    public CardSourceImpl init() {
        String[] titles = resources.getStringArray(R.array.titles);
        String[] descriptions = resources.getStringArray(R.array.descriptions);

        for (int i = 0; i < descriptions.length; i++) {
            dataSource.add(new CardData(titles[i], descriptions[i]));
        }

        return this;
    }

    public Resources getResources() {
        return resources;
    }

    @Override
    public CardData getCardData(int position) {
        return dataSource.get(position);
    }

    @Override
    public int size() {
        return dataSource.size();
    }
}
